/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit;

import static bus_transit.DashboardController.stckPne;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author NelsonDelaTorre
 */

public class SceneController {
    public static Stage stage = new DashboardController().primaryStage;
    public static StackPane stackpane = new DashboardController().stckPne;
   
    public void loadModules(){                        
        try {
            Node pane = FXMLLoader.load(getClass().getResource("Scene2.fxml"));            
            stage.setScene(new Scene((Parent) pane,800,900));
            stage.show();
        } catch (IOException ex) {System.out.println(ex);}        
    }
    
    public void loadLogin(){                        
        try {
            Node pane = FXMLLoader.load(getClass().getResource("Login.fxml"));            
            stage.setScene(new Scene((Parent) pane,800,900));
            stage.show();
        } catch (IOException ex) {System.out.println(ex);}          
    }   
    
    public void loadInsidePackage(){                        
        try {
            Node pane = FXMLLoader.load(getClass().getResource("newpackage/inPackage.fxml"));            
            stage.setScene(new Scene((Parent) pane,800,900));
            stage.show();
        } catch (IOException ex) {System.out.println(ex);}         
    }

    public void loadLearningManagement(){        
        String file = "hr/learning/LearningManagement.fxml";        
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
    
    
    // load modals from
    public void loadModal(JFXDialogLayout layout, JFXDialog dialog){         
        dialog = new JFXDialog(stckPne, layout, JFXDialog.DialogTransition.LEFT);        
        dialog.show();
        System.out.println("From scene controller");
    }
    
    public void loadEmployeeDetails(String id){
        
    }
}
