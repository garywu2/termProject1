package Client.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Client.ClientView.MainView;
import utils.Item;
import utils.Supplier;
/**
 * This is the class for the add item button action listener
 */
public class AddButton extends MainGUIController implements ActionListener {

	public AddButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	/**
     * When the button is pressed, this function takes inputs from the user
     * for Item id, name, quantity, price, and supplier. Then it creates a new item
     * and adds it to the GUI table as well as sends it to the server to add it
     * to the shop inventory
     *
     * @param e
     */
	@Override
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
                 
                 Item newItem = new Item(id, name, quantity, price, newSupp.getId());

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
