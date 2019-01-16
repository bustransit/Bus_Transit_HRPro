/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.core.ticketing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;
import utilities.Notification;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TicketingController extends Application implements Initializable {
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    Notification notif = new Notification();  
    
    
    @FXML
    private JFXTextField bus_no;
    @FXML
    private JFXComboBox<String> from;
    @FXML
    private JFXComboBox<String> to;
    @FXML
    private JFXButton ticket_save;
    @FXML
    private Label lbl_tckt_no;
    @FXML
    private Label lbl_fare;
    @FXML
    private JFXTextField qty;
    @FXML
    private Label lblTotalFare;
    @FXML
    private JFXTextField tfStudent;
    @FXML
    private JFXTextField tfPWD;
    @FXML
    private JFXTextField tfSenior;
    @FXML
    private Label lblTotalDiscount;
    @FXML
    private Label lblTicketNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       db.populateComboBox("SELECT departure FROM core_ticket_route", from);
       
       db.populateComboBox("SELECT arrival FROM core_ticket_route", to);
       
       lbl_fare.setText(totalFare+"");
    }    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Ticketing.fxml"));        
        Scene scene = new Scene(root);        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }             
    
    @FXML
    private void setDeparture(ActionEvent event) {
        setFare();
        
    }

    
    
    @FXML
    private void setArrival(ActionEvent event) {
        setFare();
    }

    double routePrice = 0.0;
    double totalFare = 0.0;
    double totalDiscount = 0.0;
    double disctount = 0.2;
    int quantity = 0;
    private void setFare(){
        String departure = from.getSelectionModel().getSelectedItem().toString();
        String arrival = to.getSelectionModel().getSelectedItem().toString();
        String q =  "SELECT price FROM core_ticket_route WHERE\n" +
                    "departure = '"+departure+"'\n" +
                    "AND arrival = '"+arrival+"';";
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                routePrice = rs.getDouble("price");
                lbl_fare.setText(rs.getString(routePrice+""));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketingController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }            
    
    @FXML
    private void save(ActionEvent event) {
        setTotalFare();
    }

    @FXML
    private void setStudDiscount(ActionEvent event) {
    }

    @FXML
    private void setPwdDiscount(ActionEvent event) {
    }

    @FXML
    private void setSeniorDiscount(ActionEvent event) {
    }

    @FXML
    private void setQty(ActionEvent event) {
        
    }   
    
    private void setTotalFare(){
        try{
            quantity = Integer.parseInt(qty.getText().trim());
            
            int pwd = Integer.parseInt(tfPWD.getText().trim());
            int stdnt = Integer.parseInt(tfStudent.getText().trim());
            int snr = Integer.parseInt(tfSenior.getText().trim());
            
            double d = 0.2;
            /**
             * qty = 8
             * price = 600
             * pwd = 3 * 0.2
             * student = 2 * 0.2
             * senior = 1 * 0.2
             * Total Fare = 4800
             * Discounted = 4080
             */
            totalDiscount = (((pwd + stdnt + snr) * routePrice)) * 0.2;
            // with discount;
            totalFare = (quantity * routePrice) - totalDiscount;
                                                
            lblTotalFare.setText("Php: "+totalFare);  
            lblTotalDiscount.setText(totalDiscount+"");
            lbl_fare.setText(routePrice+"");
            
            String ticketNo = db.getRandom(7);
            
            lblTicketNumber.setText(ticketNo);
            
            String q = "INSERT INTO core_ticket_transaction (amount, discount, TIMESTAMP) "
                    + "VALUES ("+totalFare+", "+totalDiscount+", CURRENT_TIME);";
            
            db.execute(q);
            
        }catch(NumberFormatException e){
            notif.showDarkErrorNotif("ERROR", "Check input", Pos.BOTTOM_RIGHT, 2.5);
        }           
    }
}
