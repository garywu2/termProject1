package Server.ServerController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for managing the thread pool as well as
 * the ServerCommunicationController. Everytime, a new client connects,
 * this class makes an instance of the ServerCommunicationController
 * for the client in a new thread
 * @author Harsohail Brar
 * @version 4.10.0
 * @since April 12, 2019
 */
public class ServerController {

    private static final int PORT = 9000;
    private ServerSocket serverSocket;
    private DatabaseController databaseController;
    private ExecutorService pool;

    /**
     * Constructor for the server controller which creates new Server sockets
     * and database controllers and starts the thread pool with 10 threads
     * Also prints out the ip info of server
     */
    public ServerController() {
        try {
            serverSocket = new ServerSocket(PORT);
            databaseController = new DatabaseController();
            pool = Executors.newFixedThreadPool(10);
            System.out.println("Server is running");
            printIPInfo();
            System.out.println("********");
        } catch (IOException e) {
            System.out.println("ServerController: Create a new socket error");
            e.printStackTrace();
        }
    }

    /**
     * Main function for Server controller which constructs the server controller 
     * and starts communication with clients
     * @param args
     */
    public static void main(String[] args) {
        ServerController myServer = new ServerController();
        myServer.communicateWithClient();
    }

    /**
     * Communicates with clients using threads
     */
    public void communicateWithClient() {
        try {
            while (true) {
                ServerCommunicationController scc = new ServerCommunicationController(serverSocket.accept(), this);

                System.out.println("New Client Connected");

                pool.execute(scc);
            }
        } catch (IOException e) {
            System.out.println("ServerController: CommunicateWithClient error");
            e.printStackTrace();
        }
    }

    /**
     * Prints the ip adresss of the server 
     */
    public void printIPInfo() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("You current IP address: " + ip);
        } catch (UnknownHostException e) {
            System.out.println("IP Print error");
            e.printStackTrace();
        }
    }

    //GETTERS AND SETTERS

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}
