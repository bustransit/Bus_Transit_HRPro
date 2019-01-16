/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.attendance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TimePickerSampleController extends Application implements Initializable {

    @FXML
    private JFXTimePicker tpStart;
    @FXML
    private JFXTimePicker tpEnd;
    @FXML
    private JFXButton btnOK;
    @FXML
    private JFXDatePicker dpDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TimePickerSample.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);        
    }       

    int currentMinute = 0;
    int currentHour = 0;
    Timeline clock;
    public void backgroundRefresh(){
        clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {    
            /**
             * Continuous background process here
             * will update every int refreshRate;
             */
            
            currentMinute = LocalDateTime.now().getMinute();
            currentHour = LocalDateTime.now().getHour();
            
            if(currentMinute == end.getMinute()){
                System.out.println("Time is up...");
                clock.stop();
            }                        
        }),
            new KeyFrame(javafx.util.Duration.seconds(1))
        );        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();          
    }    
    
    private LocalTime start;
    private LocalTime end;
    private long minutes;
    @FXML
    private void ok(ActionEvent event) {        
        start = tpStart.getValue();
        end = tpEnd.getValue();
        
        Timestamp tsmp = getTimestamp(dpDate, tpEnd);
        System.out.println(tsmp);
        
        Duration dur = Duration.between(start, end);               
        long hours = dur.toHours();
        minutes = dur.toMinutes();
        
        String totalTime = "00:00";
        
        if((hours > 0) || (minutes > 0)){
            // No negative value
            if((minutes % 60) != 0){
                minutes -= (60 * hours);                
            }
            totalTime = "Time: "+ hours+"."+String.format("%02d",minutes);
        }else{
            // if there is negative value
            totalTime = "Check time input carefully";
        }
                
        
        // place your QUERY here
        // 
        
        // Now display
        System.out.println(totalTime);
        backgroundRefresh();
    }
    
    private Timestamp getTimestamp(JFXDatePicker dp, JFXTimePicker tp){         
        LocalDate ld = dp.getValue();
        LocalTime lt = tp.getValue();
        return Timestamp.valueOf(LocalDateTime.of(ld, lt));        
    }
    
}
