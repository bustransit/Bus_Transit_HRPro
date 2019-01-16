/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
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
public class TrainingUtilityController extends Application implements Initializable {

    public String action = null;
    public String trainingId = null;
        
    @FXML
    private VBox vbxContentCol1;
    @FXML
    private VBox vbxContentCol2;
    @FXML
    private AnchorPane ancTrainingUtility;
    @FXML
    private VBox vbxContentCol3;
    @FXML
    private Label lblTraineesForTraining;
    @FXML
    private Label lblVenue1;
    @FXML
    private JFXCheckBox chSelectAll;
    @FXML
    private JFXTextField tfSearchTrainees;
    @FXML
    private VBox vbxTrainees;
    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    @FXML
    private JFXButton btn_close;
    @FXML
    private AnchorPane ancHeader;
    @FXML
    private Label lblTrainingHeader;
    @FXML
    private Label lblTrainingTitle;
    @FXML
    private JFXTextField tfTrainingTitle;
    @FXML
    private Label lblTrainingType;
    @FXML
    private JFXComboBox<String> cbTrainingType;
    @FXML
    private Label lblParticipants;
    @FXML
    private JFXTextField tfNumberOfParticipants;
    @FXML
    private Label lblTrainor;
    @FXML
    private JFXTextField tfTrainor;
    @FXML
    private Label lblFacilitator;
    @FXML
    private JFXTextField tfFacilitator;
    @FXML
    private Label lblVenue;
    @FXML
    private JFXTextField tfVenue;
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
    private Group grpNote;
    @FXML
    private Label lblNote;
    @FXML
    private TextArea txtNote;
    @FXML
    private HBox vbxActions;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXButton btnSave;

    private DBUtilities db = new DBUtilities();
    private ResultSet rs;    
    @FXML
    private Group grpNote1;
    @FXML
    private Label lblDescription;
    @FXML
    private TextArea txtDescription;
    
    
    @FXML
    private ToggleGroup status;
    @FXML
    private JFXRadioButton rbActive;
    @FXML
    private JFXRadioButton rbInactive;
    
    // Training fields
    private String trainingStatus = "inactive";
    private String title = null;
    private String description = null;
    private String type = null;    
    private String trainor = null;
    private String facilitator = null;
    private String venue = null;
    private LocalDate trainingDate = null;
    private LocalTime trainingStart = null;
    private LocalTime trainingEnd = null;
    private String note = null;
    private int nOfParticipants = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        action = action.trim().toUpperCase();
        trainingId = trainingId;
        
        lblTrainingHeader.setText(action);
        cbTrainingType.getItems().add("inhouse");
        cbTrainingType.getItems().add("outdoor");
        
        if(action.equals("TRAINIING DETAILS")){
            this.loadTrainingDetails(trainingId);            
        }
        if(action.equals("EDIT TRAINING")){
            this.loadTrainingDetails(trainingId);
        }
        if(action.equals("NEW TRAINING")){
            
        }        
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        Parent root = FXMLLoader.load(getClass()
                                .getResource("TrainingUtility.fxml"));
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

    public void loadTrainingDetails(String id){
       String q = "SELECT * FROM training WHERE training_id = '"+id+"'"; 
       rs = db.displayRecords(q);
        try {
            if(rs.next()){  
                
                tfTrainingTitle.setText(rs.getString("title"));
                txtDescription.setText(rs.getString("description"));
                tfNumberOfParticipants.setText(rs.getString("participants"));
                tfTrainor.setText(rs.getString("trainor"));
                tfFacilitator.setText(rs.getString("facilitator"));
                tfVenue.setText(rs.getString("venue"));                
                txtNote.setText(rs.getString("note"));
                
                dpTrainingDate.setValue(rs.getDate("target_date").toLocalDate());
                tpTrainingStart.setValue(rs.getTime("start_time").toLocalTime());
                tpTrainingEnd.setValue(rs.getTime("end_time").toLocalTime());
                
                trainingStatus = rs.getString("status");
                
                if(trainingStatus.toLowerCase().equals("active")){
                    rbActive.setSelected(true);
                }else{
                    rbInactive.setSelected(true);
                }
                
                cbTrainingType.getSelectionModel().select(rs.getString("type").toLowerCase());
                
            }} catch (SQLException ex) {
            Logger.getLogger(TrainingUtilityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
            Logger.getLogger(TrainingUtilityController.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    public void updateTraining(String id){
        try{
            title = (tfTrainingTitle.getText() != null) ? tfTrainingTitle.getText() : "" ;
            description = (txtDescription.getText() != null) ? txtDescription.getText() : "";
            type = (cbTrainingType.getSelectionModel().getSelectedItem() != null) ?cbTrainingType.getSelectionModel().getSelectedItem() : "";
            
            nOfParticipants = (tfNumberOfParticipants.getText() != null) ? Integer.parseInt(tfNumberOfParticipants.getText()) : 0;            
            facilitator = tfFacilitator.getText();
            venue = tfVenue.getText();
            trainingDate = dpTrainingDate.getValue();
            trainingStart = tpTrainingStart.getValue();
            trainingEnd = tpTrainingEnd.getValue();
            note = txtNote.getText();

            String q = "UPDATE training SET " +
                        "title = '" + tfTrainingTitle.getText() +
                        "' description = " + txtDescription.getText() +
                        "' participants = " + Integer.parseInt(tfNumberOfParticipants.getText()) +
                        "' start_time = " + tpTrainingStart.getValue() +
                        "' end_time = " + tpTrainingEnd.getValue() +
                        "' target_date = " + dpTrainingDate.getValue() +
                        "' venue = '" + tfVenue.getText() +
                        "' TYPE = '" + cbTrainingType.getSelectionModel().getSelectedItem() +
                        "' facilitator = '" + tfFacilitator.getText() +
                        "' status = '" + trainingStatus +
                        "' note = '" + txtNote.getText() +
                        " WHERE training_id="+id;
                        
            db.execute(q);               
        }catch(Exception e){
            System.err.println(e);
        }        
    }
            
    @FXML
    private void Close(ActionEvent event) {
    }



    @FXML
    private void save(ActionEvent event) {
        try{
            title = tfTrainingTitle.getText();
            description = txtDescription.getText();
            type = cbTrainingType.getSelectionModel().getSelectedItem();
            nOfParticipants = Integer.parseInt(tfNumberOfParticipants.getText());
            facilitator = tfFacilitator.getText();
            venue = tfVenue.getText();
            trainingDate = dpTrainingDate.getValue();
            trainingStart = tpTrainingStart.getValue();
            trainingEnd = tpTrainingEnd.getValue();
            note = txtNote.getText();

            String q = "INSERT INTO training(" +
                        "title," +
                        "description," +
                        "participants," +
                        "start_time," +
                        "end_time," +
                        "target_date," +
                        "venue," +
                        "TYPE," +
                        "facilitator," +
                        "status," +
                        "note) " +
                        "VALUES('"+
                        title+"', '"+
                        description+"', '"+
                        nOfParticipants+"',  '"+
                        trainingStart+"',  '"+
                        trainingEnd+"',  '"+  
                        trainingDate+"',  '"+
                        venue+"',  '"+                      
                        type+"', '"+                   
                        facilitator+"',  '"+                                     
                        trainingStatus+"',  '"+
                        note+"')";
            db.execute(q);               
        }catch(Exception e){
            System.err.println(e);
        }
    }


    @FXML
    private void reset(ActionEvent event) {
        ancTrainingUtility.requestLayout();        
    }

    @FXML
    private void setTrainingStatus(ActionEvent event) {
        String r = ((JFXRadioButton) event.getSource()).getText().trim().toLowerCase();        
    }

    @FXML
    private void setTitle(ActionEvent event) {
        this.title = tfTrainingTitle.getText().trim();
    }

    @FXML
    private void setDescription(KeyEvent event) {
        this.description = txtDescription.getText().trim();
    }

    @FXML
    private void setType(ActionEvent event) {        
        this.type = cbTrainingType.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setNumberOfParticipants(ActionEvent event) {
        this.nOfParticipants = Integer.parseInt(tfNumberOfParticipants.getText());
    }

    @FXML
    private void setTrainor(ActionEvent event) {
        this.trainor = tfTrainor.getText().trim();
    }

    @FXML
    private void setFacilitator(ActionEvent event) {
        this.facilitator = tfFacilitator.getText().trim();
    }

    @FXML
    private void setVenue(ActionEvent event) {
        this.venue = tfVenue.getText().trim();
    }
    
    @FXML
    private void setNote(KeyEvent event) {
        this.note = txtNote.getText().trim();
    }    

    @FXML
    private void setTrainingDate(ActionEvent event) {
        this.trainingDate = dpTrainingDate.getValue();
    }

    @FXML
    private void setTrainingStart(ActionEvent event) {
        this.trainingStart = tpTrainingStart.getValue();
    }

    @FXML
    private void setTrainingEnd(ActionEvent event) {
        this.trainingEnd = tpTrainingEnd.getValue();
    }   

    @FXML
    private void searchTrainees(KeyEvent event) {
    }

    @FXML
    private void addTrainee(ActionEvent event) {
    }
}
