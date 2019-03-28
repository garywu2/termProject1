import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertController {

    private InsertView insertView;
    private MainModel mainModel;
    public InsertController(InsertView v, MainModel model){
        insertView = v;
        mainModel = model;

        insertView.addInsertListener(new InsertListen());
        insertView.addReturnListener(new ReturnListen());
    }

    class InsertListen implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == insertView.getInsertButton()){
                String id = insertView.getIDNumber();
                String faculty = insertView.getFaculty();
                String major = insertView.getMajor();
                String year = insertView.getYear();

                boolean valid = true;

                if(id.length() != 5){
                    JOptionPane.showMessageDialog(null, "Error: Enter a 6-digit ID");
                    valid = false;
                }
                if(faculty.length() != 2){
                    JOptionPane.showMessageDialog(null, "Error: Enter a 4-Character Faculty name");
                    valid = false;
                }
                if(major.length() != 4){
                    JOptionPane.showMessageDialog(null, "Error: Enter a 2-Character Major name");
                    valid = false;
                }

                if(valid == true) {
                    Data data = new Data(id, faculty, major, year);
                    mainModel.addDataToList(data);
                }
            }
        }

    }

    class ReturnListen implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == insertView.getReturnButton()){
                insertView.setVisible(false);
            }
        }

    }

}
