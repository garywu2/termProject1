package Client.ClientController;


public abstract class GUIController {

    protected ClientController clientController;

    public GUIController(ClientController c){
        setClientController(c);
    }

    public void setClientController(ClientController c){
        clientController = c;   // 2-way association with CC
    }



}
