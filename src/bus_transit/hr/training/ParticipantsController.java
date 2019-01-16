/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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
public class ParticipantsController extends Application implements Initializable {

    public String action = null;
    public String trainingId = null;
        
    @FXML
    private AnchorPane ancTrainingUtility;
    @FXML
    private JFXCheckBox chSelectAll;
    @FXML
    private JFXTextField tfSearchTrainees;
    @FXML
    private Label lblSearch;
    @FXML
    private FlowPane flpTrainees;
    @FXML
    private JFXComboBox<String> cmbSearchBy;
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
    @FXML
    private Label lblTrainingTitle;
    private JFXTextField tfTrainingTitle;
    private JFXComboBox<String> cbTrainingType;
    private JFXTextField tfNumberOfParticipants;
    private JFXTextField tfTrainor;
    private JFXTextField tfFacilitator;
    private JFXTextField tfVenue;
    private JFXDatePicker dpTrainingDate;
    private JFXTimePicker tpTrainingStart;
    private JFXTimePicker tpTrainingEnd;
    private TextArea txtNote;

    private DBUtilities db = new DBUtilities();
    private ResultSet rs;    
    private TextArea txtDescription;
    
    
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
        loadParticipants();
        loadDetails(trainingId); 
        
        cmbSearchBy.getItems().add("Lastname");
        cmbSearchBy.getSelectionModel().selectFirst();
        cmbSearchBy.getItems().add("Firstname");        
        cmbSearchBy.getItems().add("Middlename");
        cmbSearchBy.getItems().add("Position");
        cmbSearchBy.getItems().add("Position Code");
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        Parent root = FXMLLoader.load(getClass()
                                .getResource("Participants.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();        
    }    
    
    public static void main(String[] args) {                
        launch(args);        
    }   
    
    public void loadDetails(String id){
        String q = "SELECT * FROM training WHERE training_id='"+id+"'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblTrainingTitle.setText(rs.getString("title").toUpperCase()); 
           }
        } catch (SQLException ex) {
            Logger.getLogger(SponsorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void loadParticipants(){
        String q = 
        "SELECT CONCAT(lastname,', ', firstname, ' ', middlename) AS 'Name', " +
        "emp_id, "+
        "employee.position_code, "+
        "UPPER(position.position_name) AS 'Position' " +
        "FROM employee, POSITION " +
        "WHERE employee.position_code = position.position_code;";
        
        rs = db.displayRecords(q);
        try {
            flpTrainees.getChildren().clear();
            while(rs.next()){
                FXMLLoader l =  new FXMLLoader(getClass().getResource("ParticipantTitledPane.fxml"));                                                            
                ParticipantTitledPaneController controller = new ParticipantTitledPaneController();                                                
                controller.setId(rs.getString("emp_id"));
                System.out.println("From Parent container:"+rs.getString("emp_id"));                
                Node fp = l.load();
                l.setController(controller);
                flpTrainees.getChildren().add(fp);                  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipantsController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParticipantsController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        
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
            Logger.getLogger(ParticipantsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
            Logger.getLogger(ParticipantsController.class.getName()).log(Level.SEVERE, null, ex);            
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


    @FXML
    private void searchTrainees(KeyEvent event) {
        String s = tfSearchTrainees.getText().trim();
        if(s != null){        
            String filter = "lastname LIKE '%"+s+"%'\n";
            if(searchBy.equals("firstname")){
                filter = "firstname LIKE '%"+s+"%'\n";
            }
            if(searchBy.equals("middlename")){
                filter = "middlename LIKE '%"+s+"%'\n";
            }            
            if(searchBy.equals("position")){
                filter = "position_name LIKE '%"+s+"%'\n";
            }
            if(searchBy.equals("position code")){
                filter = "employee.position_code LIKE '%"+s+"%'\n";
            } 
              
            try {
                
            
            String q =  "SELECT emp_id ,CONCAT(lastname,', ', firstname, ' ', middlename) AS 'Name', \n" +
                        "UPPER(position.position_name) AS 'Position'\n" +
                        "FROM employee\n" +
                        "INNER JOIN POSITION\n"+
                        "ON employee.position_code = position.position_code\n"+
                        "WHERE "+filter+
                        " ORDER BY employee.lastname";

            rs = db.displayRecords(q);
            
                flpTrainees.getChildren().clear();
                
                while(rs.next()){
                    FXMLLoader l =  new FXMLLoader(getClass().getResource("ParticipantTitledPane.fxml"));                                                            
                    ParticipantTitledPaneController controller = new ParticipantTitledPaneController();                                                
                    controller.setId(rs.getString("emp_id"));
                    System.out.println("From Parent container:"+rs.getString("emp_id"));                
                    Node fp = l.load();
                    l.setController(controller);
                    flpTrainees.getChildren().add(fp);                  
                }
            } catch (SQLException ex) {
                Logger.getLogger(ParticipantsController.class.getName())
                      .log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ParticipantsController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }             
        }else{
            flpTrainees.getChildren().clear();
            flpTrainees.getChildren().add(new Text("No Result"));
        }
    }

    public String searchBy = "lastname";
    @FXML
    private void setSearchBy(ActionEvent event) {
        searchBy = cmbSearchBy.getSelectionModel().getSelectedItem().toLowerCase().trim();
        System.out.println(searchBy);
    }
}
