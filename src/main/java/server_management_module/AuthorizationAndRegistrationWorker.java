package server_management_module;

import collection_management_module.LoginCollectionManagement;
import data_base.DBWorker;
import server_management_module.server_request_reading.ReadingRequestManager;

import java.nio.channels.SocketChannel;


public class AuthorizationAndRegistrationWorker {
    private final ReadingRequestManager readingRequestManager;
    private final LoginCollectionManagement loginCollectionManagement;
    private final SocketChannel client;

    public AuthorizationAndRegistrationWorker(ReadingRequestManager readingRequestManager, LoginCollectionManagement loginCollectionManagement, SocketChannel client) {
        this.readingRequestManager = readingRequestManager;
        this.loginCollectionManagement = loginCollectionManagement;
        this.client = client;
    }

    private void sendMessage() {
        OutputDeviceWorker.getOutputDevice().sendMessage(client);
    }

    private String register(String userName) {
        OutputDeviceWorker.getOutputDevice().createMessage("You are not registered yet, if you want to register, write yes");
        sendMessage();
        if (readingRequestManager.getClientString().equals("yes")) {
            while (true) {
                OutputDeviceWorker.getOutputDevice().createMessage("Input your new password or input exit");
                sendMessage();
                String password = readingRequestManager.getClientString();
                if (password.equals("exit")) {
                    OutputDeviceWorker.getOutputDevice().createMessage("You have logged out of the app");
                    OutputDeviceWorker.getOutputDevice().setEndOfClientFlag();
                    sendMessage();
                    break;
                }
                OutputDeviceWorker.getOutputDevice().createMessage("repeat the password or input exit");
                sendMessage();
                String repeatPassword = readingRequestManager.getClientString();
                if (repeatPassword.equals("exit")) {
                    OutputDeviceWorker.getOutputDevice().createMessage("You have logged out of the app");
                    OutputDeviceWorker.getOutputDevice().setEndOfClientFlag();
                    sendMessage();
                    break;
                }
                if (repeatPassword.equals(password)) {
                    OutputDeviceWorker.getOutputDevice().createMessage(userName);
                    OutputDeviceWorker.getOutputDevice().setLoggedUserFlag();
                    sendMessage();
                    OutputDeviceWorker.getOutputDevice().createMessage("You have successfully registered, to find out the list of available commands, type \"help\"");
                    sendMessage();
                    new DBWorker(loginCollectionManagement).register(userName, password);
                    return userName;
                }
            }
        } else {
            OutputDeviceWorker.getOutputDevice().createMessage("If you want to exit the application write exit");
            sendMessage();
            if (readingRequestManager.getClientString().equals("exit")) {
                OutputDeviceWorker.getOutputDevice().createMessage("You have logged out of the app");
                OutputDeviceWorker.getOutputDevice().setEndOfClientFlag();
                sendMessage();
            } else return login();
        }
        return userName;
    }

    public String login() {
        boolean isEndOfUserFlag = false;
        OutputDeviceWorker.getOutputDevice().createMessage("Log in to the app, enter your username");
        sendMessage();
        String username = readingRequestManager.getClientString();
        if (loginCollectionManagement.isRegisteredUser(username)) {
            OutputDeviceWorker.getOutputDevice().createMessage("Please enter your password, or write exit");
            sendMessage();
            while (true) {
                String password = readingRequestManager.getClientString();
                if (password.equals("exit")) {
                    OutputDeviceWorker.getOutputDevice().createMessage("You have logged out of the app");
                    OutputDeviceWorker.getOutputDevice().setEndOfClientFlag();
                    sendMessage();
                    isEndOfUserFlag = true;
                    break;
                }
                if (loginCollectionManagement.isTruePassword(username, password)) break;
                OutputDeviceWorker.getOutputDevice().createMessage("You entered the wrong password, please try again, or input exit");
                sendMessage();
            }
            if (!isEndOfUserFlag) {
                OutputDeviceWorker.getOutputDevice().createMessage(username);
                OutputDeviceWorker.getOutputDevice().setLoggedUserFlag();
                sendMessage();
                OutputDeviceWorker.getOutputDevice().createMessage("You have successfully logged in, to find out the list of available commands, type \"help\"");
                sendMessage();
            }
        } else return register(username);
        return username;
    }

}
