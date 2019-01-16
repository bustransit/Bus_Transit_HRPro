/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.attendance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.SegmentedButton;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class DailyTimeRecordController extends 
             Application implements
             Initializable {
    
    DBUtilities db  = new DBUtilities();
    ResultSet rs;
    String qry;
    String empId;
    String timeEntry;
    
    private Timestamp tm = null;
    
    @FXML
    private SegmentedButton segmentedButton;
    @FXML
    private JFXButton btnOk;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXTextField tfEmpId;
    @FXML
    private Label lblEmpName;
    
    private int hour;
    private int minute;
    private int second;
    private int am_pm;
    private int month;
    private int day;
    private int year;    
    @FXML
    private Label txt_time;
    @FXML
    private Label txt_date;
    private JFXTimePicker tpStart;
    private JFXTimePicker tpEnd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TimeDate();
        ToggleGroup group = new ToggleGroup();
        ToggleButton btnTimeIn = new ToggleButton("Time In");
        ToggleButton btnLunchOut = new ToggleButton("Lunch Out");
        ToggleButton btnLunchIn = new ToggleButton("Lunch In");
        ToggleButton btnBreakOut = new ToggleButton("Break Out");
        ToggleButton btnBreakIn = new ToggleButton("Break In");        
        ToggleButton btnTimeOut = new ToggleButton("Time Out");
                
        segmentedButton.getButtons().add(btnTimeIn);
        segmentedButton.getButtons().add(btnLunchOut);
        segmentedButton.getButtons().add(btnLunchIn);
        segmentedButton.getButtons().add(btnBreakOut);
        segmentedButton.getButtons().add(btnBreakIn);
        segmentedButton.getButtons().add(btnTimeOut);
        
        segmentedButton.setToggleGroup(group);        
        
        for (ToggleButton tgl : segmentedButton.getButtons()) {
            tgl.setOnAction(value->{                
               timeEntry = tgl.getText();
            });            
        }
        
        tfEmpId.setOnAction(e->{
            String id = tfEmpId.getText();
            qry = "SELECT * FROM employee WHERE emp_id='"+id+"'";
            rs = db.displayRecords(qry);
            try {
                if(rs.next()){
                    empId = rs.getString("emp_id");
                    String f = rs.getString("firstname");
                    String l = rs.getString("lastname");
                    lblEmpName.setText((timeEntry +": "+l +", "+ f).toUpperCase());                    
                }else{
                    lblEmpName.setText("Not Found!".toUpperCase());
                }                
            } catch (SQLException ex) {
                Logger.getLogger(DailyTimeRecordController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }            
        });
                        
        btnOk.setOnAction(e->{
            Date timeStamp = new Date();
            String strTimeEntry = new SimpleDateFormat("hh:mm").format(timeStamp).toString();
            String dateToday = new SimpleDateFormat("yyyy-MM-dd").format(timeStamp).toString();
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SS");
            timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

//            long start = System.currentTimeMillis();
//            long end = System.currentTimeMillis();

            long start =(long) Long.parseLong(tpStart.getValue().toString());
            long end = (long) Long.parseLong(tpEnd.getValue().toString());
            long elapse = end - start;
            int s = tpEnd.getValue().compareTo(tpStart.getValue());
            System.out.println("Compare to: "+s);
            String el = timeFormat.format(elapse);
            System.out.println("Start time: "+start);
            System.out.println("End time: "+end);
            System.out.println("Elapse time: "+el);
            
            tm = new Timestamp(System.currentTimeMillis());

            if(timeEntry.equals("Time In")){                  
              inputTimeIn(empId);                  
            }

            if(timeEntry.equals("Lunch Out")){
              inputLunchOut(empId);
            }

            if(timeEntry.equals("Lunch In")){
              inputLunchIn(empId);
            }

            if(timeEntry.equals("Break Out")){
                inputBreakOut(empId);
            }

            if(timeEntry.equals("Break In")){                
                inputBreakIn(empId);
            }

            if(timeEntry.equals("Time Out")){
                inputTimeOut(empId);
            }  
            System.out.println(timeEntry);
            System.out.println(empId);
//            System.out.println("Current time stamp: "+tm);
//            System.out.println(elapse);    
//            System.out.println(timeEntry+" : "+strTimeEntry);
//            System.out.println("Date: "+dateToday);                
//            System.out.println("TimeStamp: "+timeStamp.toString());             
        });
        
        btnClear.setOnAction(e->{
            tfEmpId.setText("");
            lblEmpName.setText("Select time entry, type your employee code then click OK");
        });        
    }    

    private void inputTimeIn(String id){        
//        String q =  "SET @empId := "+id+";" +
//                    "SET @timeInput := CURTIME();" +                
//                    "SET @schedTime := '08:00:00';" +
//                    "SET @late := (TIMEDIFF(@timeInput,@schedTime));" +
//                    "SET @lateToDouble := CAST(TIME_TO_SEC(@late) / (60 * 60) AS DECIMAL(10,1));" +
//                    "SET @remarks := IF(@lateToDouble = 0, 'on time',(IF(@lateToDouble > 0, 'late','early')));" +
//                    "INSERT INTO timesheet (emp_id, time_in,time_in_remarks, total_late, DATE)" +
//                    "VALUES(@empId, @timeInput, @remarks,@late,CURDATE());";
        
        String q = "INSERT INTO timesheet (emp_id, time_in,time_in_remarks, total_late, DATE)" +
                    "VALUES("+id+", CURTIME(), IF(CAST(TIME_TO_SEC(TIMEDIFF(CURTIME(),'08:00:00')) / (60 * 60) AS DECIMAL(10,1)) = 0, 'on time',(IF(CAST(TIME_TO_SEC(TIMEDIFF(CURTIME(),'08:00:00')) / (60 * 60) AS DECIMAL(10,1)) > 0, 'late','early'))),TIMEDIFF(CURTIME(),'08:00:00'),CURDATE());";
                    //"SELECT emp_id, time_in, time_in_remarks, 'date' FROM timesheet WHERE 'date' = CURDATE() AND emp_id="+id;
        
                    db.execute(q);
                    
        q = "SELECT emp_id, time_in, time_in_remarks, DATE "
                + "FROM timesheet "
                + "WHERE DATE = CURRENT_DATE() "
                + "AND emp_id="+id;
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblEmpName.setText("OK");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
            Logger.getLogger(DailyTimeRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void inputLunchOut(String id){
//        String q =  "SET @empId := "+id+";" +
//                    "SET @timeInput := CURTIME();" +                
//                    "SET @schedTime := '08:00:00';" +
//                    "SET @late := TIMEDIFF(@timeInput,@schedTime);" +
//                    "SET @lateToDouble := CAST(TIME_TO_SEC(@late) / (60 * 60) AS DECIMAL(10,1));" +
//                    "SET @remarks := IF(@lateToDouble = 0, 'on time',(IF(@lateToDouble > 0, 'late','early')));" +
//                    "INSERT INTO timesheet (emp_id, time_in,time_in_remarks, total_late, DATE)" +
//                    "VALUES(@empId, @timeInput, @remarks,@late,CURDATE());";
        
//        String q =  "UPDATE timesheet " +
//                    "SET lunch_out = CURTIME()," +
//                    "lunch_out_remarks = IF((CAST(TIME_TO_SEC( (TIMEDIFF(CURTIME(),'08:00:00'))) / (60 * 60) AS DECIMAL(10,1))) = 0, 'on time',(IF((CAST(TIME_TO_SEC( (TIMEDIFF(CURTIME(),'08:00:00'))) / (60 * 60) AS DECIMAL(10,1))) > 0, 'late','early'))) " +
//                    "WHERE DATE = CURDATE() " +
//                    "AND emp_id ="+id;
          String q = "UPDATE timesheet SET lunch_out = CURTIME() WHERE DATE = CURDATE() AND emp_id = "+id;

                    db.execute(q);
                    
        q = "SELECT emp_id, lunch_out, lunch_out_remarks, DATE "
                + "FROM timesheet "
                + "WHERE DATE = CURRENT_DATE() "
                + "AND emp_id="+id;
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblEmpName.setText("OK");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
            Logger.getLogger(DailyTimeRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } 
    
    private void inputLunchIn(String id){
          String q = "UPDATE timesheet SET lunch_in = CURTIME() WHERE DATE = CURDATE() AND emp_id = "+id;

                    db.execute(q);
                    
        q = "SELECT emp_id, DATE "
                + "FROM timesheet "
                + "WHERE DATE = CURRENT_DATE() "
                + "AND emp_id="+id;
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblEmpName.setText("OK");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
            Logger.getLogger(DailyTimeRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    private void inputBreakOut(String id){
          String q = "UPDATE timesheet SET break_out = CURTIME() WHERE DATE = CURDATE() AND emp_id ="+id;

                    db.execute(q);
                    
        q = "SELECT emp_id, DATE "
                + "FROM timesheet "
                + "WHERE DATE = CURRENT_DATE() "
                + "AND emp_id="+id;
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblEmpName.setText("OK");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
            Logger.getLogger(DailyTimeRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }      

    private void inputBreakIn(String id){
          String q = "UPDATE timesheet SET break_in = CURTIME() WHERE DATE = CURDATE() AND emp_id ="+id;

                    db.execute(q);
                    
        q = "SELECT emp_id, DATE "
                + "FROM timesheet "
                + "WHERE DATE = CURRENT_DATE() "
                + "AND emp_id="+id;
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblEmpName.setText("OK");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
            Logger.getLogger(DailyTimeRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }   
    
    private void inputTimeOut(String id){
          String q = "UPDATE timesheet SET time_out = CURTIME() WHERE DATE = CURDATE() AND emp_id ="+id;

                    db.execute(q);
                    
        q = "SELECT emp_id, DATE "
                + "FROM timesheet "
                + "WHERE DATE = CURRENT_DATE() "
                + "AND emp_id="+id;
        
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblEmpName.setText("OK");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
            Logger.getLogger(DailyTimeRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }    
    
    private void TimeDate() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR);
            minute = cal.get(Calendar.MINUTE);
            second = cal.get(Calendar.SECOND);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DATE);
            year = cal.get(Calendar.YEAR);
            am_pm = cal.get(Calendar.AM_PM);

            String date = month + "-" + day + "-" + year;
            if (month == 0) {
                date = ("January " + day + ", " + year);
            } else if (month == 1) {
                date = ("Febuary " + day + ", " + year);
            } else if (month == 2) {
                date = ("March " + day + ", " + year);
            } else if (month == 3) {
                date = ("April " + day + ", " + year);
            } else if (month == 4) {
                date = ("May " + day + ", " + year);
            } else if (month == 5) {
                date = ("June " + day + ", " + year);
            } else if (month == 6) {
                date = ("July " + day + ", " + year);
            } else if (month == 7) {
                date = ("August " + day + ", " + year);
            } else if (month == 8) {
                date = ("September " + day + ", " + year);
            } else if (month == 9) {
                date = ("October " + day + ", " + year);
            } else if (month == 10) {
                date = ("November " + day + ", " + year);
            } else if (month == 11) {
                date = ("December " + day + ", " + year);
            }

            String day_night = "";
            if (am_pm == 1) {
                day_night = "PM";
            } else {
                day_night = "AM";
            }

            if (hour == 0) {
                txt_time.setText("12" + ":" + minute + ":" + second + " " + day_night);
                txt_date.setText(date);
            } else {
                txt_time.setText(hour + ":" + minute + ":" + second + " " + day_night);
                txt_date.setText(date);
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DailyTimeRecord.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }    
}
