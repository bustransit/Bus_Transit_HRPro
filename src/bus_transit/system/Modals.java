/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.system;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author NelsonDelaTorre
 */
public class Modals implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        
    }
        
    public void showAlert(String msg) {                
        Alert dlg = new Alert(Alert.AlertType.INFORMATION, msg);
        dlg.show();
    }
    
    public void showConfirmation(String msg) {
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION, msg);
        dlg.show();
    }
    
    public void showInputDialog(String msg) {
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION, msg);
        dlg.show();
    } 
    
    public boolean showJFXConfirmDialog(String heading, String message, StackPane stk){            
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text(heading));            
            dialogLayout.setBody(new Text(message));

            JFXDialog dialog = 
                    new JFXDialog(stk, dialogLayout, JFXDialog.DialogTransition.CENTER);            
            HBox hbxAction = new HBox();
            hbxAction.setSpacing(10);

            JFXButton cancel = new JFXButton("Cancel");
            JFXButton ok = new JFXButton("Ok");            
            //cancel.getStyleClass().add("md-red-button");

//            cancel.setOnAction((evt) -> {                
//                dialog.close();                
//                return true; 
//            });

            // Add choices to txtQuestion from dialog
            
            //ok.getStyleClass().add("md-green-button");
//            ok.setOnAction((evt) -> {                 
//                
//            });  


            
            hbxAction.getChildren().add(cancel);
            hbxAction.getChildren().add(ok);
            dialogLayout.setActions(hbxAction);
                        

            
            dialog.show();    
            return false;
    }
}
