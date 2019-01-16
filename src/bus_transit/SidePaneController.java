/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class SidePaneController implements Initializable {


    public static String employeeFullName;
    public static String userLevel;
    public static String department;
    //public FunctionsDirectory functionList = new FunctionsDirectory();
    public Accordion sidePaneAccordion;
    @FXML private Label lblEmployeeFullName;
    @FXML private Label lblPosition;
    @FXML private Label lblDepartment;
    @FXML
    private VBox vbxModules;
    @FXML
    private JFXButton dashboard;
    @FXML
    private Accordion accdModules;
    @FXML
    private TitledPane tlpTraining;
    @FXML
    private VBox vbxModules1;
    @FXML
    private JFXButton btnTraining;
    @FXML
    private TitledPane tlpLearning;
    @FXML
    private JFXButton btnModules;
    @FXML
    private JFXButton btnTests;
    @FXML
    private JFXButton btnTestResults;
    @FXML
    private TitledPane tlpCompentency;
    @FXML
    private VBox vbxModules2;
    @FXML
    private JFXButton btnJobQuaification;
    @FXML
    private JFXButton btnJobQuaification1;
    @FXML
    private TitledPane tlpSuccession;
    @FXML
    private VBox vbxModules21;
    @FXML
    private JFXButton btnSuccessionPlan;
    
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


    @FXML
    private void loadFunction(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();        
        System.out.println(file);
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(file));
            pane.prefWidth(DashboardController.scrlCenterContent.getWidth());
            pane.prefHeight(DashboardController.scrlCenterContent.getHeight());            
            DashboardController.scrlCenterContent.setContent(pane);
            DashboardController.draw.close();            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

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
            Logger.getLogger(SidePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}