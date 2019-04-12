package Server.ServerController;

import java.sql.*;
import Server.ServerModel.DatabaseModel;

/**
 * This class is responsible for the connection to the database and
 * the database model
 * @author Ryan Holt
 * @since April 12, 2019
 */
public class DatabaseController implements DatabaseCredentials{

    private Connection myConnection;
    private DatabaseModel databaseModel;

    public DatabaseController(){
        initializeConnection();
    }

    public void initializeConnection() {
        try {
            setMyConnection(DriverManager.getConnection(DB_URL, USERNAME, PASSWORD));
            databaseModel = new DatabaseModel(myConnection);
        } catch (SQLException e) {
            System.out.println("Unable to open database.");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            myConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GETTERS AND SETTERS
    /**
     * @return the databaseModel
     */
    public DatabaseModel getDatabaseModel() {
        return databaseModel;
    }

    /**
     * @param databaseModel the databaseModel to set
     */
    public void setDatabaseModel(DatabaseModel databaseModel) {
        this.databaseModel = databaseModel;
    }

    /**
     * @return the myConnection
     */
    public Connection getMyConnection() {
        return myConnection;
    }

    /**
     * @param myConnection the myConnection to set
     */
    public void setMyConnection(Connection myConnection) {
        this.myConnection = myConnection;
    }

}
