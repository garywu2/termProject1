import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible from controlling the MainView and
 * the MainModel class
 */
public class MainController {

    //MEMBER VARIABLES

    private MainView theView;
    private MainModel theModel;

    /**
     * Constructs a MainController Object
     * @param v MainView object
     * @param m MainModel object
     */
    public MainController(MainView v, MainModel m){
        theView = v;
        theModel = m;

        theView.addFileListener(new FileListen());
        theView.addBrowseListener(new BrowseListen());
        theView.addFindListener(new FindListen());
        theView.addInsertListener(new InsertListen());
    }

    //ACTION LISTENER IMPLEMENTATION FOR EACH
    //BUTTON ON THE MAIN VIEW

    class FileListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == theView.getFileButton()){
                try{
                    String fileName = theView.getInputFileName();
                    theModel.getFile(fileName);
                    if(!theModel.getInputFile().exists())
                        JOptionPane.showMessageDialog(null, "File not Found!");
                    theModel.populateBST();
                }catch(Exception f){
                    f.printStackTrace();
                }
            }
        }

    }

    class BrowseListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == theView.getBrowseButton()){
                try{
                    if(theModel.getList() == null && theModel.getBST() != null){
                        theModel.createList();
                        theView.getCentrePanel().add(theModel.getScrollPane(), BorderLayout.LINE_END);
                        theView.revalidate();
                    }
                }catch(Exception f){
                    f.printStackTrace();
                }
            }
        }

    }

    class FindListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == theView.getFindButton()){
                if(theModel.getList() != null){
                    try{
                        String input = JOptionPane.showInputDialog("Please enter the student's ID:");

                        for(Data d: theModel.getDataArrayList()){
                            if(input.equals(d.id))
                                JOptionPane.showMessageDialog(null, d.toString());
                        }
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                }
            }
        }

    }

    class InsertListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == theView.getInsertButton()){
                if(theModel.getList() != null){
                    try{
                        InsertView insertView = new InsertView();

                        InsertController insertController = new InsertController(insertView, theModel);

                        theView.revalidate();
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                }
            }
        }

    }
}
