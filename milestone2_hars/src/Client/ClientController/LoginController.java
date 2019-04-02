package Client.ClientController;

import Client.ClientView.LoginView;
import Server.ServerModel.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is responsible for controlling the login view
 * as well as creating the main window after login
 *
 * @author  Gary Wu, Harsohail Brar, Ryan Holt
 * @version 4.10.0
 * @since April 5, 2019
 */
public class LoginController{

    //MEMBER VARIABLES

    private MainGUIController mainGUIController;
    private LoginView loginView;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Creates a LoginController object and adds the object listener for the
     * log in button
     * @param m main GUI controller object
     * @param l login view object
     * @param o output socket stream
     * @param i input socket stream
     */
    public LoginController(MainGUIController m, LoginView l, ObjectOutputStream o, ObjectInputStream i){
        mainGUIController = m;
        loginView = l;
        out = o;
        in = i;

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

                    out.writeObject(new User(username, password));

                    String verification = (String)in.readObject();

                    if(verification.equals("verified")) {
                        mainGUIController.getMainView().setVisible(true);
                        loginView.setVisible(false);
                        System.out.println("User Logged In!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Invalid User!");
                    }

                    out.flush();
                }catch(Exception f){
                    f.printStackTrace();
                }

            }
        }

    }

    public MainGUIController getMainGUIController() {
        return mainGUIController;
    }

    public LoginView getLoginView() {
        return loginView;
    }

}
