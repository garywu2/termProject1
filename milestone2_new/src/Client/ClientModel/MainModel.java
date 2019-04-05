package Client.ClientModel;

import Server.ServerModel.Item;

import java.util.ArrayList;


/**
 * This is the main model of the class which
 * holds the array list of items
 * Overall the purpose of this class is to hold
 * the arrayList of Items
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class MainModel {

    private ArrayList<Item> items;

    public MainModel(){
        items = new ArrayList<>();
    }

    public boolean idExists(int id){
        for(Item i: items){
            if(id == i.getToolId())
                return true;
        }

        return false;
    }


    public ArrayList<Item> getItems() {
        return items;
    }

}
