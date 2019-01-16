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
import com.jfoenix.controls.JFXNodesList;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.BasicConfigurator;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class ViewTrainingController extends Application implements Initializable {

    public String action = null;
    public static String trainingId = null;
        
    @FXML
    private VBox vbxContentCol1;
    @FXML
    private AnchorPane ancTrainingUtility;
    @FXML
    private Label lblTitle;
    @FXML
    private HBox hbxDescription;
    @FXML
    private Text txtDescription1;
    @FXML
    private Text txtDescription12;
    @FXML
    private Text txtDescription13;
    @FXML
    private HBox hbxStatus;
    @FXML
    private Label lblStatus;
    @FXML
    private HBox hbxNotes;
    @FXML
    private HBox hbxSponsors;
    @FXML
    private Text txtStatus1;
    @FXML
    private HBox hbxTrainors;
    @FXML
    private VBox vbxParticipants;
    @FXML
    private VBox vbxSchedules;
    @FXML
    private Text txtTrainors;
    @FXML
    private TextField tfSearch;
    @FXML
    private Label lblParticipants;
    @FXML
    private JFXNodesList jfxListTrainees;
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
    private AnchorPane ancHeader;
    private JFXTextField tfTrainingTitle;
    private JFXComboBox<String> cbTrainingType;
    private JFXTextField tfNumberOfParticipants;
    private JFXTextField tfTrainor;
    private JFXTextField tfFacilitator;
    private JFXTextField tfVenue;
    private JFXDatePicker dpTrainingDate;
    private JFXTimePicker tpTrainingStart;
    private JFXTimePicker tpTrainingEnd;
    @FXML
    private Text txtNote;

    private DBUtilities db = new DBUtilities();
    private ResultSet rs;    
    @FXML
    private Text txtDescription;
    
    
    private JFXRadioButton rbActive;
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
       this.trainingId = trainingId;
       loadTrainingDetails(trainingId);
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        Parent root = FXMLLoader.load(getClass()
                                .getResource("ViewTraining.fxml"));
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
                
                lblTitle.setText(rs.getString("title"));
                txtDescription.setText(rs.getString("description"));
                lblStatus.setText(rs.getString("status"));
                txtNote.setText(rs.getString("note"));    
                lblParticipants.setText(rs.getString("n_of_participants")+" Participants");
                
                 loadSchedules(id);
            }} catch (SQLException ex) {
            Logger.getLogger(ViewTrainingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
            Logger.getLogger(ViewTrainingController.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    public void loadSchedules(String id){
        String s = "SELECT * FROM training_dates WHERE training_id="+id;
        rs = db.displayRecords(s);
        try {
            vbxSchedules.getChildren().clear();
            vbxSchedules.getChildren().add(new Label("Schedules"));            
            while(rs.next()){
                Label l = new Label(rs.getString("training_date")+" "+
                               rs.getString("start_time")+" - "+
                               rs.getString("end_time"));
                
                vbxSchedules.getChildren().add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewTrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTrainees(String id){
        String s = "SELECT * FROM training_dates WHERE training_id="+id;
        rs = db.displayRecords(s);
        try {
            vbxSchedules.getChildren().clear();
            vbxSchedules.getChildren().add(new Label("Schedules"));            
            while(rs.next()){
                Label l = new Label(rs.getString("training_date")+" "+
                               rs.getString("start_time")+" - "+
                               rs.getString("end_time"));
                
                vbxSchedules.getChildren().add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewTrainingController.class.getName()).log(Level.SEVERE, null, ex);
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


    private void reset(ActionEvent event) {
        ancTrainingUtility.requestLayout();        
    }

    private void setTrainingStatus(ActionEvent event) {
        String r = ((JFXRadioButton) event.getSource()).getText().trim().toLowerCase();        
    }

    private void setTitle(ActionEvent event) {
        this.title = tfTrainingTitle.getText().trim();
    }

    private void setDescription(KeyEvent event) {
        this.description = txtDescription.getText().trim();
    }

    private void setType(ActionEvent event) {        
        this.type = cbTrainingType.getSelectionModel().getSelectedItem();
    }

    private void setNumberOfParticipants(ActionEvent event) {
        this.nOfParticipants = Integer.parseInt(tfNumberOfParticipants.getText());
    }

    private void setTrainor(ActionEvent event) {
        this.trainor = tfTrainor.getText().trim();
    }

    private void setFacilitator(ActionEvent event) {
        this.facilitator = tfFacilitator.getText().trim();
    }

    private void setVenue(ActionEvent event) {
        this.venue = tfVenue.getText().trim();
    }
    
    private void setNote(KeyEvent event) {
        this.note = txtNote.getText().trim();
    }    

    private void setTrainingDate(ActionEvent event) {
        this.trainingDate = dpTrainingDate.getValue();
    }

    private void setTrainingStart(ActionEvent event) {
        this.trainingStart = tpTrainingStart.getValue();
    }

    private void setTrainingEnd(ActionEvent event) {
        this.trainingEnd = tpTrainingEnd.getValue();
    }   


}
