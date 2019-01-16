/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.claims;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Edhz
 */
public class ReimbursementFormController extends Application implements Initializable {
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    
    @FXML
    private JFXTextField empId;
    @FXML
    private Label lblFullname;
    @FXML
    private Label lblPosition;
    @FXML
    private Label lblDepartement;
    @FXML
    private JFXComboBox<?> cmbSpecific;
    @FXML
    private VBox vbxRequirements;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String s = "SELECT reimbursement_name FROM valid_reimbursement_list";
        db.populateComboBox(s, cmbSpecific);        
    }    

    @FXML
    private void getEmployeeInfo(ActionEvent event) {
        String s = empId.getText().trim();
        String q =  "SELECT \n" +
                    "employee_position.`position_name`,\n" +
                    "department.`department_name`,\n" +
                    "employee.`lastname`,\n" +
                    "employee.`firstname`\n" +
                    "FROM \n" +
                    "   employee,\n" +
                    "   employee_position,\n" +
                    "   department\n" +
                    "WHERE \n" +
                    "   employee.`position_code` = employee.`position_code`\n" +
                    "AND \n" +
                    "   department.`department_code` = employee_position.`department_code`\n" +
                    "AND \n" +
                    "   employee.`uli` = '"+s+"'\n" +
                    "GROUP BY uli;";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String dept = rs.getString("department_name");
                String position = rs.getString("position_name");
                
                lblFullname.setText(lname +", "+fname);
                lblPosition.setText(position);
                lblDepartement.setText(dept);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReimbursementFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
      @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass().getResource("ReimbursementForm.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }   
       
    public static void main(String[] args) {
        launch(args);
    }    

    @FXML
    private void setSpecific(ActionEvent event) {
        String s = cmbSpecific.getSelectionModel()
                              .getSelectedItem().toString();
        
        String q = "SELECT * FROM reimbursement_req WHERE reimbursement_name='"+s+"'";
        
        rs = db.displayRecords(q);                              
        try {
            vbxRequirements.getChildren().clear();    
            while(rs.next()){
                String ch = rs.getString("requirements_name");                
                JFXCheckBox chb = new JFXCheckBox(ch);
                vbxRequirements.getChildren().add(chb);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ReimbursementFormController.class.getName()).log(Level.SEVERE, null, ex);
        }               
    }

    @FXML
    private void setSpecific(MouseEvent event) {
    }
    
}