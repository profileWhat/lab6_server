package fileManagementModule;

import java.io.*;

import serverManagementModule.OutputDeviceWorker;

/**
 * Class for work with file
 */
public class FileWorker {
    private String fileName;
    private static FileWorker fileWorker;
    private boolean isNotWorkedFile;

    private FileWorker() {
        this.isNotWorkedFile = false;
    }

    /**
     * Static Method to init File Worker for the first time and then get this File Worker.
     *
     * @return File Worker
     */
    public static FileWorker getFileWorker() {
        if (FileWorker.fileWorker == null) FileWorker.fileWorker = new FileWorker();
        return FileWorker.fileWorker;
    }

    /**
     * Method for set file name
     *
     * @param fileName to set it
     */
    public void setFileName(String fileName) {
        try {
            this.fileName = fileName;
            if (fileName.contains("\\dev\\") || fileName.contains("/dev/")) {
                isNotWorkedFile = true;
                OutputDeviceWorker.getOutputDevice().createMessage("Incorrect File, collection will not be load");
            }
        } catch (NullPointerException e) {
            isNotWorkedFile = true;
            OutputDeviceWorker.getOutputDevice().describeFileNotSpecified();
        }
    }

    /**
     * Method for write to file
     *
     * @param s for write it to file
     */
    public void write(String s) {
        if (!isNotWorkedFile) {
            try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName))) {
                out.write(s);
            } catch (IOException e) {
                isNotWorkedFile = true;
                OutputDeviceWorker.getOutputDevice().describeException(e);
            } catch (NullPointerException e) {
                isNotWorkedFile = true;
                OutputDeviceWorker.getOutputDevice().describeFileNotSpecified();
            }
        }
    }

    /**
     * Method for read string into file
     *
     * @return read string
     */
    public String read() {
        StringBuilder data = new StringBuilder();
        if (!isNotWorkedFile) {
            try (FileReader in = new FileReader(fileName)) {
                char[] cBuf = new char[1];
                while (in.read(cBuf) != -1) {
                    data.append(cBuf);
                }
            } catch (IOException e) {
                isNotWorkedFile = true;
                OutputDeviceWorker.getOutputDevice().describeException(e);
            } catch (NullPointerException e) {
                isNotWorkedFile = true;
                OutputDeviceWorker.getOutputDevice().describeFileNotSpecified();
            }
        }
        return data.toString();

    }

    /**
     * Method for return is Not Worked File flag
     *
     * @return is not worked file flag
     */
    public boolean isNotWorkedFile() {
        return isNotWorkedFile;
    }

}
