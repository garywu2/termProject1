package Client.ClientController;

import Client.ClientView.MainView;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The MAIN GUIController class essentially holds all code for that
 * action listeners of the various buttons such as browse, it holds
 * objects of the main view and model
 * Overall the purpose of this class is to add the action listeners
 * to the various buttons in the main view
 *
 * @author Harsohail Brar
 * @version 4.10.0
 * @since April 5, 2019
 */
public class MainGUIController extends GUIController {

    //MEMBER VARAIBLES
    private MainView mainView;

    /**
     * Constructor for the MainGUIController class which essentially adds
     * action listeners to the different buttons
     *
     * @param v this is the MainView Object
     */
    public MainGUIController(MainView v, ClientController cc) {
        super(cc);
        mainView = v;

        mainView.addBrowseListener(new BrowseListen());
        mainView.addSearchByIDListener(new SearchByIDListen());
        mainView.addSearchByNameListener(new SearchByNameListen());
        mainView.addSaleListener(new SaleListen());
        mainView.addAddListener(new AddListen());
        mainView.addRemoveListener(new RemoveListen());
        mainView.addRefreshListener(new RefreshListen());
    }

    /**
     * This is the class for the browse button action listener
     */
    class BrowseListen implements ActionListener {

        /**
         * When this button is pressed the action performed a
         * list of the many tools will now become visible to the user
         * and it will also add the action listener associated with the list
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getBrowseButton()) {
                try {
                    if (mainView.getTable() == null) {
                        mainView.createTable();
                    }
                } catch (Exception f) {
                    System.out.println("MainGUIController: BrowseListen error");
                    f.printStackTrace();
                }
            }
        }
    }

    /**
     * This is the class for the Search by ID button action listener
     */
    class SearchByIDListen implements ActionListener {

        /**
         * When the button is pressed the user will be prompted to enter the
         * ID of a tool and it will go through all the tools and try to match
         * the tool ID with one in the database and if it matches
         * then the elements of the tool will appear in a dialog box
         * else tells the user it does not exist
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getSearchByIDButton()) {
                int inputID = intInputPrompt("Enter tool ID:");

                try {
                    //sending
                    clientController.getSocketOut().writeObject("searchByID");

                    clientController.getSocketOut().writeObject(String.valueOf(inputID));

                    //receiving
                    Item readItem = (Item) clientController.getSocketIn().readObject();

                    //prompt
                    if (readItem != null) {
                        JOptionPane.showMessageDialog(null, promptItem(readItem));
                    } else {
                        JOptionPane.showMessageDialog(null, "Tool not found!");
                    }

                    //update table
                    importItemsFromServer();
                    mainView.updateTable();
                } catch (Exception f) {
                    System.out.println("MainGUIController SearchByID error");
                    f.printStackTrace();
                }
            }
        }

    }

    /**
     * This is the class for the search by name action listener
     */
    class SearchByNameListen implements ActionListener {

        /**
         * When the button is pressed the user will be prompted to enter the
         * name of a tool and it will go through all the tools and try to match
         * the tool name with one in the database and if it matches
         * then the elements of the tool will appear in a dialog box
         * else tells the user it does not exist
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getSearchByNameButton()) {
                //inputs
                String input = JOptionPane.showInputDialog("Please enter tool Name:");

                try {
                    //sending
                    clientController.getSocketOut().writeObject("searchByName");

                    clientController.getSocketOut().writeObject(input);

                    //receiving
                    Item readItem = (Item) clientController.getSocketIn().readObject();

                    //prompt
                    if (readItem != null) {
                        JOptionPane.showMessageDialog(null, promptItem(readItem));
                    } else {
                        JOptionPane.showMessageDialog(null, "Tool not found!");
                    }

                    //update table
                    importItemsFromServer();
                    mainView.updateTable();
                } catch (Exception f) {
                    System.out.println("MainGUIController SearchByName error");
                    f.printStackTrace();
                }
            }
        }
    }

    /**
     * This is the class for the sale button action listener
     */
    class SaleListen implements ActionListener {

        /**
         * When the button is pressed, this function takes the selected
         * row item and decreases its quantity by the specified amount.
         * Then it sends the item and the new quantity to the server
         * which updates the inventory of the shop
         *
         * @param e Action Event
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getSaleButton()) {
                int selectedRow = -1;
                try {
                    clientController.getSocketOut().writeObject("sale");

                    selectedRow = mainView.getTable().getSelectedRow();

                    if (selectedRow < 0) {
                        JOptionPane.showMessageDialog(null, "Please select an item!");
                        clientController.getSocketOut().writeObject("reset");
                        return;
                    }


                    String s = JOptionPane.showInputDialog("Enter number of items sold:");
                    if (s == null)
                        clientController.getSocketOut().writeObject("reset");

                    int sold = Integer.parseInt(s);

                    // allows server to proceed in the method called
                    clientController.getSocketOut().writeObject("continue");

                    String itemID = (String) mainView.getTableModel().getValueAt(selectedRow, 0);

                    //send itemID to server to get item from DB
                    clientController.getSocketOut().writeObject(itemID);

                    int currQuantity = Integer.parseInt((String) mainView.getTableModel().getValueAt(selectedRow, 2));

                    while (sold > currQuantity) {
                        JOptionPane.showMessageDialog(null, "Sale exceeded quantity!");
                        sold = Integer.parseInt(JOptionPane.showInputDialog("Enter number of items sold:"));
                    }

                    //output new quantity to server
                    clientController.getSocketOut().writeObject(String.valueOf(currQuantity - sold));

                    mainView.getTableModel().setValueAt(currQuantity - sold, selectedRow, 2);

                    //gets confirmation from server
                    String verif = (String) clientController.getSocketIn().readObject();
                    if (verif.equals("not updated")) {
                        JOptionPane.showMessageDialog(null, "Tool not updated! Please refresh!");
                    }
                    //update table
                    importItemsFromServer();
                    mainView.updateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                }

            }
        }
    }

    /**
     * This is the class for the add item button action listener
     */
    class AddListen implements ActionListener {

        /**
         * When the button is pressed, this function takes inputs from the user
         * for Item id, name, quantity, price, and supplier. Then it creates a new item
         * and adds it to the GUI table as well as sends it to the server to add it
         * to the shop inventory
         *
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getAddButton()) {
                try {
                    clientController.getSocketOut().writeObject("add");


                    int id = intInputPrompt("Enter new tool ID: (integer)");

                    //server id check
                    clientController.getSocketOut().writeObject(String.valueOf(id));
                    String idExists = (String) clientController.getSocketIn().readObject();

                    while (idExists.equals("true")) {
                        JOptionPane.showMessageDialog(null, "ID already exists, try again!");
                        id = intInputPrompt("Enter new tool ID: (integer)");
                        clientController.getSocketOut().writeObject(String.valueOf(id));
                        idExists = (String) clientController.getSocketIn().readObject();
                    }

                    String name = JOptionPane.showInputDialog("Enter new tool name:");
                    int quantity = intInputPrompt("Enter new tool quantity: (integer)");
                    double price = doubleInputPrompt("Enter new tool price: (double)");

                    String verif = " ";
                    int suppID = 0;

                    while (!verif.equals("verified")) {
                        suppID = intInputPrompt("Enter new tool supplier ID: (Integer)");
                        verif = sendSuppID(suppID);
                        if (!verif.equals("verified"))
                            JOptionPane.showMessageDialog(null, "Supplier doesn't exist, try again!");
                    }

                    //reads new supplier
                    Supplier newSupp = (Supplier) clientController.getSocketIn().readObject();
                    ;
                    Item newItem = new Item(id, name, quantity, price, newSupp);

                    //send item to server
                    clientController.getSocketOut().writeObject(newItem);

                    //update table
                    importItemsFromServer();
                    mainView.updateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }

        }
    }

    /**
     * This is the class for the remove button action listener
     */
    class RemoveListen implements ActionListener {

        /**
         * When the button is pressed, this function removes the row that is selected,
         * then sends the item of that row to the server to remove it from the
         * inventory
         *
         * @param e Action Event
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getRemoveButton()) {
                int selectedRow = -1;

                try {
                    clientController.getSocketOut().writeObject("remove");

                    selectedRow = mainView.getTable().getSelectedRow();

                    if (selectedRow < 0) {
                        JOptionPane.showMessageDialog(null, "Please select an item!");
                        clientController.getSocketOut().writeObject("reset");
                        return;
                    }

                    clientController.getSocketOut().writeObject("continue");


                    String itemID = (String) mainView.getTableModel().getValueAt(selectedRow, 0);

                    //send item ID to server
                    clientController.getSocketOut().writeObject(itemID);

                    //gets confirmation from server
                    String verif = (String) clientController.getSocketIn().readObject();
                    if (verif.equals("not updated")) {
                        JOptionPane.showMessageDialog(null, "Tool not deleted! Please refresh!");
                    }

                    //update table
                    importItemsFromServer();
                    mainView.updateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }
    }

    class RefreshListen implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainView.getRefreshButton()) {
                try {
                    //send
                    clientController.getSocketOut().writeObject("refresh");
                    //receiving
                    importItemsFromServer();
                    //update table
                    mainView.updateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }

    }


    //OUTER CLASS METHODS

    /**
     * Gets an integer input from the user with error checking
     *
     * @param n message being displayed for input
     * @return integer entered by user
     */
    public int intInputPrompt(String n) {
        String input = null;
        int num = 0;
        while (input == null || num < 0) {

            try {
                input = JOptionPane.showInputDialog(n);
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Add Item NFE");
                JOptionPane.showMessageDialog(null, "Try again!");
                input = null;
            }

        }

        return num;
    }

    /**
     * Gets a double input from the user with error checking
     *
     * @param n message being displayed for input
     * @return integer entered by user
     */
    public double doubleInputPrompt(String n) {
        String input = null;
        double num = 0;
        while (input == null || num < 0) {

            try {
                input = JOptionPane.showInputDialog(n);
                num = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Add Item NFE");
                JOptionPane.showMessageDialog(null, "Try again!");
                input = null;
            }

        }

        return num;
    }

    public String promptItem(Item i) {
        return "ID: " + i.getToolId() +
                "  Name: " + i.getToolName() +
                "  Quantity: " + i.getToolQuantity() +
                "  Price: " + i.getToolPrice() +
                "  Supplier: " + i.getToolSupplier().getName();
    }

    /**
     * sends supplier id from user to the server to check if the supplier
     * entered exists
     *
     * @param suppID supplier id
     * @return verified or not
     */
    public String sendSuppID(int suppID) {
        String verif = null;

        try {
            clientController.getSocketOut().writeObject(String.valueOf(suppID));
            verif = (String) clientController.getSocketIn().readObject();
        } catch (Exception f) {
            System.out.println("Supplier ID writing error from client");
            f.printStackTrace();
        }

        return verif;
    }

    /**
     * TODO REMOVE
     */
    public void importItemsFromServer() {
        try {
            int numOfItems = Integer.parseInt((String) clientController.getSocketIn().readObject());
            String[][] data = new String[numOfItems][5];
            String[] header = {"ID", "Name", "Quantity", "Price", "Supplier"};

            for (int i = 0; i < numOfItems; i++) {
                Item readItem = (Item) clientController.getSocketIn().readObject();

                data[i][0] = String.valueOf(readItem.getToolId());
                data[i][1] = readItem.getToolName();
                data[i][2] = String.valueOf(readItem.getToolQuantity());
                data[i][3] = String.valueOf(readItem.getToolPrice());
                data[i][4] = readItem.getToolSupplier().getId() + " - " + readItem.getToolSupplier().getName();
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, header);
            mainView.setTableModel(tableModel);
        } catch (Exception e) {
            System.out.println("Importing item from server error");
            e.printStackTrace();
        }
    }

    //GETTERS AND SETTERS
    public MainView getMainView() {
        return mainView;
    }

}

