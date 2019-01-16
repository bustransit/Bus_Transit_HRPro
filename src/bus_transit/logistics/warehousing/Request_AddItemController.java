/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.Logistics.Warehousing;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class Request_AddItemController implements Initializable {

    @FXML
    private JFXButton btn_close;
    @FXML
    private TextField txt_item;
    @FXML
    private ComboBox<?> txt_type;
    @FXML
    private TextField txt_quantity;
    @FXML
    private ComboBox<?> txt_unit;
    @FXML
    private TextArea txt_note;
    @FXML
    private JFXButton btn_addItem;
    @FXML
    private JFXButton btn_reset;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Close(ActionEvent event) {
    }
    
}
