/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestManagementController extends Application implements Initializable {

    @FXML private AnchorPane container;
    @FXML private AnchorPane heading;
    @FXML private JFXButton btnNewQuestion;
    @FXML private StackPane stackpane;
    @FXML private AnchorPane content;
    @FXML private VBox vbxQuestions;
    @FXML private Label lblTitle;
    @FXML private Label lblDuration;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("TestManagement.fxml"));        
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
    private void toEdit(MouseEvent event) {
        Label tf = (Label) event.getSource();
        String t = tf.getText();
        tf.setVisible(false);         
        JFXTextField s = new JFXTextField(t);
        s.setLayoutX(tf.getLayoutX());
        s.setLayoutY(tf.getLayoutY());        
        heading.getChildren().add(0, s);
        s.setOnAction(evt->{
        
        });
    }

    @FXML
    private void showNewQuestionModal(ActionEvent event) {
        
    }            
}
