/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXNodesList;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class ParticipantTitledPaneController implements Initializable {

    /**
     * @return the id
     */
    public static String getId() {
        return id;
    }

    /**
     * @param aId the id to set
     */
    public static void setId(String aId) {
        id = aId;
    }

    @FXML
    private TitledPane tlpTraineeName;
    @FXML
    private JFXCheckBox chkTrainee;
    @FXML
    private AnchorPane ancContainer;

    private DBUtilities db = new DBUtilities();
    private ResultSet rs;      
    
    public static String id;
    public static String currentTrainingId;
    @FXML
    private Hyperlink hplName;
    @FXML
    private Hyperlink hplPosition;
    @FXML
    private JFXNodesList ndTrainingList;
    @FXML
    private ImageView img;
            
    /**
     * Initializes the controller class.
     */
    @Override   
    public void initialize(URL url, ResourceBundle rb) {          
        loadParticipantsInfo();
        //loadCurrenTrainingInfo(currentTrainingId);
    }

    private void loadParticipantsInfo() {        
        String q = 
        "SELECT CONCAT(lastname,', ', firstname, ' ', middlename) AS 'Name', " +
        "emp_id, "+
        "gender, "+
        "employee.position_code, "+
        "UPPER(position.position_name) AS 'Position' " +
        "FROM employee, POSITION " +
        "WHERE employee.emp_id="+id+
        " AND employee.position_code = position.position_code "+
        "ORDER BY employee.lastname;"; 
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                hplName.setText(rs.getString("Name"));
                hplPosition.setText(rs.getString("Position")+", "+rs.getString("position_code"));
                setAvatar(rs.getString("gender").toLowerCase());
                loadParticipantsTraining(id);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipantTitledPaneController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAvatar(String g){
        if(g.equals("male")){
            //img.setImage("");
        }
        if(g.equals("female")){
            
        }        
    }
    
    private void loadParticipantsTraining(String id){
        String q = "SELECT "+
                    "training_title, "+
                    "date_attended, "+
                    "venue FROM employee_training_attended\n" +
                    "INNER JOIN employee\n" +
                    "ON employee.emp_id = employee_training_attended.emp_id\n" +
                    "WHERE employee.emp_id="+id;
        rs = db.displayRecords(q);
        try {
            ndTrainingList.getChildren().clear();
            while(rs.next()){
                String t = rs.getString("training_title")+"\n"+
                           rs.getString("date_attended")+"\n"+
                           rs.getString("venue");
                Hyperlink lst = new Hyperlink(t);
                ndTrainingList.getChildren().add(lst);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipantTitledPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCurrenTrainingInfo(String currentTrainingId) {
//        String q = "";
//        db.execute(q);
    }    

    @FXML
    private void addToTrainees(ActionEvent event) {
        
    }

    @FXML
    private void goToPersonInfo(ActionEvent event) {
        
    }

    @FXML
    private void goToPositionInfo(ActionEvent event) {
        
    }
}
