package utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the inventory class which holds all the items
 * of the shop
 */
public class Inventory {

    /**
     * list of object items in shop
     */
    private ArrayList<Item> itemList;

    /**
     * constructs an inventory object with an array list of object items
     * @param items array list of object items
     */
    public Inventory(ArrayList<Item> items){
        itemList = items;
    }

    /**
     * searches tool by the name requested
     * @return object item searched for
     */
    public Item searchToolName(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the tool:");
        String name;

        while(true) {
            name = input.nextLine().toLowerCase();
            for (int i = 0; i < itemList.size(); i++) {
                if (name.equals(itemList.get(i).getToolName().toLowerCase()))
                    return itemList.get(i);
            }
            System.out.println("Tool of that name not found. Enter another name:");
        }
    }

    /**
     * searches tool by ID requested
     * @return object item searched for
     */
    public Item searchToolId(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the ID of the tool:");
        int id;

        while(true) {
            if(input.hasNextInt()) {
                id = input.nextInt();
                for (int i = 0; i < itemList.size(); i++) {
                    if (id == itemList.get(i).getToolId())
                        return itemList.get(i);
                }
                System.out.println("Tool of that ID not found. Enter another ID:");
            }
            System.out.println("Please enter an integer.");
            input.nextLine();
        }
    }

    public int searchToolIndex(int id){
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).getToolId() == id)
                return i;
        }
        return -1;
    }

    /**
     * checks and prints the quantity for item requested
     */
    public void checkItemQuantity(){
        System.out.println("Please choose one of the following:");
        System.out.println("1. Enter item ID");
        System.out.println("2. Enter item Name");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();

        if(num == 1){
            Item searchedItem = searchToolId();
            System.out.println("Quantity of " + searchedItem.getToolName() + " is " + searchedItem.getToolQuantity());
        }
        else{
            Item searchedItem = searchToolName();
            System.out.println("Quantity of " + searchedItem.getToolName() + " is " + searchedItem.getToolQuantity());
        }

    }

    /**
     * decreases the quantity of an item by the specified amount
     */
    public void decreaseItemQuantity(){
        System.out.println("Please choose one of the following:");
        System.out.println("1. Enter item ID");
        System.out.println("2. Enter item Name");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        Item searchedItem;
        if(num == 1){
            searchedItem = searchToolId();

            System.out.println("Enter quantity to be decreased:");
            num = input.nextInt();

            System.out.println("Previously, quantity of " + searchedItem.getToolName() + " was " + searchedItem.getToolQuantity());
            searchedItem.setToolQuantity(searchedItem.getToolQuantity() - num);
            System.out.println("Now, quantity of " + searchedItem.getToolName()  + " is " + searchedItem.getToolQuantity());
        }
        else{
            searchedItem = searchToolName();

            System.out.println("Enter quantity to be decreased:");
            num = input.nextInt();

            System.out.println("Previously, quantity of " + searchedItem.getToolName() + " was " + searchedItem.getToolQuantity());
            searchedItem.setToolQuantity(searchedItem.getToolQuantity() - num);
            System.out.println("Now, quantity of " + searchedItem.getToolName()  + " is " + searchedItem.getToolQuantity());
        }
    }

    /**
     * increases the quantity of an item by specified amount
     * @param itemOrdered item who's quantity is to be changed
     * @param addedQuantity amount of item increased
     */
    public void increaseItemQuantity(Item itemOrdered, int addedQuantity){
        itemOrdered.setToolQuantity(itemOrdered.getToolQuantity() + addedQuantity);
    }

    /**
     * sets quantity of tool ordered to 50
     * @param itemOrdered item who's quantity is being set
     */
    public void increaseItemQuantityByOrder(Item itemOrdered){
        itemOrdered.setToolQuantity(50);
    }

    /**
     * places a new order using an items ordered list and a list of all orders
     * @param itemsOrdering list of items being ordered
     * @param orderList list of all orders
     */
    public void placeOrder(ArrayList<Item> itemsOrdering, ArrayList<Order> orderList){
        orderList.add(new Order(Constants.generateFiveDigit(), Constants.date, itemsOrdering));
    }

    /**
     * checks if a tool exists for given id
     * @param id id being checked
     * @return true if tool of that id exists, false otherwise
     */
    public boolean isTool(int id){
        for(int i = 0; i < getItemList().size(); i++){
            if(id == getItemList().get(i).getToolId())
                return true;
        }
        return false;
    }

    /**
     * checks if a tool exists for given name
     * @param name name being checked
     * @return true if tool of that name exists, false otherwise
     */
    public boolean isTool(String name){
        for(int i = 0; i < getItemList().size(); i++){
            if(name.toLowerCase().equals(getItemList().get(i).getToolName().toLowerCase()))
                return true;
        }
        return false;
    }


    /**
     * checks quantity of all items in stock and places orders for
     * those under quantity of 40
     * @param orderList list of orders of the shop
     */
    public void checkAllStockQuantity(ArrayList<Order> orderList){
        ArrayList<Item> itemsOrdering = new ArrayList<>();

        for(int i = 0; i < getItemList().size(); i++){
            if(getItemList().get(i).lowQuantity()) {
                itemsOrdering.add(new Item(getItemList().get(i).getToolId(), getItemList().get(i).getToolName(), getItemList().get(i).getToolQuantity(), getItemList().get(i).getToolPrice(), getItemList().get(i).getToolSupplier()));
                increaseItemQuantityByOrder(getItemList().get(i));
            }
        }
        if(itemsOrdering.size() > 0) {
            placeOrder(itemsOrdering, orderList);
            System.out.println("\nOrders were placed for item with quantity under 40!");
        }
    }

    //getters and setters
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }



}
