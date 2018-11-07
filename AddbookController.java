
package libraryassistant.addbook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import libraryassistant.database.DatabaseHandler;


public class AddbookController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField bookname;
    @FXML
    private TextField bookid;
    @FXML
    private TextField bookauthor;
    @FXML
    private TextField bookpublisher;
    @FXML
    private TextField intcode;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    
    DatabaseHandler databasehandler;
    @FXML
    private ComboBox<String> chooseCategory;
    ObservableList<String> category = FXCollections.observableArrayList(
                "Computer Science",
                "Mathematics",
                "English",
                "Biology",
                "Zoology",
                "Microbiology",
                "Education",
                "Political Science",
                "Economics"
              );

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            databasehandler = DatabaseHandler.getInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException ex) {
            Logger.getLogger(AddbookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        checkData();
       
        chooseCategory.setItems(category);
    }    

    @FXML
    private void Save(ActionEvent event) {
        String name = bookname.getText();
        String id = bookid.getText();
        String author = bookauthor.getText();
        String publisher = bookpublisher.getText();
        String icode = intcode.getText();
        String cat = chooseCategory.getValue();
        
        boolean empty = name.isEmpty()||id.isEmpty()||author.isEmpty()||publisher.isEmpty()||icode.isEmpty();
        if(empty){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO BOOK VALUES("+
                "'"+ name +"',"+
                "'"+ id +"',"+
                "'"+ author +"',"+
                "'"+ publisher +"',"+
                "'"+ icode +"',"+
                "'"+ cat +"',"+
                " "+ true +" "+
                 ")";
        System.out.println(qu);
        if(databasehandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saved");
            alert.setContentText("Book saved Sucessfully!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("Failed to save Book!");
            alert.showAndWait();
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    

    private void checkData() {
        String qu = "SELECT *FROM BOOK ";
        ResultSet rs = databasehandler.execQuery(qu);
        try {
            while(rs.next()){
                String bname = rs.getString("Name");
                String bid = rs.getString("ID");
                String bauthor= rs.getString("Author");
                String bpublisher = rs.getString("Publisher");
                String bicode = rs.getString("InternalCode");
                boolean bisavail = rs.getBoolean("isAvail");
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Exception at CheckData, AddBookController.: " +ex.getLocalizedMessage(),""
                                            + "Error Occured",JOptionPane.ERROR_MESSAGE ); 
        }
    }
    
    @FXML    
    private void combo(ActionEvent event) {
               //valueProperty();
        }

    private static class Combo{
        public void add(){
        
        }        
    }
}
