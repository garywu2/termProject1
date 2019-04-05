package utils;

import java.util.ArrayList;

/**
 * This is the Order class which keep track of each order placed
 * for items under quantity of 40
 */
public class Order {

    /**
     * ID of an order
     */
    int orderId;

    /**
     * Date order was placed on
     */
    String orderDate;

    /**
     * Items ordered in the order
     */
    ArrayList<Item> itemsOrdered;

    /**
     * Constructs an object Order with specified values of  id, date,
     * and a list of items
     * @param id ID of order
     * @param date Date order is being placed on
     * @param items list of items being ordered
     */
    public Order(int id, String date, ArrayList<Item> items){
        orderId = id;
        orderDate = date;
        itemsOrdered = items;
    }

    /**
     * formats order information into a string
     * @return string consisting of order information
     */
    public String toString(){
        String result = String.format("%-30s %-30d\n%-30s %-30s\n\n", "ORDER ID:", orderId, "Date Ordered:", orderDate);

        for(int i = 0; i < itemsOrdered.size(); i++)
            result += String.format("%-30s %-30s\n%-30s %-30d\n%-30s %-30s\n\n",  "Item description:", itemsOrdered.get(i).getToolName(), "Amount ordered:", 50 - itemsOrdered.get(i).getToolQuantity(), "Supplier:", itemsOrdered.get(i).getToolSupplier().getName());

        return result;
    }

    //getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<Item> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(ArrayList<Item> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }
}
