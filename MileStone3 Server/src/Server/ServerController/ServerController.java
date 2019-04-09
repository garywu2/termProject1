package Server.ServerController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {

    private static final int PORT = 9000;
    private ServerSocket serverSocket;
    private DatabaseController databaseController;
    private ExecutorService pool;

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

    public static void main(String[] args) {
        ServerController myServer = new ServerController();
        myServer.communicateWithClient();
    }

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
