package Client.ClientController;

import Client.ClientView.MainView;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    protected MainView mainView;

    /**
     * Constructor for the MainGUIController class which essentially adds
     * action listeners to the different buttons
     *
     * @param v this is the MainView Object
     */
    public MainGUIController(MainView v, ClientController cc) {
        super(cc);
        mainView = v;
    }
    
    public void addListeners() {
        mainView.addBrowseListener(new BrowseButton(mainView, clientController));
        mainView.addSearchByIDListener(new SearchByIDButton(mainView, clientController));
        mainView.addSearchByNameListener(new SearchByNameButton(mainView, clientController));
        mainView.addSaleListener(new SaleButton(mainView, clientController));
        mainView.addAddListener(new AddButton(mainView, clientController));
        mainView.addRemoveListener(new RemoveButton(mainView, clientController));
        mainView.addRefreshListener(new RefreshButton(mainView, clientController));
    }
            //HELPER FUNCTIONS

    /**
     * Gets an integer input from the user with error checking
     *
     * @param n message being displayed for input
     * @return integer entered by user
     */
    protected int intInputPrompt(String n) {
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
    protected double doubleInputPrompt(String n) {
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

    protected String promptItem(Item i) {
        return "ID: " + i.getToolId() +
                "  Name: " + i.getToolName() +
                "  Quantity: " + i.getToolQuantity() +
                "  Price: " + i.getToolPrice() +
                "  Supplier: " + i.getToolSupplierIdNumber();
    }

    /**
     * sends supplier id from user to the server to check if the supplier
     * entered exists
     *
     * @param suppID supplier id
     * @return verified or not
     */
    protected String sendSuppID(int suppID) {
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
    protected void importItemsFromServer() {
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
                data[i][4] = String.valueOf(readItem.getToolSupplierIdNumber());	//TODO re-implement this so that it show the supplier name
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

