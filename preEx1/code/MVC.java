/**
 * This class acts as the front end of the program which holds
 * the main function and constructs the MVC components
 * @author Harsohail Brar, Gary Wu, and Ryan Holt
 * @version 1.0
 * @since March 28, 2019
 */
public class MVC {

    /**
     * main function which runs the program and constructs
     * the MVC components
     * @param args command line arguments
     */
    public static void main(String[] args){
        MainView theView = new MainView(500, 375);
        MainModel theModel = new MainModel();

        MainController theController = new MainController(theView, theModel);

        theView.setVisible(true);
    }

}
