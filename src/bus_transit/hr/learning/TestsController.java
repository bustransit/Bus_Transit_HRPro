/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestsController extends Application implements Initializable {

    @FXML
    private AnchorPane header;
    @FXML
    private JFXButton btnNewTest;
    @FXML
    private HBox hbxSearch;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnTestSchedule;

    /**
     * @return the stackpane
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
    DBUtilities db = new DBUtilities();
    ResultSet rs; 
    
    @FXML
    private CustomTextField txt_searchAll;
    @FXML
    private FlowPane flpTestContainer;
    @FXML
    public StackPane stackpane;
    public int duration = 0;
    public String testTitle;
    public String type;
    public String description;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTestContainer();
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("Tests.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }       

    @FXML
    private void newTest(ActionEvent event) {                                            
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("NewTest.fxml"));
            
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setBody(pane);
                      
            JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.TOP);

            JFXButton cancel = new JFXButton("Cancel");
            cancel.setOnAction((evt) -> {
                dialog.close();
            });
            JFXButton ok  = new JFXButton("Ok");
            ok.setOnAction((evt) -> {
                dialog.close();
            });
            HBox hbxAction = new HBox(cancel, ok);
            
            dialogLayout.setActions(hbxAction);
            
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(TestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            Pane pane = FXMLLoader.load(getClass().getResource("NewTest.fxml"));
//            pane.prefWidth(DashboardController.scrlCenterContent.getWidth());
//            pane.prefHeight(DashboardController.scrlCenterContent.getHeight());            
//            DashboardController.scrlCenterContent.setContent(pane);
//            DashboardController.draw.close();            
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }          
    }    

    
    private void populateTestContainer(){
        String q = "SELECT * FROM test";
        rs = db.displayRecords(q);               
        ArrayList<FXMLLoader> fxmlLoaders = new ArrayList();
        ArrayList<FlowPane> arrFp = new ArrayList();
        ArrayList<TestController> testControllers = new ArrayList();
                        
        try {
            flpTestContainer.getChildren().clear();
            while(rs.next()){
                String testId = rs.getString("test_id");
                String testName = rs.getString("test_name").toUpperCase();
                String description = rs.getString("description").toUpperCase();
                String lastUpdate = rs.getString("last_update").toUpperCase();    
                
                FXMLLoader l = new FXMLLoader(getClass().getResource("Test.fxml"));                                 
                TestController testController = new TestController();
                testController.setTestId(testId);                
                FlowPane fp = l.load();
                l.setController(testController);
                
                fxmlLoaders.add(l);
                arrFp.add(fp);                
                testControllers.add(testController);                
                flpTestContainer.getChildren().add(fp);                                                                                                                                                                                                                                                      
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }     
    
    @FXML
    private void search(KeyEvent event) {
        String s = txt_searchAll.getText().trim();
        if(!s.isEmpty()){
            String q = "SELECT * FROM test WHERE test_name LIKE '%"+s+"%'";
            searchTest(q);                    
        }
    } 
    
    private void searchTest(String q){         
        rs = db.displayRecords(q);               
        ArrayList<FXMLLoader> fxmlLoaders = new ArrayList();
        ArrayList<FlowPane> arrFp = new ArrayList();
        ArrayList<TestController> testControllers = new ArrayList();
                        
        try {
            flpTestContainer.getChildren().clear();
            while(rs.next()){
                String testId = rs.getString("test_id");
                String testName = rs.getString("test_name").toUpperCase();
                String description = rs.getString("description").toUpperCase();
                String lastUpdate = rs.getString("last_update").toUpperCase();    
                
                FXMLLoader l = new FXMLLoader(getClass().getResource("Test.fxml"));                                 
                TestController testController = new TestController();
                testController.setTestId(testId);                
                FlowPane fp = l.load();
                l.setController(testController);
                
                fxmlLoaders.add(l);
                arrFp.add(fp);
                
                testControllers.add(testController);
                
                flpTestContainer.getChildren().add(fp);
                System.out.println("From LMS: "+testId);                                                                                                                                                                                                                                            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }        
    }          

    @FXML
    private void search(ActionEvent event) {
        String s = txt_searchAll.getText().trim();
        if(!s.isEmpty()){
            String q = "SELECT * FROM test WHERE test_name LIKE '%"+s+"%'";
            searchTest(q);                    
        }
    }

    @FXML
    private void viewTestSchedule(ActionEvent event) {
    }
}
