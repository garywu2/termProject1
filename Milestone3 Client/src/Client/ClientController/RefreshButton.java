package Client.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Client.ClientView.MainView;

public class RefreshButton extends MainGUIController implements ActionListener {

	public RefreshButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainView.getRefreshButton()) {
			try {
				// send
				clientController.getSocketOut().writeObject("refresh");
				// receiving
				importItemsFromServer();
				// update table
				mainView.updateTable();
			} catch (Exception f) {
				f.printStackTrace();
			}
		}
	}

}
