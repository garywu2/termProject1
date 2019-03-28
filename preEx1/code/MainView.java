import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JPanel titlePanel, centrePanel, buttonPanel;

    private JButton insertButton, findButton, browseButton, fileButton;

    public MainView(int widthPixels, int heightPixels){
        titlePanel = new JPanel();
        centrePanel = new JPanel();
        buttonPanel = new JPanel();

        insertButton = new JButton("Insert");
        findButton = new JButton("Find");
        browseButton = new JButton("Browse");
        fileButton = new JButton("Create Tree from File");

        setTitle ( "Main Window");
        setSize(widthPixels, heightPixels);
        setLayout (new BorderLayout( ) );
        add( "North", titlePanel );
        add( "Center", centrePanel );
        add( "South", buttonPanel );

        titlePanel.add(new Label("An Application to Maintain Student Records"));

        buttonPanel.add(insertButton);
        buttonPanel.add(findButton);
        buttonPanel.add(browseButton);
        buttonPanel.add(fileButton);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public String getInputFileName(){
        return JOptionPane.showInputDialog("Enter the file name:");
    }

    public void addInsertListener(ActionListener listenForInsertButton){
        insertButton.addActionListener(listenForInsertButton);
    }

    public void addFindListener(ActionListener listenForFindButton){
        findButton.addActionListener(listenForFindButton);
    }

    public void addBrowseListener(ActionListener listenForBrowseButton){
        browseButton.addActionListener(listenForBrowseButton);
    }

    public void addFileListener(ActionListener listenForFileButton){
        fileButton.addActionListener(listenForFileButton);
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public JButton getFindButton() {
        return findButton;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getFileButton() {
        return fileButton;
    }

    public JPanel getCentrePanel() {
        return centrePanel;
    }
}
