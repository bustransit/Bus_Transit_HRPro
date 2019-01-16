/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.BasicConfigurator;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class SponsorsController extends Application implements Initializable {

    @FXML
    private AnchorPane ancTrainingUtility;
    @FXML
    private AnchorPane ancHeader;
    @FXML
    private Label lblFacilitator;
    @FXML
    private JFXTextField tfFacilitator;
    @FXML
    private Label lblTrainor1;

    public String trainingId;
    @FXML
    private Label lblTrainingTitle;
    
    private DBUtilities db = new DBUtilities();
    private ResultSet rs;     
    @FXML
    private VBox vbxSponsors;
    @FXML
    private VBox vbxTrainor;
    @FXML
    private JFXTextField tfTrainor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.trainingId = trainingId;
        loadDetails(trainingId);
//        tfFacilitator.setOnAction((event) -> {
//            setFacilitator(event);
//        });
//        tfTrainor.setOnAction((event) -> {
//            setTrainor(event);
//        });
    }    

    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        Parent root = FXMLLoader.load(getClass()
                                .getResource("Sponsors.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();        
    }    
    
    public static void main(String[] args) {        
        BasicConfigurator.configure();
        launch(args);        
    }    
    
    public void loadDetails(String id){
        String q = "SELECT * FROM training WHERE training_id='"+id+"'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblTrainingTitle.setText(rs.getString("title").toUpperCase());
                loadFacilitator(id);
                loadTrainor(id);
           }
        } catch (SQLException ex) {
            Logger.getLogger(SponsorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    private void loadFacilitator(String id){
        String q = "SELECT * FROM training_sponsor WHERE training_id="+id;
        rs = db.displayRecords(q);
        vbxSponsors.getChildren().clear();            
        try {
            while(rs.next()){
                String sponsor = rs.getString("sponsor_name");
                HBox hbx = new HBox();
                hbx.setAccessibleText(rs.getString("rec_id"));
                
                Label lbl = new Label(sponsor);
                hbx.getChildren().add(lbl);
                
                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);                
                JFXButton btn = new JFXButton(null, icon);
                hbx.getChildren().add(btn);
                
                vbxSponsors.getChildren().add(hbx);

                btn.setOnAction((ev) -> {
                    JFXButton b = ((JFXButton) ev.getSource());
                    int i = vbxSponsors.getChildren().indexOf(hbx);
                    String d = "DELETE FROM training_sponsor WHERE rec_id="+hbx.getAccessibleText();
                    vbxSponsors.getChildren().remove(i);
                    db.execute(d);
                });
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SponsorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTrainor(String id){
        String q = "SELECT * FROM training_trainor WHERE training_id="+id;
        rs = db.displayRecords(q);
        vbxTrainor.getChildren().clear();            
        try {
            while(rs.next()){
                String sponsor = rs.getString("trainor_name");
                HBox hbx = new HBox();
                hbx.setAccessibleText(rs.getString("trainor_id"));
                
                Label lbl = new Label(sponsor);
                hbx.getChildren().add(lbl);
                
                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);                
                JFXButton btn = new JFXButton(null, icon);
                hbx.getChildren().add(btn);
                
                vbxTrainor.getChildren().add(hbx);

                btn.setOnAction((ev) -> {
                    JFXButton b = ((JFXButton) ev.getSource());
                    int i = vbxTrainor.getChildren().indexOf(hbx);
                    String d = "DELETE FROM training_trainor WHERE trainor_id="+hbx.getAccessibleText();
                    vbxTrainor.getChildren().remove(i);
                    db.execute(d);
                });
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SponsorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void setTrainor(ActionEvent event) {
        String s = tfTrainor.getText().trim();
        String q = "INSERT INTO training_trainor (training_id, trainor_name) VALUES('"+trainingId+"','"+s+"')";
        db.execute(q);
        loadTrainor(trainingId);
        tfTrainor.setText(null);
        tfTrainor.requestFocus();        
    }
    
    @FXML
    private void setFacilitator(ActionEvent event) {
        String s = tfFacilitator.getText().trim();
        String q = "INSERT INTO training_sponsor (training_id, sponsor_name) VALUES('"+trainingId+"','"+s+"')";
        db.execute(q);
        loadFacilitator(trainingId);
        tfFacilitator.setText(null);
        tfFacilitator.requestFocus();
    }    

    
}
