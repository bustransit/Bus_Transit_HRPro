/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import bus_transit.*;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class HRSidePaneController implements Initializable {


    public static String employeeFullName;
    public static String userLevel;
    public static String department;
    //public FunctionsDirectory functionList = new FunctionsDirectory();
    public Accordion sidePaneAccordion;
    @FXML private JFXButton trainingManagement;
    @FXML private JFXButton successionPlanning;
    @FXML private JFXButton competencyManagement;
    @FXML private Label lblEmployeeFullName;
    @FXML private Label lblPosition;
    @FXML private Label lblDepartment;
    @FXML
    private JFXButton learningManagement;
    @FXML
    private VBox vbxModules;
    @FXML
    private JFXButton dashboard;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //loadFunctions(userLevel, department);
        
        lblEmployeeFullName.setText(employeeFullName.toUpperCase());
        lblPosition.setText(userLevel.toUpperCase());
        lblDepartment.setText(department.toUpperCase());        
    }

    public void loadFunctions(String dept, String usrLvl) {
        sidePaneAccordion.getPanes().removeAll(sidePaneAccordion.getPanes());
        ObservableList<JFXButton> btn;
        ObservableList<TitledPane> titledPane;
        AnchorPane anchorPane;
        VBox vbox;

        /**
         * User level to be filtered staff supervisor managerial board
         * system_admin
         */
        /**
         * Department code to filtered admin it hr logistics finance core
         */
        // system_admin
        if (dept.toLowerCase().equals("system_admin")) {

        }

        // Logisitics
        if (dept.toLowerCase().equals("logistics")) {
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }
            if (usrLvl.equals("managerial")) {

            }
        }

        // hr
        if (dept.toLowerCase().equals("hr")) {
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }
            if (usrLvl.equals("managerial")) {

            }
        }

        // finance
        if (dept.toLowerCase().equals("finance")) {
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }
            if (usrLvl.equals("managerial")) {

            }
        }

        // core
        if (dept.toLowerCase().equals("core")) {
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }
            if (usrLvl.equals("managerial")) {

            }
        }
                
//        TitledPane t = new TitledPane();
//        t.setText("Vendor Portal");
//        VBox v = new VBox();
//        v.setFillWidth(true);
//        FontAwesomeIconView ico = new FontAwesomeIconView();
//        ico.glyphNameProperty().setValue("ANCHOR");
//        JFXButton b = new JFXButton("Purchasing");
//        b.graphicProperty().set(ico);
//        v.getChildren().add(b);
//        t.setContent(v);
//        sidePaneAccordion.getPanes().add(t);        
    } // filterUser end here    

//    private void Module1(ActionEvent event) throws IOException {
//        FadeTransition fade = new FadeTransition();
//        fade.setDuration(Duration.millis(500));
//        fade.setNode(HRDashboardController.root);
//        fade.setFromValue(1);
//        fade.setToValue(0);
//        fade.setOnFinished(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    AnchorPane pane = FXMLLoader.load(getClass().getResource("hr/Certification.fxml"));
//                    pane.setPrefSize(HRDashboardController.root.getWidth(), HRDashboardController.root.getHeight());
//                    HRDashboardController.root.getChildren().removeAll(HRDashboardController.root.getChildren());
//                    HRDashboardController.root.getChildren().add(pane);
//                    HRDashboardController.draw.close();
//                } catch (IOException ex) {
//                    System.out.println(ex);
//                }
//            }
//        });
//        fade.play();
//    }

    @FXML
    private void loadFunction(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();
        System.out.println(file);
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(file));
            pane.setPrefSize(HRDashboardController.root.getWidth(), HRDashboardController.root.getHeight());
            HRDashboardController.root.getChildren().removeAll(HRDashboardController.root.getChildren());
            HRDashboardController.root.getChildren().add(pane);
            HRDashboardController.draw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    } // loadFunction ends here            

    @FXML
    private void loadDashboard(ActionEvent event) {
        try {
            JFXButton b = (JFXButton) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            stage.close();
            Stage dash = new Stage(StageStyle.UNDECORATED);
            Parent root;            
            root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root);
            dash.setScene(scene);
            dash.setMaximized(true);
            dash.show();              
        } catch (IOException ex) {
            Logger.getLogger(HRSidePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}