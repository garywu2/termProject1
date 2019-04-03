package Client.ClientController;

import Client.ClientView.MainView;
import Client.ClientModel.MainModel;
import Server.ServerModel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The MAIN GUIController class essentially holds all code for that
 * action listeners of the various buttons such as browse, it holds
 * objects of the main view and model
 * Overall the purpose of this class is to add the action listeners
 * to the various buttons in the main view
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class MainGUIController extends GUIController{

    private MainView mainView;
    private MainModel mainModel;

    /**
     * Constructor for the MainGUIController class which essentially adds
     * action listeners to the different buttons
     * @param v this is the MainView Object
     */
    public MainGUIController(MainView v, ClientController cc){
        super(cc);
        mainView = v;
        mainModel = new MainModel();

        mainView.addBrowseListener(new BrowseListen());
        mainView.addSearchByIDListener(new SearchByIDListen());
        mainView.addSearchByNameListener(new SearchByNameListen());
        mainView.addSaleListener(new SaleListen());
        mainView.addAddListener(new AddListen());
        mainView.addRemoveListener(new RemoveListen());
    }

    /**
     * This is the class for the browse button action listener
     */
    class BrowseListen implements ActionListener{

        /**
         * When this button is pressed the action performed a
         * list of the many tools will now become visible to the user
         * and it will also add the action listener associated with the list
         */
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getBrowseButton()){
                try{
                    if(mainView.getTable() == null){
                        mainView.createTable(mainModel.getItems());
                    }
                }catch (Exception f){
                    f.printStackTrace();
                }
            }
        }

    }

    /**
     * This is the class for the Search by ID button action listener
     */
    class SearchByIDListen implements ActionListener{

        /**
         * When the button is pressed the user will be prompted to enter the
         * ID of a tool and it will go through all the tools and try to match
         * the tool ID with one in the database and if it matches
         * then the elements of the tool will appear in a dialog box
         * else tells the user it does not exist
         */
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getSearchByIDButton()){
                String input = JOptionPane.showInputDialog("Please enter tool ID:");
                int inputID = Integer.parseInt(input);

                for(int i = 0; i < mainModel.getItems().size(); i++){
                    if(inputID == mainModel.getItems().get(i).getToolId()){
                        int id = mainModel.getItems().get(i).getToolId();
                        String name = mainModel.getItems().get(i).getToolName();
                        int quantity = mainModel.getItems().get(i).getToolQuantity();
                        double price = mainModel.getItems().get(i).getToolPrice();
                        String supplier = mainModel.getItems().get(i).getToolSupplier().getName();

                        JOptionPane.showMessageDialog(null, "ID: " + id
                                                                                    + " Name: " + name
                                                                                    + " Quantity: " + quantity
                                                                                    + " Price: $" + price
                                                                                    + " Supplier: " + supplier);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Tool not found!");
            }
        }

    }

    /**
     * This is the class for the search by name action listener
     */
    class SearchByNameListen implements ActionListener{

        /**
         * When the button is pressed the user will be prompted to enter the
         * name of a tool and it will go through all the tools and try to match
         * the tool name with one in the database and if it matches
         * then the elements of the tool will appear in a dialog box
         * else tells the user it does not exist
         */
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getSearchByNameButton()){
                String input = JOptionPane.showInputDialog("Please enter tool Name:");

                for(int i = 0; i < mainModel.getItems().size(); i++){
                    if(input.equals(mainModel.getItems().get(i).getToolName())){
                        int id = mainModel.getItems().get(i).getToolId();
                        String name = mainModel.getItems().get(i).getToolName();
                        int quantity = mainModel.getItems().get(i).getToolQuantity();
                        double price = mainModel.getItems().get(i).getToolPrice();
                        String supplier = mainModel.getItems().get(i).getToolSupplier().getName();

                        JOptionPane.showMessageDialog(null, "ID: " + id
                                + " Name: " + name
                                + " Quantity: " + quantity
                                + " Price: $" + price
                                + " Supplier: " + supplier);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Tool not found!");
            }
        }

    }

    class SaleListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getSaleButton()){
                int selectedRow = mainView.getTable().getSelectedRow();

                if(selectedRow < 0) { // nothing selected
                    JOptionPane.showMessageDialog(null, "Please select an item");
                    return;
                }

                Item selectedItem = mainModel.getItems().get(selectedRow);

                System.out.println(selectedRow);

                int sold = Integer.parseInt(JOptionPane.showInputDialog("Enter number of " + selectedItem.getToolName() + " sold:"));
                int currQuantity = mainModel.getItems().get(selectedRow).getToolQuantity();
                mainModel.getItems().get(selectedRow).setToolQuantity(currQuantity - sold);

                mainView.getTableModel().setValueAt(currQuantity - sold, selectedRow, 2);
            }
        }

    }

    class AddListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getAddButton()){
                try {

                    int id = Integer.parseInt(JOptionPane.showInputDialog("Enter new tool ID:"));
                    String name = JOptionPane.showInputDialog("Enter new tool name: ");
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new tool quantity: "));
                    double price = Double.parseDouble(JOptionPane.showInputDialog("Enter new tool price"));
                    int suppID = Integer.parseInt(JOptionPane.showInputDialog("Enter new tool supplier ID: "));

                    clientController.getSocketOut().writeObject(String.valueOf(suppID));
                    Supplier readSupp = (Supplier) clientController.getSocketIn().readObject();

                    Item newItem = new Item(id, name, quantity, price, readSupp);
                    mainModel.getItems().add(newItem);

                    String[] newItemData = {String.valueOf(id), name, String.valueOf(quantity), String.valueOf(price), readSupp.getName()};
                    mainView.getTableModel().addRow(newItemData);
                }catch(Exception f){
                    System.out.println("Add Listen Error");
                    f.printStackTrace();
                }
            }
        }

    }

    class RemoveListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getRemoveButton()){
                int selectedRow = mainView.getTable().getSelectedRow();

                if(selectedRow < 0) { // nothing selected
                    JOptionPane.showMessageDialog(null, "Please select an item!");
                    return;
                }

                Item selectedItem = mainModel.getItems().get(selectedRow);

                mainView.getTableModel().removeRow(selectedRow);
                mainModel.getItems().remove(selectedItem);
            }
        }

    }

    public MainView getMainView() {
        return mainView;
    }

    public MainModel getMainModel() {
        return mainModel;
    }
}
