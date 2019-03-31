package Client.ClientController;

import Client.ClientView.MainView;
import Client.ClientModel.MainModel;
import Server.ServerModel.Item;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUIController {

    private MainView mainView;
    private MainModel mainModel;

    public MainGUIController(MainView v, MainModel m){
        mainView = v;
        mainModel = m;

        mainView.addBrowseListener(new BrowseListen());
        mainView.addSearchByIDListener(new SearchByIDListen());
        mainView.addSearchByNameListener(new SearchByNameListen());
    }

    class BrowseListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == mainView.getBrowseButton()){
                try{
                    if(mainView.getList() == null){
                        mainView.createList(mainModel.getItems());
                        mainView.addListSelectionListener(new ListSelectionListen());
                    }
                }catch (Exception f){
                    f.printStackTrace();
                }
            }
        }

    }

    class SearchByIDListen implements ActionListener{

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

    class SearchByNameListen implements ActionListener{

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

    class ListSelectionListen implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e){
            if(!e.getValueIsAdjusting()){
                mainView.getSelectedItem().setText(mainView.getList().getSelectedValue().toString());
            }

            mainView.getSelectedItem().validate();
        }

    }

    public MainView getMainView() {
        return mainView;
    }

    public MainModel getMainModel() {
        return mainModel;
    }
}
