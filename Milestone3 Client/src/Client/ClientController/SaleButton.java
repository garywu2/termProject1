package Client.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Client.ClientView.MainView;

/**
 * This is the class for the sale button action listener
 */
public class SaleButton extends MainGUIController implements ActionListener {
	public SaleButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	/**
	 * When the button is pressed, this function takes the selected row item and
	 * decreases its quantity by the specified amount. Then it sends the item and
	 * the new quantity to the server which updates the inventory of the shop
	 *
	 * @param e Action Event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainView.getSaleButton()) {
			int selectedRow = -1;
			try {
				clientController.getSocketOut().writeObject("sale");

				selectedRow = mainView.getTable().getSelectedRow();

				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Please select an item!");
					clientController.getSocketOut().writeObject("reset");
					return;
				}

				String s = JOptionPane.showInputDialog("Enter number of items sold:");
				if (s == null)
					clientController.getSocketOut().writeObject("reset");

				int sold = Integer.parseInt(s);

				// allows server to proceed in the method called
				clientController.getSocketOut().writeObject("continue");

				String itemID = (String) mainView.getTableModel().getValueAt(selectedRow, 0);

				// send itemID to server to get item from DB
				clientController.getSocketOut().writeObject(itemID);

				int currQuantity = Integer.parseInt((String) mainView.getTableModel().getValueAt(selectedRow, 2));

				while (sold > currQuantity) {
					JOptionPane.showMessageDialog(null, "Sale exceeded quantity!");
					sold = Integer.parseInt(JOptionPane.showInputDialog("Enter number of items sold:"));
				}

				// output new quantity to server
				clientController.getSocketOut().writeObject(String.valueOf(currQuantity - sold));

				mainView.getTableModel().setValueAt(currQuantity - sold, selectedRow, 2);

				// gets confirmation from server
				String verif = (String) clientController.getSocketIn().readObject();
				if (verif.equals("not updated")) {
					JOptionPane.showMessageDialog(null, "Tool not updated! Please refresh!");
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
