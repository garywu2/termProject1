package Client.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Client.ClientView.MainView;
import utils.Item;

/**
 * This is the class for the search by name action listener
 */
public class SearchByNameButton extends MainGUIController implements ActionListener {

	public SearchByNameButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	/**
	 * When the button is pressed the user will be prompted to enter the name of a
	 * tool and it will go through all the tools and try to match the tool name with
	 * one in the database and if it matches then the elements of the tool will
	 * appear in a dialog box else tells the user it does not exist
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainView.getSearchByNameButton()) {
            //inputs
            String input = JOptionPane.showInputDialog("Please enter tool Name:");

            try {
                //sending
                clientController.getSocketOut().writeObject("searchByName");

                clientController.getSocketOut().writeObject(input);

                //receiving
                Item readItem = (Item) clientController.getSocketIn().readObject();

                //prompt
                if (readItem != null) {
                    JOptionPane.showMessageDialog(null, promptItem(readItem));
                } else {
                    JOptionPane.showMessageDialog(null, "Tool not found!");
                }

                //update table
                importItemsFromServer();
                mainView.updateTable();
            } catch (Exception f) {
                System.out.println("MainGUIController SearchByName error");
                f.printStackTrace();
            }
        }

	}

}
