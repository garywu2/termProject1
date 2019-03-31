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
 */
public class LoginController{

    //MEMBER VARIABLES

    private MainGUIController mainGUIController;
    private LoginView loginView;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Creates a LoginController object
     * @param m main GUI controller object
     * @param l login view object
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
                        System.out.println("Verified!");
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
}
