package utils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the supplier class which holds information of each supplier
 */
public class Supplier implements Serializable {

    private static final long serialVersionUID = 2L;
    /**
     * ID of supplier
     */
    private int id;

    /**
     * Name of supplier
     */
    private String name;

    /**
     * Address of supplier
     */
    private String address;

    /**
     * Sales Contact of Supplier
     */
    private String salesContact;
    /**
     * TODO
     * @param rs
     * @throws SQLException
     */
    public Supplier(ResultSet rs){
    	try {
			id =rs.getInt("supplierId");
			name = rs.getString("name");
			address = rs.getString("address");
			salesContact = rs.getString("salesContact");
		} catch (SQLException e) {
			System.out.println("Supplier creation error (ResultSet)");
			e.printStackTrace();
		}
    }

    /**
     * Constructs a supplier object with specified values of id, name, address,
     * and sales contact
     * @param id ID of supplier
     * @param name Name of supplier
     * @param address Address of supplier
     * @param salesContact Sales Contact of supplier
     */
    public Supplier(int id, String name, String address, String salesContact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salesContact = salesContact;
    }

    /**
     * Formats supplier information into a string
     * @return string consisting of supplier information
     */
    public String toString() {
        return id + " " + name + " " + address + " " + salesContact;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

