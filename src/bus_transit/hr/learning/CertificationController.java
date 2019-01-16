/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sferyx.administration.editors.HTMLEditorJavaFXWrapper;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class CertificationController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private JFXButton btn_menu;

    HTMLEditorJavaFXWrapper wrapper = new HTMLEditorJavaFXWrapper();
    @FXML private AnchorPane content;
    @FXML private AnchorPane heading;
    @FXML private AnchorPane certificationPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        wrapper.getHTMLEditorInstance().setContent("Hello from sferyx");
//        wrapper.setPreferredSize(200, 400);
//        content.getChildren().add(wrapper);
    }
    
    @FXML private void back(ActionEvent e){
        try {
            Stage stage = (Stage) btn_menu.getScene().getWindow();
            stage.close();
            Stage dash = new Stage(StageStyle.UNDECORATED);
            Parent root;
            root = FXMLLoader.load(getClass().getResource("../Dashboard.fxml"));
            Scene scene = new Scene(root);
            dash.setScene(scene);
            dash.setMaximized(true);
            dash.show();
        } catch (IOException ex) {
            Logger.getLogger(CertificationController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
