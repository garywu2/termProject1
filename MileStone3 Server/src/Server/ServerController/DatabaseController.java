package Server.ServerController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Server.DatabaseModel.DatabaseModel;
import utils.Supplier;
import utils.User;

public class DatabaseController implements DatabaseCredentials {
	private Connection myConnection;
	private DatabaseModel databaseModel;

	public void initializeConnection() {
		try {
			setMyConnection(DriverManager.getConnection(DB_URL, USERNAME, PASSWORD));
			databaseModel = new DatabaseModel(myConnection);
		} catch (SQLException e) {
			System.out.println("Unable to open database.");
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			myConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
