package Client.ClientModel;

//import Server.ServerModel.Item;
import utils.*;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;


/**
 * This is the main model of the class which
 * holds the array list of items
 * Overall the purpose of this class is to hold
 * the arrayList of Items
 * @author  Harsohail Brar
 * @version 4.10.0
 * @since April 5, 2019
 */
public class MainModel {
    private DefaultTableModel tableModel;

//    /**
//     * Checks if id already exists
//     * @param id id being checked
//     * @return true if exists, false otherwise
//     */
//    public boolean idExists(int id){
//        for(Item i: items){
//            if(id == i.getToolId())
//                return true;
//        }
//
//        return false;
//    }

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
