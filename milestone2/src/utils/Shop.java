package utils;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * This is the shop class which holds the order list, supplier list and
 * the inventory of the shop
 */
public class Shop implements Constants{
    /**
     * list of orders placed for items of quantity under 40
     */
    private ArrayList<Order> orderList;

    /**
     * list of suppliers that supply to shop
     */
    private ArrayList<Supplier> supplierList;

    /**
     * inventory where all items are stored
     */
    private Inventory inventory;

    /**
     * Constructs a Shop object with the specified values for an array list
     * of orders, array list of suppliers and an inventory object.
     * The values of the data fields are supplied by the given parameters.
     * @param orders order list of orders placed
     * @param suppliers supplier list
     * @param inventory inventory object which holds all items of the shop
     */
    public Shop(ArrayList<Order> orders, ArrayList<Supplier> suppliers, Inventory inventory){
        this.orderList = orders;
        this.supplierList = suppliers;
        this.inventory = inventory;
    }

    /**
     * imports suppliers from suppliers.txt
     * @throws FileNotFoundException
     */
    public void importSuppliers() throws FileNotFoundException{
        String directory = System.getProperty("user.dir");
        String path = directory+File.separator+"suppliers.txt";
        File file = new File(path);
        Scanner input = new Scanner(file);

        String rawData;
        String[] splitData;

        while(input.hasNextLine()){
            rawData = input.nextLine();
            splitData = rawData.split(";");

            supplierList.add(new Supplier(Integer.parseInt(splitData[0]), splitData[1], splitData[2], splitData[3]));
        }
    }

    /**
     * imports tools from items.txt
     * @throws FileNotFoundException
     */
    public void importTools() throws FileNotFoundException{
        String directory = System.getProperty("user.dir");
        String path = directory+File.separator+"items.txt";
        File file = new File(path);
        Scanner input = new Scanner(file);

        String rawData;
        String[] splitData;

        while(input.hasNextLine()){
            rawData = input.nextLine();
            splitData = rawData.split(";");

            Supplier matchedSupplier = searchSupplier(Integer.parseInt(splitData[4]));

            inventory.getItemList().add(new Item(Integer.parseInt(splitData[0]), splitData[1], Integer.parseInt(splitData[2]), Double.parseDouble(splitData[3]), matchedSupplier));
        }
    }

    /**
     * searches supplier from supplier list adn returns
     * a supplier object
     * @param id id of supplier being searched
     * @return supplier object
     */
    public Supplier searchSupplier(int id) {
        for(int i = 0; i < supplierList.size(); i++){
            if(supplierList.get(i).getId() == id)
                return supplierList.get(i);
        }
        return null;
    }

    /**
     * prints all tools (inventory)
     */
    public void exportAllTools(){
        printHeader();
        System.out.println();
        for(int i = 0; i < inventory.getItemList().size(); i++)
            System.out.println(inventory.getItemList().get(i).toString());
    }

    /**
     * prints the menu
     * @return user input for the menu
     */
    public int menu(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nMenu:");
        System.out.println("1. List all tools in inventory.");
        System.out.println("2. Search for tool by toolName.");
        System.out.println("3. Search for tool by toolID.");
        System.out.println("4. Check item quantity.");
        System.out.println("5. Sale of an Item (Decrease item quantity).");
        System.out.println("6. Add a tool to inventory.");
        System.out.println("7. Remove a tool from inventory.");
        System.out.println("8. Quit.\n");

        int num = input.nextInt();
        while(num < 1 || num > 8){
            System.out.println("Please enter number between 1 - 8.");
            num = input.nextInt();
        }

        return num;
    }

    /**
     * prints header
     */
    public void printHeader(){
        System.out.printf("%-5s %-20s %-10s %-7s %-15s\n", "ID", "Name", "Quantity", "Price", "Supplier ID" );
    }


    /**
     * prints the orders placed
     * @throws IOException
     */
    public void exportOrders() throws IOException{
        String directory = System.getProperty("user.dir");
        String file = directory+File.separator+"orders.txt";

        FileWriter writer = new FileWriter(file);

        writer.write("\n**************************************\n");
        for(int i = 0; i < orderList.size(); i++){
            writer.write(orderList.get(i).toString());
            writer.write("\n**************************************\n");
        }
        writer.close();
    }

    /**
     * adds a new tool to inventory
     */
    public void addTool(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter ID of new tool:");
        int ID = 1000;
        while(true) {
            if (input.hasNextInt()) {
                ID = input.nextInt();
                if(!inventory.isTool(ID))
                    break;
            }
            System.out.println("Enter an integer:");
            input.nextLine();
        }

        input.nextLine();

        System.out.println("Enter name of the new tool:");
        String name;
        while(true){
            if(input.hasNextLine()) {
                name = input.nextLine();
                if(!inventory.isTool(name))
                    break;
            }
            System.out.println("Tool of this name exists, enter a new name:");
        }

        System.out.println("Enter quantity of the new tool:");
        int quantity = 0;
        while(true) {
            if (input.hasNextInt()) {
                quantity = input.nextInt();
                break;
            }
            System.out.println("Enter an integer:");
            input.nextLine();
        }


        System.out.println("Enter price of the new tool:");
        double price;
        while(true) {
            if (input.hasNextDouble()) {
                price = input.nextDouble();
                break;
            }
            System.out.println("Enter an integer:");
            input.nextLine();
        }

        System.out.println("Enter SupplierID of the new tool:");
        int suppID;
        while(true) {
            if (input.hasNextInt()) {
                suppID = input.nextInt();
                if(isSupplier(suppID))
                    break;
            }
            System.out.println("Enter an integer:");
            input.nextLine();
        }

        Supplier matchedSupplier = searchSupplier(suppID);

        inventory.getItemList().add(new Item(ID, name, quantity, price, matchedSupplier));
    }

    /**
     * removes tools from inventory by decreasing the quantity of the tool
     * requested
     */
    public void removeTool(){
        Scanner input = new Scanner(System.in);
        int num = 0;

        while(num < 1 || num > 2){
            System.out.println("Please choose one of the following:");
            System.out.println("1. Enter item ID");
            System.out.println("2. Enter item Name");
            num = input.nextInt();
        }

        Item toolRemoving;
        if(num == 1){
            toolRemoving = inventory.searchToolId();
        }
        else{
            toolRemoving = inventory.searchToolName();
        }

        inventory.getItemList().remove(toolRemoving);

        System.out.println("Tool removed!");
    }

    /**
     * checks if a supplier exists for id requested
     * @param id id of supplier being checked to exist
     * @return true if supplier of given id exits, false otherwise
     */
    public boolean isSupplier(int id){
        for(int i = 0; i < supplierList.size(); i++){
            if(id == supplierList.get(i).getId())
                return true;
        }
        return false;
    }


    //getters and setters

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public ArrayList<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(ArrayList<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
