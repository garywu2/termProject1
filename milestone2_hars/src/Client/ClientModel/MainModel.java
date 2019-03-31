package Client.ClientModel;

import java.util.ArrayList;
import Server.ServerModel.Item;

import javax.swing.*;
import java.awt.*;

public class MainModel {

    private ArrayList<Item> items;

    public MainModel(){
        items = new ArrayList<>();
    }


    public ArrayList<Item> getItems() {
        return items;
    }


}
