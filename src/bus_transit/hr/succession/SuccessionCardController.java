/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.succession;

import bus_transit.DashboardController;
import bus_transit.Directories;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class SuccessionCardController implements Initializable {

    @FXML
    private VBox vbxUpNext;
    @FXML
    private VBox vbxReadInThreeYrs;
    @FXML
    private VBox vbxHiPotentials;

    public static String positionId;
    private final String posId = positionId;
    
    DBUtilities db = new DBUtilities();
    ResultSet rs;    
    @FXML
    private AnchorPane ancSuccessorCard;
    @FXML
    private Label lblPosition;
    @FXML
    private Label lblCurrentEmployee;
    @FXML
    private Label lblDateOfExit;
    @FXML
    private HBox hbxImmediateSuccessor;
    @FXML
    private HBox hbxReadyInThree;
    @FXML
    private HBox hbxReadyInFive;
    @FXML
    private JFXButton btnNewSuccessor;
    @FXML
    private StackPane stackPane;
    @FXML
    private Hyperlink hplkEmployeeLink;
    
    Directories dir = new Directories();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPosition();
    }    

    private void loadPosition(){
        String q = "SELECT employee_position.position_name as 'position' FROM employee_position WHERE employee_position.position_id = "+posId;
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblPosition.setText(rs.getString("position"));
                System.out.println(rs.getString("position"));
                System.out.println(posId);
                System.out.println(positionId);
                System.out.println(getPositionId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuccessionCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadCurrentEmployee(){
        String q = "SELECT employee_position.position_name as 'position' FROM employee_position WHERE employee_position.position_id = "+posId;
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                //lblPosition.setText(rs.getString("position"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuccessionCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * @return the positionId
     */
    public String getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @FXML
    private void loadSuccessorModal(ActionEvent event) {
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("New Successor for "+lblPosition.getText().toUpperCase()));
        
        VBox vbx = new VBox();
        vbx.setFillWidth(true);
        
        String q = "";
        
//        JFXComboBox cbPosion = new JFXComboBox();
//        q = "SELECT CONCAT(position_id,', ', position_name) FROM employee_position";        
//        db.populateComboBox(q, cbPosion);
//        vbx.getChildren().add(cbPosion);
//        cbPosion.setOnAction((e) -> {
//            
//        });        
        
        //vbx.getChildren().add(new Label(lblPosition.getText()));
        
        JFXComboBox cbEmployee = new JFXComboBox();
        q = "SELECT CONCAT(emp_id,', ',lastname,"
            + "', ', firstname,' ', middlename) AS 'employee' FROM employee";
                        
        db.populateComboBox(q, cbEmployee);
        vbx.getChildren().add(cbEmployee);
        
        cbEmployee.setOnAction((e) -> {

        });
        
        JFXComboBox<String> cbReadiness = new JFXComboBox();
        cbReadiness.getItems().add("IMMEDIATE");
        cbReadiness.getItems().add("1-3 YEARS");
        cbReadiness.getItems().add("HIGH POTENTIALS");
        cbReadiness.setOnAction((evt) -> {
            
        });
        
        vbx.getChildren().add(cbReadiness);
        
        //JFXDatePicker dp = new JFXDatePicker();
        //vbx.getChildren().add(dp);
        
//        dp.setOnAction((evt) -> {
//            
//        });
        
        dialog.setBody(vbx);
        
        
        
        JFXDialog dlg = new JFXDialog(stackPane,dialog,JFXDialog.DialogTransition.TOP);
        
        
        
        JFXButton btnOK = new JFXButton("OK");
        vbx.getChildren().add(btnOK);
        btnOK.setOnAction((evt) -> {
            
            dlg.close();
        });
        
        JFXButton btnCancel = new JFXButton("CANCEL");
        vbx.getChildren().add(btnCancel);
        btnOK.setOnAction((evt) -> {          
            dlg.close();
        });        
        
        dlg.show();                              
        
        dialog.setActions(btnOK);        
        
        dlg.setOnDialogClosed((evt) -> {
            
        });        
    }

    @FXML
    private void loadEmployeeInfo(ActionEvent event) {        
        String file = dir.employeeInfo.toString();        
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(file));
            pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
            DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
            DashboardController.root.getChildren().add(pane);
            DashboardController.draw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }        
    }
    
}
