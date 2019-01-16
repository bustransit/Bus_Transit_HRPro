/*
 * BESTLINK COLLEGE OF THE PHILIPPINES
 * This is under License of BSIT 4201
 * Provincial Bus Transportation System
 */

package bus_transit.components;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 *
 * @author NelsonDelaTorre
 */

public class ViewUtilities {
    public HBox createEditableHboxRow(String txt){
        HBox hbx = new HBox();
        
        if(!txt.isEmpty()){
            Label l = new Label(txt);
            hbx.getChildren().add(l);
            HBox.setHgrow(l, Priority.ALWAYS);            
            l.setOnMouseClicked((evt) -> {

            });

            MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.DELETE);
            JFXButton btnDelete = new JFXButton("x", icon);            
            hbx.getChildren().add(btnDelete);
            btnDelete.setOnAction((event) -> {            
                
            });             
        }
        return hbx;
    }
}
