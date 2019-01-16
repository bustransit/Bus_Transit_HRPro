/*
 * BESTLINK COLLEGE OF THE PHILIPPINES
 * This is under License of BSIT 4201
 * Provincial Bus Transportation System
 */
package bus_transit.components;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class PositionFullInfoController implements Initializable {

    public String positionCode;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPositionDetials(this.positionCode);
    }    
    
    private void loadPositionDetials(String psCode){
        
    };
    
}
