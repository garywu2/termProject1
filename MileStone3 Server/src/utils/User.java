package utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is responsible for holding a user's
 * data  such as username, password etc.
 * 
 * @author  Gary Wu
 * @version 4.10.0
 * @since April 5, 2019
 */
public class User implements Serializable {

    //MEMBER VARIABLES
    private static final long serialVersionUID = 3L;
    private String username;
    private String password;
    private ArrayList<Item> itemsOrdered;

    /**
     * Constructs a user object
     * @param u username
     * @param p password
     */
    public User(String u, String p){
        username = u;
        password = p;
        itemsOrdered = new ArrayList<>();
    }

    /**
     * Compares if the username and password match the one set and if yes
     * returns true else false
     * @param u is the User entered
     * @return true or false dependeing if the user exisits 
     */
    public boolean compareUser(User u){
        if(u.username.equals(this.username) && u.password.equals(this.password))
            return true;

        return false;
    }

    //GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Item> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(ArrayList<Item> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }
}
