package Client.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Client.ClientView.MainView;

/**
 * This is the class for the browse button action listener
 */
class BrowseButton extends MainGUIController implements ActionListener  {

    public BrowseButton(MainView v, ClientController cc) {
		super(v, cc);
	}

	/**
     * When this button is pressed the action performed a
     * list of the many tools will now become visible to the user
     * and it will also add the action listener associated with the list
     */
    @Override 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainView.getBrowseButton()) {
            try {
                if (mainView.getTable() == null) {
                    mainView.createTable();
                }
            } catch (Exception f) {
                System.out.println("MainGUIController: BrowseListen error");
                f.printStackTrace();
            }
        }
    }
}
