package Client.ClientView;

import Server.ServerModel.Item;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainView extends JFrame {

    //MEMBER VARIABLES
    private JPanel titlePanel, centrePanel, buttonPanel;

    private JButton browseButton, searchByIDButton, searchByNameButton, buyButton;

    private JTextField selectedItem;
    private JLabel selectedItemLabel;

    private DefaultListModel listModel;
    private JList list;
    private JScrollPane scrollPane;


    public MainView(int width, int height){
        titlePanel = new JPanel();
        centrePanel = new JPanel();
        buttonPanel = new JPanel();

        browseButton = new JButton("Browse");
        searchByIDButton = new JButton("Search by ID");
        searchByNameButton = new JButton("Search by Name");
        buyButton = new JButton("Buy");

        selectedItem = new JTextField(25);
        selectedItemLabel = new JLabel("Selected Item");
        selectedItemLabel.setLabelFor(selectedItem);

        setTitle("Main Window");
        setSize(width, height);
        setLayout(new BorderLayout());
        add("North", titlePanel);
        add("Center", centrePanel);
        add("South", buttonPanel);

        titlePanel.add(new Label("An Tool Shop Application"));

        buttonPanel.add(browseButton);
        buttonPanel.add(searchByIDButton);
        buttonPanel.add(searchByNameButton);
        buttonPanel.add(buyButton);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void createList(ArrayList<Item> items){
        listModel = new DefaultListModel();

        String id = "ID";
        String name = "Name";
        String quantity = "Quantity";
        String price = "Price";
        String supplier = "Supplier";

        String header = String.format("%-5s %-20s %-10s %-7s %-15s", id, name, quantity, price, supplier);

        listModel.add(0, header);
        for(int i = 1; i < items.size(); i++){
            listModel.add(i, items.get(i).toString());
        }

        list = new JList(listModel);
        scrollPane = new JScrollPane(list);

        list.setVisibleRowCount(15);

        centrePanel.add(scrollPane, BorderLayout.LINE_END);
        centrePanel.add(selectedItemLabel);
        centrePanel.add(selectedItem);

        revalidate();
    }

    public void addBrowseListener(ActionListener listenForBrowseButton){
        browseButton.addActionListener(listenForBrowseButton);
    }

    public void addSearchByIDListener(ActionListener listenForSearchByIDButton){
        searchByIDButton.addActionListener(listenForSearchByIDButton);
    }

    public void addSearchByNameListener(ActionListener listenForSearchByNameButton){
        searchByNameButton.addActionListener(listenForSearchByNameButton);
    }

    public void addListSelectionListener(ListSelectionListener listenForListSelection){
        list.addListSelectionListener(listenForListSelection);
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getSearchByIDButton() {
        return searchByIDButton;
    }

    public JButton getSearchByNameButton() {
        return searchByNameButton;
    }

    public JButton getBuyButton() {
        return buyButton;
    }

    public JList getList() {
        return list;
    }

    public JPanel getCentrePanel() {
        return centrePanel;
    }

    public JTextField getSelectedItem() {
        return selectedItem;
    }
}
