package Client.ClientController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import Client.ClientView.MainView;
import utils.Item;

/**
 * This is the class for the Search by ID button action listener
 */
public class SearchByIDButton extends MainGUIController implements ActionListener {

    public SearchByIDButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	/**
     * When the button is pressed the user will be prompted to enter the
     * name of a tool and it will go through all the tools and try to match
     * the tool name with one in the database and if it matches
     * then the elements of the tool will appear in a dialog box
     * else tells the user it does not exist
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainView.getSearchByIDButton()) {
			int inputID = intInputPrompt("Enter tool ID:");

			try {
				// sending
				clientController.getSocketOut().writeObject("searchByID");

				clientController.getSocketOut().writeObject(String.valueOf(inputID));

				// receiving
				Item readItem = (Item) clientController.getSocketIn().readObject();

				// prompt
				if (readItem != null) {
					JOptionPane.showMessageDialog(null, promptItem(readItem));
				} else {
					JOptionPane.showMessageDialog(null, "Tool not found!");
				}

				// update table
				importItemsFromServer();
				mainView.updateTable();
			} catch (Exception f) {
				System.out.println("MainGUIController SearchByID error");
				f.printStackTrace();
			}
		}

	}

}
