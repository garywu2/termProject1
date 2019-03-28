import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class acts as the front end of the program which holds
 * the main function and constructs the shop object
 * @author Harsohail Brar
 * @version 1.0
 * @since February 7, 2019
 */
public class FrontEnd {

    /**
     * main function which runs the program and constructs the shop object
     * @param args arguments from command line
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("ORDERS.TXT IS CREATED IN PROJECT DIRECTORY!");
        System.out.println("PLACE ITEMS.TXT AND SUPPLIERS.TXT IN PROJECT DIRECTORY TO BE READ!");

        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        Inventory inventory = new Inventory(items);

        Shop myShop = new Shop(orders, suppliers, inventory);

        myShop.importSuppliers();
        myShop.importTools();

        int input;
        while(true){
            input = myShop.menu();
            switch (input){
                case 1: myShop.exportAllTools();
                    break;
                case 2: Item searchedItem = myShop.getInventory().searchToolName();
                    myShop.printHeader();
                    System.out.println(searchedItem.toString());
                    break;
                case 3: searchedItem = myShop.getInventory().searchToolId();
                    myShop.printHeader();
                    System.out.println(searchedItem.toString());
                    break;
                case 4: myShop.getInventory().checkItemQuantity();
                    break;
                case 5: myShop.getInventory().decreaseItemQuantity();
                    break;
                case 6: myShop.addTool();
                    break;
                case 7: myShop.removeTool();
                    break;
                case 8: System.exit(1);
            }

            myShop.getInventory().checkAllStockQuantity(orders);
            myShop.exportOrders();
        }


    }

}
