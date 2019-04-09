package Client.ClientController;

import Client.ClientView.LoginView;
import Client.ClientView.MainView;
import utils.*;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This class is responsible for communicating with the server
 * and holding the LoginController
 * Overall the client controller is used for communication with
 * the server
 *
 * @author Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class ClientController {

    //MEMBER VARIABLES
    private ObjectOutputStream socketOut;
    private Socket aSocket;
    private ObjectInputStream socketIn;

    private LoginController loginController;
    private MainGUIController mainGUIController;

    /**
     * Constructs a Client controller object
     *
     * @param serverName name of server
     * @param portNumber port number
     */
    public ClientController(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);

            socketIn = new ObjectInputStream(aSocket.getInputStream());
            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            MainView mainView = new MainView(800, 550);
            LoginView loginView = new LoginView(250, 175);

            loginController = new LoginController(loginView, this);
            mainGUIController = new MainGUIController(mainView, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs the client side
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ClientController cc = new ClientController("localhost", 9000);

        cc.mainGUIController.importItemsFromServer();
        //cc.importTableModelFromServer();

        cc.showMainWindow();
    }


    /**
     * imports and sets tool table model from server
     */
    public void importTableModelFromServer() {
        try {
            DefaultTableModel readTableModel = (DefaultTableModel) socketIn.readObject();
            mainGUIController.getMainView().setTableModel(readTableModel);
        } catch (Exception e) {
            System.out.println("ClientController: importTableModelFromServer error");
            e.printStackTrace();
        }
    }

    /**
     * verifies if user has logged in, then makes Main Window visible
     */
    public void showMainWindow() {
        while (!loginController.isVerified()) {
            mainGUIController.getMainView().setVisible(false);
        }

        mainGUIController.getMainView().setVisible(true);
    }

    //GETTERS AND SETTERS
    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }

    public GUIController getLoginController() {
        return loginController;
    }

    public GUIController getMainGUIController() {
        return mainGUIController;
    }
}