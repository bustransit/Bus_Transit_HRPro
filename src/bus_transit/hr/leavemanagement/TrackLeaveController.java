/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.leavemanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Edhz
 */
public class TrackLeaveController implements Initializable {

    @FXML
    private Label lbFullName;
    @FXML
    private Label lbPosition;
    @FXML
    private Label lbFileDate;
    @FXML
    private Label lbDuration;
    @FXML
    private Label lbTypeLeave;
    @FXML
    private Label lbDepartment;
    @FXML
    private VBox vbLeaveBalance;

    String empId = "";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
