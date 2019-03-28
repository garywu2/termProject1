public class MVC {

    public static void main(String[] args){
        MainView theView = new MainView(500, 375);
        MainModel theModel = new MainModel();

        MainController theController = new MainController(theView, theModel);

        theView.setVisible(true);
    }

}
