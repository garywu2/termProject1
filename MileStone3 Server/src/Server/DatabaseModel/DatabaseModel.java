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
	private int userId = 2;
	private DefaultTableModel tableModel;
	private Connection myConnection;
	private final String SQL_GET_USER = "SELECT * FROM users WHERE username =? and password =?";
	private final String SQL_ADD_USER = "INSERT INTO users (username, password) values(?,?)";
	private final String SQL_GET_SUPPLIER_BY_ID = "SELECT supplierId FROM suppliers WHERE supplierId =?";
	private final String SQL_ADD_ITEM = "INSERT INTO items (Item_ID, Name, Quantity, Price, Supplier_ID) values(?,?,?,?,?)";
	private final String SQL_GET_ITEM_BY_ID = "SELECT * FROM items WHERE Item_ID =?";
	private final String SQL_GET_ITEM_BY_NAME = "SELECT * FROM items WHERE Name =?";

	/**
	 * TODO
	 */
	public DatabaseModel(Connection c) {
		myConnection = c;
	}

	/**
	 * Checks to see if the user entered is correct
	 * 
	 * @param user the user object to be verified
	 * @return returns true if the User exists otherwise false
	 */
	public boolean userExists(User user) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_USER)) {
			pStmt.setString(1, user.getUsername());
			pStmt.setString(2, user.getPassword());
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					System.out.println("User is logged in");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void addUser(User user) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_ADD_USER)) {
			pStmt.setInt(1, userId++);
			pStmt.setString(2, user.getUsername());
			pStmt.setString(3, user.getPassword());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to add user. You must enter a unique username.");
			e.printStackTrace();
		}
	}

	public Supplier searchSupplierByID(int supplierIdNumber) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_SUPPLIER_BY_ID)) {
			pStmt.setInt(1, supplierIdNumber);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					return new Supplier(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean supplierExists(int supplierIdNumber) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_SUPPLIER_BY_ID)) {
			pStmt.setInt(1, supplierIdNumber);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Item addItem(Item item) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_ADD_ITEM)) {
			pStmt.setInt(1, item.getToolId());
			pStmt.setString(2, item.getToolName());
			pStmt.setInt(1, item.getToolQuantity());
			pStmt.setDouble(1, item.getToolPrice());
			pStmt.setInt(1, item.getToolSupplierIdNumber());
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					return new Item(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public Item searchItemByName(String name){
        try {
            Statement myStat = myConnection.createStatement();
            String query = ;
            ResultSet rs = myStat.executeQuery(query);

            if(rs.next())
                return new Item(rs);
        } catch (SQLException e) {
            System.out.println("Supplier search from DB error");
            e.printStackTrace();
        }
        return null;
    }

    public Item searchItemByID(int id){
        try {
            Statement myStat = myConnection.createStatement();
            String query = "SELECT * FROM items WHERE itemID = '" + id + "'";
            ResultSet rs = myStat.executeQuery(query);

            if(rs.next())
                return new Item(rs);
        } catch (SQLException e) {
            System.out.println("Supplier search from DB error");
            e.printStackTrace();
        }
        return null;
    }

	public void createDefaultTableModel(Statement myStat) {
		try {
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

	// TODO
	// public defaultTableModel createOrderList(Date, Statement)

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
