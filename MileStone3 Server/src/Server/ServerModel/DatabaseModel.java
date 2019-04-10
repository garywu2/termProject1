package Server.ServerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import utils.*;

/**
 * TODO
 *
 * @author Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class DatabaseModel implements DatabaseAccessQueries{

	private Connection myConnection;
	private int userId = 2;
	private DefaultTableModel tableModel;
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
	public boolean verifyUser(User user) {
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

	public void createDefaultTableModel() {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_ALL_ITEMS)) {
			try (ResultSet rs = pStmt.executeQuery()) {
				setTableModel(createTableFromRS(rs));
			}

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

	/**
	 * TODO remove and implement table model^
	 *
	 * @return
	 */
	public ArrayList<Item> getItemsFromDB() {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_ALL_ITEMS)) {
			ArrayList<Item> items = new ArrayList<>();
			try (ResultSet rs = pStmt.executeQuery()) {
				while (rs.next()) {
					items.add(new Item(rs));
				}
			}
			System.out.println(items.size());
			return items;
		} catch (SQLException e) {
			System.out.println("Getting items from DB error");
			e.printStackTrace();
		}
		return null;
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
			System.out.println("Supplier search from DB error");
			e.printStackTrace();
		}
		return null;
	}

	public Item searchItemByName(String name) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_ITEM_BY_NAME)) {
			pStmt.setString(1, name);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next())
					return new Item(rs);
			}
		} catch (SQLException e) {
			System.out.println("Item search by name from DB error");
			e.printStackTrace();
		}
		return null;
	}

	public Item searchItemByID(int id) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_ITEM_BY_ID)) {
			pStmt.setInt(1, id);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next())
					return new Item(rs);
			}
		} catch (SQLException e) {
			System.out.println("Item search by ID from DB error");
			e.printStackTrace();
		}
		return null;
	}

	public boolean decreaseItemQuantity(int id, int newQuantity) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_DECREASE_ITEM_QUANTITY)) {
			pStmt.setInt(1, newQuantity);
			pStmt.setInt(2, id);
			int status = pStmt.executeUpdate();
			if (status == 0) {
				System.out.println("Item already deleted!");
				return false;
			} else {
				System.out.println("Item Quantity in DB Decreased!");
			}
		} catch (SQLException e) {
			System.out.println("Item search by ID from DB error");
			e.printStackTrace();
		}
		return true;
	}

	public boolean itemExists(int id) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_ITEM_BY_ID)) {
			pStmt.setInt(1, id);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next())
					return true;
			}
		} catch (SQLException e) {
			System.out.println("Item search by ID from DB error");
			e.printStackTrace();
		}
		return false;
	}

	public boolean supplierExists(int supplierIdNumber) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_GET_SUPPLIER_BY_ID)) {
			pStmt.setInt(1, supplierIdNumber);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next())
					return true;
			}
		} catch (SQLException e) {
			System.out.println("Supplier search by ID from DB error");
			e.printStackTrace();
		}
		return false;
	}

	public void addItemToDB(Item newItem) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_ADD_ITEM)) {
			pStmt.setInt(1, newItem.getToolId());
			pStmt.setString(2, newItem.getToolName());
			pStmt.setInt(3, newItem.getToolQuantity());
			pStmt.setDouble(4, newItem.getToolPrice());
			pStmt.setInt(5, newItem.getToolSupplierIdNumber());
			pStmt.executeUpdate();
			System.out.println("Item added to DB");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean removeItemFromDB(int id) {
		try (PreparedStatement pStmt = myConnection.prepareStatement(SQL_REMOVE_ITEM)) {
			pStmt.setInt(1, id);
			int status = pStmt.executeUpdate();
			if (status == 0) {
				System.out.println("Item already deleted!");
				return false;
			} else {
				System.out.println("Item deleted from DB!");
			}
		} catch (SQLException e) {
			System.out.println("Item remove by ID from DB error");
			e.printStackTrace();
		}
		return true;
	}

	// getters and setters

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