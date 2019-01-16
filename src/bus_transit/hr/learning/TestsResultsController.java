/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestsResultsController implements Initializable {

    @FXML
    private AnchorPane heading;
    @FXML
    private StackPane stackpane;
    @FXML
    private CustomTextField txt_searchAll;
    @FXML
    private JFXButton btnNew;
    @FXML
    private FlowPane flpMainContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchModule(KeyEvent event) {
    }

    @FXML
    private void newLearningModule(ActionEvent event) {
    }
    
}
