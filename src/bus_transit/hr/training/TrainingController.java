/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TrainingController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane ancPosition;
    @FXML
    private JFXButton btnShowOptions;
    @FXML
    private AnchorPane ancContent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * OPions
     */    
    private PopOver pop = new PopOver();
    private VBox vbx = new VBox();    
    @FXML
    private void showOptions(ActionEvent event) {
        if(pop.isFocused()){
            
        }else{            
            vbx.getChildren().clear();
            vbx.setFillWidth(true);
            vbx.setSpacing(10);
            double p = 8;
            vbx.setPadding(new Insets(p,p,p,p));
            
            double textSize = 14;
            
            JFXButton btnEdit = new JFXButton("Edit");                                                
            btnEdit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT));
            btnEdit.setAccessibleText("EditJobQualification.fxml");
            
            btnEdit.setFont(new Font(textSize));                       
            vbx.getChildren().add(btnEdit);
                      
            JFXButton btnViewDetails = new JFXButton("Details");                                           
            btnViewDetails.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD)); 
            btnViewDetails.setFont(new Font(textSize));
            vbx.getChildren().add(btnViewDetails);        
            
            pop.setContentNode(vbx);
            pop.setAnimated(true);        
            pop.setId("OptionPopOver"); 

            pop.setDetachable(false);
            pop.setAnimated(true);
            pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
            pop.setCornerRadius(4);        
            pop.show(btnShowOptions);
            String popOverId = pop.getId();
            System.out.println(popOverId);             
        }      
    }    
}
