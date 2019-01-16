/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.system;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import utilities.DBUtilities;
import utilities.Notification;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class ProfileController implements Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    Notification notif = new Notification();
    
    @FXML
    private ToggleButton tglCurrentPassword;
    @FXML
    private PasswordField cpfCurrentPassword;
    @FXML
    private TextField tfCurrentPassword;
    @FXML
    private TextField tfNewPassword;
    @FXML
    private PasswordField cpfNewPassword;
    @FXML
    private ToggleButton tglNewPassword;
    @FXML
    private TextField tfConfirmPassword;
    @FXML
    private PasswordField cpfConfirmPassword;
    @FXML
    private ToggleButton tglConfirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setToggleMask(cpfCurrentPassword, tfCurrentPassword, tglCurrentPassword);
        setToggleMask(cpfNewPassword, tfNewPassword, tglNewPassword);
        setToggleMask(cpfConfirmPassword, tfConfirmPassword, tglConfirmPassword);                                                        
    }    

    /**
     * 
     * @param pf PasswordField
     * @param tf TextField
     * @param tgb ToggleButton
     */
    private void setToggleMask(PasswordField pf, TextField tf, ToggleButton tgb){
        tf.setManaged(false);
        tf.setVisible(false);        
        tf.managedProperty().bind(tgb.selectedProperty());
        tf.visibleProperty().bind(tgb.selectedProperty());        
        
        pf.managedProperty().bind(tgb.selectedProperty().not());
        pf.visibleProperty().bind(tgb.selectedProperty().not());
        
        tf.textProperty().bindBidirectional(pf.textProperty());      
        
        notif.showDarkWarningNotif("Warning Notif", "Just a sample text", Pos.BOTTOM_RIGHT,null);
    }
    

    

    
}
