/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.leavemanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Edhz
 */
public class LeaveSummaryController extends Application implements Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    @FXML
    private TableView<?> tblLeaveSummary;
    @FXML
    private StackPane stackpane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db.buildData("SELECT * from leave_application", tblLeaveSummary);
    }    

    @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass().getResource("LeaveSummary.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }        
    
    private void populateTable(){
        String q = "";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loadTrackLea(MouseEvent event) {
        int dbClck = event.getClickCount();
        if(dbClck == 2){
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            
            System.out.println("Double Clicked");
            
            try {
                Pane pane = FXMLLoader.load(getClass().getResource("TrackLeave.fxml"));
                
                dialogLayout.setBody(pane);
                
                JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                
                
                
                
                HBox hbx = new HBox();
                
                JFXButton ok = new JFXButton("OK");
                hbx.getChildren().add(ok);
                ok.setOnAction((evt) -> {
                    dialog.close();
                });
                
                JFXButton cancel = new JFXButton("Cancel");
                hbx.getChildren().add(cancel);          
                cancel.setOnAction((evt) -> {
                    dialog.close();
                });
                
                dialogLayout.setActions(hbx);
                
                dialog.show();                
            } catch (IOException ex) {
                Logger.getLogger(LeaveSummaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            
            
            
//            // load track lea
//            try {
//                Parent pane = FXMLLoader.load(getClass().getResource("TrackLeave.fxml"));
//                //TrackLeaveController tr = new TrackLeaveController();
//                pane.prefHeight(DashboardController.root.getHeight());
//                pane.prefWidth(DashboardController.root.getWidth());
//                //pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
//                DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
//                DashboardController.root.getChildren().add(pane);
//                DashboardController.draw.close();
//            } catch (IOException ex) {
//                System.out.println(ex);
//            }            
        }
    }            
}