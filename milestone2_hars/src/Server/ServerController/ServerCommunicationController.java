package Server.ServerController;

import Server.ServerModel.User;
import Server.ServerModel.Item;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import Server.ServerModel.ServerModel;


public class ServerCommunicationController {

    private Socket aSocket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ServerSocket serverSocket;
    private ServerModel serverModel;

    public ServerCommunicationController(){
        try{

            serverSocket = new ServerSocket(1234);
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

    public static void main(String[] args){
        ServerCommunicationController myServer = new ServerCommunicationController();

        myServer.createInputStream(); //after connects with client (client creates output stream)

        //myServer.exportToolsToClient();

        myServer.verifyLogin();

        myServer.communicateWithClient();
    }

    public void communicateWithClient(){
        while(true){

        }
    }

    public void createInputStream(){
        try{
            socketIn = new ObjectInputStream(aSocket.getInputStream());
        }catch (IOException e){
            System.out.println("Error creating server output stream");
            e.printStackTrace();
        }
    }

    public void verifyLogin(){
        try {
            boolean verified = false;

            while(!verified) {
                User readUser = (User) socketIn.readObject();
                System.out.println("read");
                if (serverModel.verifyUser(readUser)) {
                    socketOut.writeObject("verified");
                    verified = true;
                } else {
                    System.out.println("not veri");
                    socketOut.writeObject("notVerified");
                }

                socketOut.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

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
