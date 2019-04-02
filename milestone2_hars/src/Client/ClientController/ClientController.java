package Client.ClientController;

import Client.ClientModel.MainModel;
import Client.ClientView.LoginView;
import Client.ClientView.MainView;
import Server.ServerModel.Item;

import java.io.*;
import java.net.*;

/**
 * This class is responsible for communicating with the server
 * and holding the LoginController
 * Overall the client controller is used for communication with
 * the server
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class ClientController {

    //MEMBER VARIABLES

    private ObjectOutputStream socketOut;
    private Socket aSocket;
    private ObjectInputStream socketIn;

    private LoginController loginController;

    /**
     * Constructs a Client controller object
     * @param serverName name of server
     * @param portNumber port number
     */
    public ClientController(String serverName, int portNumber){
        try{
            aSocket = new Socket(serverName, portNumber);

            socketIn = new ObjectInputStream(aSocket.getInputStream());
            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            loginController = new LoginController(new MainGUIController(new MainView(500,400),
                                                                        new MainModel()),new LoginView(250,150),
                                                                        socketOut, socketIn);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * runs the client side
     * @param args command line arguments
     */
    public static void main(String[] args){
        ClientController clientController = new ClientController("localhost", 1234);

        clientController.importToolsFromServer();
    }

    /**
     * imports tools from server
     */
    public void importToolsFromServer(){
        try {
            int numItems = Integer.parseInt((String)socketIn.readObject());

            while(numItems > 0){
                loginController.getMainGUIController().getMainModel().getItems().add((Item)socketIn.readObject());
                numItems--;
            }

            System.out.println("Imported Tools From Server!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
