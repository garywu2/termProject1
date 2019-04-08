package Server.DatabaseModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import utils.*;

/**
 * TODO
 * 
 * @author Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 *
 */
public class DatabaseModel {

	private Connection myConnection;
	private Shop myShop;
	private int userId = 2;
	private DefaultTableModel tableModel;

	/**
	 * TODO
	 */
	public DatabaseModel(Connection c) {
		myConnection = c;
		myShop = new Shop(c);
	}

	/**
	 * Checks to see if the user entered is correct
	 * 
	 * @param user the user object to be verified
	 * @return returns true if the User exists otherwise false
	 */
	public boolean verifyUser(User user) {
		try {
			Statement myStat = myConnection.createStatement();
			String query = "SELECT * FROM users WHERE username = '" + user.getUsername() + "' and password ='"
					+ user.getPassword() + "'";
			ResultSet rs = myStat.executeQuery(query);
			if (rs.next()) {
				System.out.println("User is logged in");
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addUser(User user) {
		try {
			String query = "INSERT INTO users (username, password) values(?,?)";
			PreparedStatement myStat = myConnection.prepareStatement(query);
			myStat.setInt(1, userId);
			userId++;
			myStat.setString(2, user.getUsername());
			myStat.setString(3, user.getPassword());
			myStat.executeUpdate();
			myStat.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to add user. You must enter a unique username.");
			e.printStackTrace();
			return false;
		}
	}


	
	public void createDefaultTableModel() {
		try {
			Statement myStat = myConnection.createStatement();
			String query = "SELECT * FROM items";
			ResultSet rs = myStat.executeQuery(query);
			setTableModel(createTableFromRS(rs));
		} catch (SQLException e) {
			System.out.println("Unable to create table");
			e.printStackTrace();
			setTableModel(null);
		}
	}

	public DefaultTableModel createTableFromRS(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		Vector<String> columnNames = new Vector<String>();
		int numCol = metaData.getColumnCount();
		for (int column = 1; column <= numCol; column++) {
			columnNames.add(metaData.getColumnName(column));
		}
		Vector<Vector<Object>> dataTable = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int colIndex = 1; colIndex <= numCol; colIndex++) {
				vector.add(rs.getObject(colIndex));
			}
			dataTable.add(vector);
		}
		return new DefaultTableModel(dataTable, columnNames) {

			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
	}

	// getters and setters
	public Shop getMyShop() {
		return myShop;
	}

	/**
	 * @return the tableModel
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel the tableModel to set
	 */
	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
}
