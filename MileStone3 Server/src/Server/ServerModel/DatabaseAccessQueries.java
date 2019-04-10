package Server.ServerModel;

public interface DatabaseAccessQueries {
	public static final String SQL_GET_USER = "SELECT * FROM users WHERE userID =? and userPass =?";
	public static final String SQL_ADD_USER = "INSERT INTO users (userID, userPass) values(?,?)";
	public static final String SQL_GET_SUPPLIER_BY_ID = "SELECT * FROM suppliers WHERE supplierID =?";
	public static final String SQL_ADD_ITEM = "INSERT INTO items (itemID, itemName, itemQuantity, itemPrice, itemSupplierID) values(?,?,?,?,?)";
	public static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM items WHERE itemID =?";
	public static final String SQL_GET_ITEM_BY_NAME = "SELECT * FROM items WHERE itemName =?";
	public static final String SQL_GET_ALL_ITEMS = "SELECT * FROM items";
	public static final String SQL_DECREASE_ITEM_QUANTITY = "UPDATE items SET itemQuantity =? WHERE itemID =?";
	public static final String SQL_REMOVE_ITEM = "DELETE FROM items WHERE itemID =?";
}
