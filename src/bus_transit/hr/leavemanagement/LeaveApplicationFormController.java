/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.hr.leavemanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Edhz
 */
public class LeaveApplicationFormController extends Application implements Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;

    @FXML
    private JFXTextField tfULI;
    @FXML
    private JFXComboBox<?> cmbLeaveType;
    @FXML
    private JFXDatePicker dpFileDate;
    @FXML
    private JFXDatePicker dpEffectivity;
    @FXML
    private JFXTextField tfNofDays;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXTextField tfFullname;
    @FXML
    private JFXTextField tfPosition;
    @FXML
    private JFXTextField tfStatus;
    @FXML
    private JFXTextArea taReason;
    @FXML
    private JFXTextField tfcareof;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LeaveApplicationForm.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    private void getStatus(ActionEvent event) {
        String lvType = cmbLeaveType.getSelectionModel().getSelectedItem().toString();
        String uli = tfULI.getText().trim();
        getLeaveStatus(uli, lvType);
    }

    private void getLeaveStatus(String uli, String l){
        String q = "SELECT * FROM employee_leave WHERE leave_name='"+l+"' AND uli='"+uli+"'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                tfStatus.setText(rs.getString("status"));
                cmbLeaveType.setAccessibleText(rs.getString("leave_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getEmployeeInfo(ActionEvent event) {
        try {
            String uli = tfULI.getText().trim();
                                                   
            String q = "SELECT employee.*, employee_leave.*\n" +
                        "FROM employee, employee_leave\n" +
                        "WHERE employee.uli = '"+uli+"'";

            rs = db.displayRecords(q);

            if(rs.next()){
                String fname = rs.getString("lastname");
                String lname = rs.getString("firstname");
                String mname = rs.getString("middlename");
                String fullname = lname+", "+fname +" "+mname;

                tfFullname.setText(fullname);
                String position = getPosition(uli);
                tfPosition.setText(position);

                db.populateComboBox("SELECT LOWER(leave_name) FROM employee_leave\n" +
                                    "WHERE uli = '"+uli+"'", cmbLeaveType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getPosition(String u){
        String position = "";
        try {
            String uli = tfULI.getText().trim();

            String q = "SELECT position.position_name\n" +
                        "FROM POSITION, employee\n" +
                        "WHERE employee.position_code = position.position_code\n" +
                        "AND employee.uli = '"+uli+"'";
            rs = db.displayRecords(q);
            if(rs.next()){
                position = rs.getString("position_name");
            }else{
                return null;
            }



        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return position;

    }


    @FXML
    private void save(ActionEvent event) {
        String uli =tfULI.getText().trim();
        LocalDate FileDate =dpFileDate.getValue();
        LocalDate Effectivity =dpEffectivity.getValue();
        String NoofDays =tfNofDays.getText().trim();
        String Reason =taReason.getText().trim();
        String Careof =tfcareof.getText().trim();
        String leaveId = cmbLeaveType.getAccessibleText().trim();
        String Status =tfStatus.getText().trim();

        try{
            String q = "INSERT INTO `leave_application` (uli, leave_id, reason, filing_date,effectivity_date, no_of_days,status,care_of)\n" +
                       "VALUES('"+uli+"','"+leaveId+"','"+Reason+"','"+FileDate+"','"+Effectivity+"','"+NoofDays+"','pending','"+Careof+"')";

            db.execute(q);
            
            // notif here
            clearForm();
            
        }catch(Exception ex){            
            System.out.println(ex);
        }
    }
    
    private void clearForm(){
        tfULI.setText(null);
        taReason.setText(null);
        tfNofDays.setText(null);
        tfStatus.setText(null);
        dpEffectivity.setValue(null);        
        tfcareof.setText(null);
        tfFullname.setText(null);
        cmbLeaveType.getSelectionModel().select(0);
        dpFileDate.setValue(null);
        tfPosition.setText(null);
    }

}
