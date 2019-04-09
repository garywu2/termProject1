package Server.ServerModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import utils.*;
/**
 * This is the server model for the shop which helps successful import all
 * items and creates the new shop .
 * @author  Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 *
 */
public class ServerModel {

    private Shop myShop;
    private ArrayList<User> users;

    /**
     * Constructor for the shop which creates new Array lists of
     * orders suppliers and items and create new inventory.  Also
     * creates the shop and imports all the suppliers and tools fro
     */
    public ServerModel(){
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        Inventory inventory = new Inventory(items);
        
        myShop = new Shop(orders, suppliers, inventory);
        try {
            myShop.importSuppliers();
            myShop.importTools();
        }catch (FileNotFoundException e){
            System.out.println("Server data importing error");
            e.printStackTrace();
        }

        System.out.println("Successful Import from files");

        users = new ArrayList<>();
        users.add(new User("hi", "123"));
    }

    /**
     * Checks to see if the user entered is correct
     * @param user the user name of the user
     * @return returns true if the User exists otherwise false
     */
    public boolean verifyUser(User user){
        for(User u: users){
            if(u.compareUser(user))
                return true;
        }
        return false;
    }

    //getters and setters
    public Shop getMyShop() {
        return myShop;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
