/*
 * BESTLINK COLLEGE OF THE PHILIPPINES
 * This is under License of BSIT 4201
 * Provincial Bus Transportation System
 */
package bus_transit.hr.reports;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class ReportsGeneralController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadReports();
    }    
    
    private void loadReports(){
        ObservableList<String> learningMgtReports = FXCollections.observableArrayList();
    }        
    
    private AnchorPane reportPaneCreator(ObservableList<String> list){
        AnchorPane pane = new AnchorPane();
        VBox vbxReports = new VBox();
        list.forEach((report) -> {
            Hyperlink l = new Hyperlink();
            vbxReports.getChildren().add(l);
        });
        
        
        
        
        return pane;
    }
}
