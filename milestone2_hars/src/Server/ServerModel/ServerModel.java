package Server.ServerModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ServerModel {

    private Shop myShop;
    private ArrayList<User> users;

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

        System.out.println("Successful Import!");

        users = new ArrayList<>();
        users.add(new User("hi", "123"));
    }

    public boolean verifyUser(User user){
        for(User u: users){
            if(u.compareUser(user))
                return true;
        }
        return false;
    }

    public Shop getMyShop() {
        return myShop;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
