import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainModel {

    private File inputFile;

    private BinSearchTree BST;
    private ArrayList<Data> dataArrayList;
    private Scanner inputStream;

    private DefaultListModel listModel;
    private JList list;
    private JScrollPane scrollPane;

    public void getFile(String n){
        String directory = System.getProperty("user.dir");
        String path = directory + java.io.File.separator + n;
        inputFile = new java.io.File(path);
    }

    public void populateBST(){
        try {
            BST = new BinSearchTree();
            dataArrayList = new ArrayList<>();
            inputStream = new Scanner(inputFile);
            String line;
            String[] rawData;
            ArrayList<String> dataArr = new ArrayList<>();

            while(inputStream.hasNextLine()){
                dataArr.clear();

                line = inputStream.nextLine();
                rawData = line.split("\\s+");

                populateDataArr(dataArr, rawData);

                BST.insert(dataArr.get(0), dataArr.get(1), dataArr.get(2), dataArr.get(3));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void populateDataArr(ArrayList<String> dataArr, String[] rawData){
        for(String s: rawData){
            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) != ' ') {
                    dataArr.add(s);
                    break;
                }
            }
        }
        dataArrayList.add(new Data(dataArr.get(0), dataArr.get(1), dataArr.get(2), dataArr.get(3)));
    }

    public void createList(){
        String[] arr = new String[dataArrayList.size()];
        for(int i = 0; i < dataArrayList.size(); i++)
            arr[i] = dataArrayList.get(i).id + "                              "
                    + dataArrayList.get(i).faculty + "                        "
                    + dataArrayList.get(i).major + "                      "
                    + dataArrayList.get(i).year;

        listModel = new DefaultListModel();
        for(int i = 0; i < arr.length; i++)
            listModel.add(i, arr[i]);

        list = new JList(listModel);
        scrollPane = new JScrollPane(list);

        list.setVisibleRowCount(15);
    }

    //GETTERS AND SETTERS

    public JList getList() {
        return list;
    }

    public BinSearchTree getBST() {
        return BST;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public void addDataToList(Data data){
        String s = String.format("%-20s%-20s%-20s%s", data.id, data.faculty, data.major, data.year);
        getListModel().add(getDataArrayList().size(), s);
        getDataArrayList().add(new Data(data.id, data.faculty, data.major, data.year));
        getBST().insert(data.id, data.faculty, data.major, data.year);
    }
}


