package Client.ClientController;

import Client.ClientView.LoginView;
import Server.ServerModel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible for controlling the login view
 * as well as creating the main window after login
 *
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class LoginController extends GUIController{

    //MEMBER VARIABLES

    private LoginView loginView;
    private boolean verified;

    /**
     * Creates a LoginController object and adds the object listener for the
     * log in button
     * @param m main GUI controller object
     * @param l login view object
     * @param o output socket stream
     * @param i input socket stream
     */
    public LoginController(LoginView l, ClientController cc){
        super(cc);
        loginView = l;
        verified = false;

        loginView.addLoginListener(new LoginListen());
    }

    /**
     * Action Listener implementation for Login Button
     */
    class LoginListen implements ActionListener{

        /**
         * When the user clicks on the button it checks to see if
         * the username and password is the same as one of our users
         * from our database and if yes the GUI becomes visible else
         * a box will prompt telling user is invalid
         */
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == loginView.getLoginButton()){
                try{
                    String username = loginView.getUsernameField().getText();
                    String password = loginView.getPasswordFeild().getText();

                    clientController.getSocketOut().writeObject(new User(username, password));

                    String verification = (String)clientController.getSocketIn().readObject();

                    if(verification.equals("verified")) {
                        loginView.setVisible(false);
                        verified = true;
                        System.out.println("User Logged In!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Invalid User!");
                    }

                    clientController.getSocketOut().flush();
                }catch(Exception f){
                    f.printStackTrace();
                }

            }
        }

    }

    //getters and setters
    public LoginView getLoginView() {
        return loginView;
    }

    public boolean isVerified() {
        return verified;
    }
}
