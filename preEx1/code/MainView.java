import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class is responsible with main window GUI
 */
public class MainView extends JFrame {

    //MEMBER VARIABLES

    private JPanel titlePanel, centrePanel, buttonPanel;

    private JButton insertButton, findButton, browseButton, fileButton;

    /**
     * Constructs a MainView object
     * @param widthPixels width of the window
     * @param heightPixels height of the window
     */
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

    /**
     * gets input file name from user
     * @return name of file
     */
    public String getInputFileName(){
        return JOptionPane.showInputDialog("Enter the file name:");
    }

    //ADD ACTION LISTENER FUNCTIONS

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

    //GETTERS AND SETTERS

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
