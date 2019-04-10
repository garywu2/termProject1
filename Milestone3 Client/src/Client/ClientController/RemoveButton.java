package Client.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This is the class for the remove button action listener
 */

import javax.swing.JOptionPane;

import Client.ClientView.MainView;

public class RemoveButton extends MainGUIController implements ActionListener {
	public RemoveButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	/**
	 * When the button is pressed, this function removes the row that is selected,
	 * then sends the item of that row to the server to remove it from the inventory
	 *
	 * @param e Action Event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainView.getRemoveButton()) {
			int selectedRow = -1;

			try {
				clientController.getSocketOut().writeObject("remove");

				selectedRow = mainView.getTable().getSelectedRow();

				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please select an item!");
					clientController.getSocketOut().writeObject("reset");
					return;
				}

				clientController.getSocketOut().writeObject("continue");

				String itemID = (String) mainView.getTableModel().getValueAt(selectedRow, 0);

				// send item ID to server
				clientController.getSocketOut().writeObject(itemID);

				// gets confirmation from server
				String verif = (String) clientController.getSocketIn().readObject();
				if (verif.equals("not updated")) {
					JOptionPane.showMessageDialog(null, "Tool not deleted! Please refresh!");
				}

				// update table
				importItemsFromServer();
				mainView.updateTable();
			} catch (Exception f) {
				f.printStackTrace();
			}
		}
	}

}
