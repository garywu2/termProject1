import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class is responsible for the Insert Window GUI
 */
public class InsertView extends JFrame{

    //MEMBER VARIABLES

    private JPanel titlePanel, centrePanel, buttonPanel;

    private JButton insertButton, returnButton;

    private JTextField idField, facultyField, majorField, yearField;

    /**
     * Creates an InsertView object
     */
    public InsertView(){
        titlePanel = new JPanel();
        centrePanel = new JPanel();
        buttonPanel = new JPanel();

        insertButton = new JButton("Insert");
        returnButton = new JButton("Return to Main Window");

        setSize(700, 150);
        setLayout(new BorderLayout());
        add("North", titlePanel);
        add("Center", centrePanel);
        add("South", buttonPanel);

        titlePanel.add(new Label("Insert a New Node"));

        idField = new JTextField(6);
        facultyField = new JTextField(2);
        majorField = new JTextField(4);
        yearField = new JTextField(1);

        JLabel idLabel = new JLabel("Enter the Student ID");
        idLabel.setLabelFor(idField);
        centrePanel.add(idLabel);
        centrePanel.add(idField);

        JLabel facultyLabel = new JLabel("Enter Faculty");
        facultyLabel.setLabelFor(facultyField);
        centrePanel.add(facultyLabel);
        centrePanel.add(facultyField);

        JLabel majorLabel = new JLabel("Enter Student's Major");
        majorLabel.setLabelFor(majorField);
        centrePanel.add(majorLabel);
        centrePanel.add(majorField);

        JLabel yearLabel = new JLabel("Enter Year");
        yearLabel.setLabelFor(yearField);
        centrePanel.add(yearLabel);
        centrePanel.add(yearField);

        buttonPanel.add(insertButton);
        buttonPanel.add(returnButton);

        setVisible(true);
    }

    //GETTERS AND SETTERS

    public String getIDNumber(){
        return idField.getText();
    }

    public String getFaculty(){
        return facultyField.getText();
    }

    public String getMajor(){
        return majorField.getText();
    }

    public String getYear(){
        return yearField.getText();
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    //ADD ACTION LISTENER METHODS

    public void addInsertListener(ActionListener listenForInsertButton){
        insertButton.addActionListener(listenForInsertButton);
    }

    public void addReturnListener(ActionListener listenForReturnButton){
        returnButton.addActionListener(listenForReturnButton);
    }
}
