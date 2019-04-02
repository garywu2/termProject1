package Client.ClientView;

import Server.ServerModel.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The mainView class holds the entire GUI after you log in
 * The main purpose of this class is to create the GUI as well as the
 * different buttons
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class MainView extends JFrame {

    //MEMBER VARIABLES
    private JPanel titlePanel, centrePanel, buttonPanel;

    private JButton browseButton, searchByIDButton, searchByNameButton, buyButton;

    private JTextField selectedItem;
    private JLabel selectedItemLabel;

    private JScrollPane scrollPane;
    private JTable table;

    /**
     * This is the constructor for the MainView Class
     * which starts by making the GUI by adding JPanel and then adding values
     * to different aspects of the GUI such as buttons
     * @param width width of GUI
     * @param height height of GUI
     */
    public MainView(int width, int height){
        titlePanel = new JPanel();
        centrePanel = new JPanel();
        buttonPanel = new JPanel();

        browseButton = new JButton("Browse");
        searchByIDButton = new JButton("Search by ID");
        searchByNameButton = new JButton("Search by Name");
        buyButton = new JButton("Buy");

        selectedItem = new JTextField(30);
        selectedItemLabel = new JLabel("Selected Item");
        selectedItemLabel.setLabelFor(selectedItem);

        setTitle("Main Window");
        setSize(width, height);
        setLayout(new BorderLayout());
        add("North", titlePanel);
        add("Center", centrePanel);
        add("South", buttonPanel);

        titlePanel.add(new Label("A Tool Shop Application"));

        buttonPanel.add(browseButton);
        buttonPanel.add(searchByIDButton);
        buttonPanel.add(searchByNameButton);
        buttonPanel.add(buyButton);
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Creates a table of the objects and displays it on the GUI
     * @param items the list of Items
     */
    public void createTable(ArrayList<Item> items){
        String[][] data = new String[items.size()][5];

        for(int i = 0; i < items.size(); i++){
            data[i][0] = String.valueOf(items.get(i).getToolId());
            data[i][1] = items.get(i).getToolName();
            data[i][2] = String.valueOf(items.get(i).getToolQuantity());
            data[i][3] = String.valueOf(items.get(i).getToolPrice());
            data[i][4] = items.get(i).getToolSupplier().getName();
        }

        String[] header = {"ID", "Name", "Quantity", "Price", "Supplier"};

        table = new JTable(data, header);
        scrollPane = new JScrollPane(table);

        centrePanel.add(scrollPane, BorderLayout.LINE_END);

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

    /**
     * Adds a action listener to the browse button
     * @param listenForBrowseButton
     */
    public JButton getBrowseButton() {
        return browseButton;
    }

    /**
     * Adds a action listener to the searchByID button
     * @param listenForBrowseButton
     */
    public JButton getSearchByIDButton() {
        return searchByIDButton;
    }

    /**
     * Adds a action listener to the searchByName button
     * @param listenForBrowseButton
     */
    public JButton getSearchByNameButton() {
        return searchByNameButton;
    }

    //getters and setters
    public JButton getBuyButton() {
        return buyButton;
    }

    public JPanel getCentrePanel() {
        return centrePanel;
    }

    public JTextField getSelectedItem() {
        return selectedItem;
    }

    public JTable getTable() {
        return table;
    }
}
