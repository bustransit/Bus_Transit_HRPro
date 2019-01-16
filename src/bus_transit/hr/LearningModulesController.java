/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import bus_transit.hr.learning.LearningManagementController;
import bus_transit.hr.learning.LearningModuleCardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningModulesController implements Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;     
    
    private FlowPane flpModules;
    private StackPane stackpane;
    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane ancPosition;
    @FXML
    private JFXButton btnShowOptions;
    @FXML
    private AnchorPane ancContent;

    
    /**
     * Getters and Setters
     */
    public StackPane getStackpane() {
        return stackpane;
    }

    /**
     * @param stackpane the stackpane to set
     */
    public void setStackpane(StackPane stackpane) {
        this.stackpane = stackpane;
    }    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    public void populateLearningModule(){
        String q = "SELECT * FROM learning_modules";
        rs = db.displayRecords(q);        
        flpModules.getChildren().clear();
        try {
            while(rs.next()){                                                                
                String mId = rs.getString("module_id");                 
                FXMLLoader l = new FXMLLoader(getClass().getResource("LearningModuleCard.fxml"));
                LearningModuleCardController md = new LearningModuleCardController();
                md.setModuleId(mId);                             
                FlowPane fp = l.load();
                l.setController(md);   
                flpModules.getChildren().add(fp);
            }                                    
        } catch (SQLException ex) {
            Logger.getLogger(LearningModulesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningModulesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void newLearningModule(ActionEvent event) {    
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("New Module"));
        
        VBox vbx = new VBox();        
        
        JFXTextField title = new JFXTextField();
        title.setPromptText("Title");
        vbx.getChildren().add(0, title);
        
        JFXTextField description = new JFXTextField();
        description.setPromptText("Description");        
        vbx.getChildren().add(1, description);
                
        JFXTextField author = new JFXTextField();
        author.setPromptText("Author");        
        vbx.getChildren().add(2, author);        
        
        JFXTextField type = new JFXTextField();
        type.setPromptText("Type");        
        vbx.getChildren().add(3, type);         
        
        dialog.setBody(vbx);
        
        JFXDialog dlg = new JFXDialog(getStackpane(),
                                      dialog,
                                      JFXDialog.DialogTransition.CENTER);
        
        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            try{
                dlg.close();
                String q = "INSERT INTO learning_modules (module_id, title, description, author, type, last_update)"
                        + "VALUES(null, '"+title.getText()+"','"+description.getText()+"','"+author.getText()+"','"+type.getText()+"',CURDATE())";
                db.execute(q);
                populateLearningModule();
                getStackpane().toBack();                   
            }catch(NullPointerException e){
                
            }
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dlg.close();
            getStackpane().toBack();
        });

        dialog.setActions(btn_cancel, btn_yes);              
        dlg.show();            
    }

    @FXML
    private void showOptions(ActionEvent event) {
    }
    
}
