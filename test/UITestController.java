/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.ButtonModel;
import org.apache.log4j.BasicConfigurator;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class UITestController extends Application implements Initializable {

    @FXML
    private AnchorPane ancTest;
    @FXML
    private VBox vbxButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXButton b = CreateDefaultJFXButton();
        b.setText("Default Button");
        vbxButton.getChildren().add(b);
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        Parent root = FXMLLoader.load(getClass()
                                .getResource("UITest.fxml"));
        Scene scene = new Scene(root);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();        
    }    
    
    public static void main(String[] args) {        
        BasicConfigurator.configure();
        launch(args);        
    }      
    
    public JFXButton CreateDefaultJFXButton(){        
        JFXButton b = new JFXButton();
        b.getStyleClass().add("default-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        double p = 4;
        b.setPadding(new Insets(p));        
        
        return b;
    }
}
