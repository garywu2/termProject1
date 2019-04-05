package Client.ClientController;

import Client.ClientView.MainView;;
import Client.ClientModel.MainModel;
//import Client.ClientModel.*;
import Server.ServerModel.Item;
import Server.ServerModel.Supplier;

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
                try {
                    clientController.getSocketOut().writeObject("sale");
                    clientController.getSocketOut().flush();

                    int selectedRow = mainView.getTable().getSelectedRow();

                    if (selectedRow < 0) { // nothing selected
                        JOptionPane.showMessageDialog(null, "Please select an item");
                        return;
                    }

                    Item selectedItem = mainModel.getItems().get(selectedRow);

                    int sold = Integer.parseInt(JOptionPane.showInputDialog("Enter number of " + selectedItem.getToolName() + " sold:"));
                    int currQuantity = mainModel.getItems().get(selectedRow).getToolQuantity();

                    //output to server
                    clientController.getSocketOut().writeObject(String.valueOf(currQuantity - sold));
                    clientController.getSocketOut().writeObject(selectedItem);

                    mainModel.getItems().get(selectedRow).setToolQuantity(currQuantity - sold);
                    mainView.getTableModel().setValueAt(currQuantity - sold, selectedRow, 2);
                }catch(Exception f){
                f.printStackTrace();
                }
            }
        }

    }

    class AddListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getAddButton()){
              try{
                  clientController.getSocketOut().writeObject("add");
              }catch (Exception f){
                  f.printStackTrace();
              }

              int id = intInputPrompt("Enter new tool ID: (integer)");

              while(mainModel.idExists(id)){
                  JOptionPane.showMessageDialog(null, "ID already exists, try again!");
                  id = intInputPrompt("Enter new tool ID: (integer)");
              }

              String name = JOptionPane.showInputDialog("Enter new tool name:");
              int quantity = intInputPrompt("Enter new tool quantity: (integer)");
              double price = doubleInputPrompt("Enter new tool price: (double");

              String verif = " ";
              int suppID = 0;

              while(!verif.equals("verified")) {
                  suppID = intInputPrompt("Enter new tool supplier ID: (Integer)");
                  verif = sendSuppID(suppID);
                  if(!verif.equals("verified"))
                      JOptionPane.showMessageDialog(null, "Supplier doesn't exist, try again!");
              }

              Supplier newSupp = readNewSupplier();
              Item newItem = new Item(id, name, quantity, price, newSupp);
              addItemToTable(newItem);
              sendItemData(newItem);
            }
        }

        public Supplier readNewSupplier(){
            Supplier supp = null;
            try{
                supp = (Supplier)clientController.getSocketIn().readObject();
            }catch (Exception e){
                System.out.println("New Supp read error");
            }
            return supp;
        }

        public void addItemToTable(Item newItem){
            String[] data = {String.valueOf(newItem.getToolId()),
                             newItem.getToolName(),
                             String.valueOf(newItem.getToolQuantity()),
                             String.valueOf(newItem.getToolPrice()),
                             newItem.getToolSupplier().getId() + " - " + newItem.getToolSupplier().getName()};

            mainModel.getItems().add(newItem);
            mainView.getTableModel().addRow(data);
        }

        public void sendItemData(Item newItem){
            try {
                clientController.getSocketOut().writeObject(newItem);
            }catch(IOException e){
                System.out.println("Client: item data writing error");
                e.printStackTrace();
            }
        }

        public String sendSuppID(int suppID){
            String verif = null;

            try {
                clientController.getSocketOut().writeObject(String.valueOf(suppID));
                verif = (String)clientController.getSocketIn().readObject();
            }catch(Exception f){
                System.out.println("Supplier ID writing error from client");
                f.printStackTrace();
            }

            return verif;
        }

        public int intInputPrompt(String n){
            String input = null;
            int num = 0;
            while(input == null || num < 0){

                try {
                    input = JOptionPane.showInputDialog(n);
                    num = Integer.parseInt(input);
                }catch (NumberFormatException e){
                    System.out.println("Add Item NFE");
                    JOptionPane.showMessageDialog(null, "Try again!");
                    input = null;
                }

            }

            return num;
        }

        public double doubleInputPrompt(String n){
            String input = null;
            double num = 0;
            while(input == null || num < 0){

                try {
                    input = JOptionPane.showInputDialog(n);
                    num = Double.parseDouble(input);
                }catch (NumberFormatException e){
                    System.out.println("Add Item NFE");
                    JOptionPane.showMessageDialog(null, "Try again!");
                    input = null;
                }

            }

            return num;
        }

    }

    class RemoveListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getRemoveButton()){
                try {
                    clientController.getSocketOut().writeObject("remove");
                }catch (Exception f){
                    f.printStackTrace();
                }

                int selectedRow = mainView.getTable().getSelectedRow();

                if(selectedRow < 0) { // nothing selected
                    JOptionPane.showMessageDialog(null, "Please select an item!");
                    return;
                }

                Item selectedItem = mainModel.getItems().get(selectedRow);

                mainView.getTableModel().removeRow(selectedRow);
                mainModel.getItems().remove(selectedItem);

                sendItemData(selectedItem);
            }
        }

        public void sendItemData(Item newItem){
            try {
                clientController.getSocketOut().writeObject(newItem);
            }catch(IOException e){
                System.out.println("Client: item data writing error");
                e.printStackTrace();
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
