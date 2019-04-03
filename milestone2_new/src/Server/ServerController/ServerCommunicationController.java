package Server.ServerController;

import Server.ServerModel.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Server.ServerModel.ServerModel;

/**
 *The Server communications class serves to create the different input and output
 *screens as well as start off the GUI with the log in menu and ensuring the user
 *enters a correct username and pass.  It also creates the new sockets for input
 * and output and exports the tools into the client
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class ServerCommunicationController {

    private final int PORT = 9999;
    private Socket aSocket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ServerSocket serverSocket;
    private ServerModel serverModel;

    /**
     * This is the constructor for the class and it adds a new
     * socket and creates the output stream as well as creates
     * a new object of the serverModel
     */
    public ServerCommunicationController(){
        try{

            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running");

            aSocket = serverSocket.accept();
            System.out.println("after accept");

            socketOut = new ObjectOutputStream(aSocket.getOutputStream());

            serverModel = new ServerModel();

        }catch(IOException e){

            System.out.println("Create new socket error");
            System.out.println(e.getMessage());

        }
    }

    /**
     * This is the main String for the Server which creates the input stream and then exports the
     * tools and checks to see if the user entered right username and password
     */
    public static void main(String[] args){
        ServerCommunicationController myServer = new ServerCommunicationController();

        myServer.createInputStream(); //after connects with client (client creates output stream)

        myServer.exportToolsToClient();

        myServer.verifyLogin();

        myServer.SupplierCheck();
    }

    /**
     * SupplierCheck
     */
    public void SupplierCheck() {
        while (true) {
            try {
                String read = (String) socketIn.readObject();
                int readSuppID = Integer.valueOf(read);

                Supplier searchedSupp = serverModel.getMyShop().searchSupplier(readSuppID);

                if (searchedSupp != null)
                    socketOut.writeObject(searchedSupp);
                else
                    socketOut.writeObject(null);

            } catch (Exception e) {
                System.out.println("Supplier Check Error");
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates an input socket stream from server
     */
    public void createInputStream(){
        try{
            socketIn = new ObjectInputStream(aSocket.getInputStream());
        }catch (IOException e){
            System.out.println("Error creating server output stream");
            e.printStackTrace();
        }
    }

    /**
     * Verifies the log in by running an infinite loop that only stops
     * if the user has entered a valid username and password
     */
    public void verifyLogin(){
        try {
            boolean verified = false;

            while(!verified) {
                User readUser = (User) socketIn.readObject();

                if (serverModel.verifyUser(readUser)) {
                    socketOut.writeObject("verified");
                    System.out.println("User Verified!");
                    verified = true;
                    return;
                } else {
                    socketOut.writeObject("notVerified");
                }

                socketOut.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Exports all the object of tools to the client
     */
    public void exportToolsToClient(){
        try {
            int tools = 0;
            for(Item i : serverModel.getMyShop().getInventory().getItemList()) {
                tools++;
            }

            socketOut.writeObject(String.valueOf(tools));

            for (Item i : serverModel.getMyShop().getInventory().getItemList()) {
                socketOut.writeObject(i);
            }

            System.out.println("Successful tools export to Client");
        }catch(IOException e){
            System.out.println("Tool Exporting Error");
            e.printStackTrace();
        }
    }




}
