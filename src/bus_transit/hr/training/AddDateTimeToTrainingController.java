/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
public class AddDateTimeToTrainingController extends Application implements Initializable {

    @FXML
    private AnchorPane ancTrainingUtility;
    @FXML
    private AnchorPane ancHeader;
    @FXML
    private JFXButton btn_close;
    @FXML
    private HBox vbxActions;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXButton btnSave;
    @FXML
    private VBox vbxContentCol2;
    @FXML
    private Label lblTrainingDate;
    @FXML
    private JFXDatePicker dpTrainingDate;
    @FXML
    private Label lblTrainingStart;
    @FXML
    private JFXTimePicker tpTrainingStart;
    @FXML
    private Label lblTrainingEnd;
    @FXML
    private JFXTimePicker tpTrainingEnd;
    @FXML
    private VBox vbxDate;

      
    
    public String trainingId;
    @FXML
    private Label lblTrainingTitle;
    
    private DBUtilities db = new DBUtilities();
    private ResultSet rs;     
    
    LocalDate date;
    LocalTime start;
    LocalTime end;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.trainingId = trainingId;
        loadDetails(trainingId);
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        Parent root = FXMLLoader.load(getClass()
                                .getResource("AddDateTimeToTraining.fxml"));
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
        String q = "SELECT * FROM training WHERE training_id="+id;
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblTrainingTitle.setText(rs.getString("title").toUpperCase());                
                loadDates(id);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(AddDateTimeToTrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void reset(ActionEvent event) {
    }

    
    private void loadDates(String id){
        String q = "SELECT * FROM training_dates WHERE training_id="+id;
        rs = db.displayRecords(q);
        try {
            vbxDate.getChildren().clear();            
            while(rs.next()){
                String dateTime = rs.getString("training_date")+ " " +
                                  rs.getString("start_time")+" - " + 
                                  rs.getString("end_time");              

                HBox hbx = new HBox();
                hbx.setAccessibleText(rs.getString("rec_id"));

                Label lbl = new Label(dateTime);        
                hbx.getChildren().add(lbl);        

                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);                
                JFXButton btn = new JFXButton(null, icon);
                hbx.getChildren().add(btn);            

                vbxDate.getChildren().add(hbx);

                btn.setOnAction((ev) -> {
                    JFXButton b = ((JFXButton) ev.getSource());
                    int i = vbxDate.getChildren().indexOf(hbx);
                    String d = "DELETE FROM training_dates WHERE rec_id="+hbx.getAccessibleText();
                    vbxDate.getChildren().remove(i);
                    db.execute(d);
                });                            
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddDateTimeToTrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    private void add(ActionEvent event) {
        try{
            if(dpTrainingDate.getValue() != null &&
               tpTrainingStart.getValue() != null &&
               tpTrainingEnd.getValue() != null){            
                String q = "INSERT INTO training_dates (training_id, "
                        + "training_date, start_time, end_time) "
                        + "VALUES('"+trainingId+"','"+date+"','"+start+"','"+end+"') ";
                db.execute(q);
                loadDates(trainingId);
                dpTrainingDate.setValue(null);
                tpTrainingStart.setValue(null);
                tpTrainingEnd.setValue(null);
            }            
        }catch(NullPointerException e){
            System.out.println(e);
        }
    }

    @FXML
    private void setTrainingDate(ActionEvent event) {
        this.date = dpTrainingDate.getValue();
    }

    @FXML
    private void setTrainingStart(ActionEvent event) {
        this.start = tpTrainingStart.getValue();
    }

    @FXML
    private void setTrainingEnd(ActionEvent event) {
        this.end = tpTrainingEnd.getValue();
    }
    
    public void save(){
        vbxDate.getChildren().forEach((t) -> {
            HBox hbx = (HBox) t;
            
       
            
        });
    }
    
}
