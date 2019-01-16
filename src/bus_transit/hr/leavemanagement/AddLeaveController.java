/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.leavemanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Edhz
 */
public class AddLeaveController extends Application implements Initializable {

    @FXML
    private CustomTextField ctfRequirement;
    @FXML
    private Button btnOk;
    @FXML
    private CustomTextField ctfLeaveName;
    @FXML
    private CustomTextField ctfDayPerYear;
    @FXML
    private TextArea taLeaveDescription;
    @FXML
    private JFXCheckBox chkConvertTofCash;
    @FXML
    private VBox vbxRequirements;
    @FXML
    private JFXButton btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddLeave.fxml"));
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
    private void addRequirements(ActionEvent event) {
        String req = ctfRequirement.getText().trim();
        
        HBox hbx = new HBox();
        hbx.setSpacing(10);
        hbx.setAlignment(Pos.CENTER_LEFT);
        Label lblRequirement = new Label(req);
        JFXButton btn = new JFXButton("X");
        btn.getStyleClass().add("sm-red-button");
        btn.setOnAction((evt) -> {
            Parent p = btn.getParent();
            vbxRequirements.getChildren().remove(p);
        });
        
        hbx.getChildren().add(lblRequirement);                    
        hbx.getChildren().add(btn);                                    
        
//        vbxRequirements.getChildren().forEach((t) -> {
//            HBox h = (HBox) t;
//            h.getChildren().forEach((hb) -> {
//                Label tx = (Label) hb;
//                if(tx.getText().trim().equals(req)){
//                    System.out.println("Duplicate");
//                }else{
//
//                }
//            });
//        });

                    
        
        vbxRequirements.getChildren().add(hbx);
        ctfRequirement.setText("");
    }

    @FXML
    private void setConvertible(ActionEvent event) {
        
    }

    ObservableList<String> listOfReq = FXCollections.observableArrayList();
    @FXML
    private void saveLeave(ActionEvent event) {
        vbxRequirements.getChildren().forEach((t) -> {
            HBox h = (HBox) t;
            
            h.getChildren().forEach((hb) -> {
                if(hb instanceof Label){
                }
                Label tx = (Label) hb;
                
                listOfReq.add(tx.getText().trim());
            });
        });
        System.out.println(listOfReq);
        //listOfReq.add(req);
    }
    
}
