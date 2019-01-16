/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.components;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.layout.Background;

/**
 *
 * @author NelsonDelaTorre
 */

public class JFXCancelButton extends JFXButton{

    public JFXCancelButton() {
    }

    public JFXCancelButton(String text) {
        super(text);
    }

    public JFXCancelButton(String text, Node graphic) {
        super(text, graphic);
    }    
    
    public void initialize(){
        this.setStyle("-fx-background-color: blue;");
    }
}
