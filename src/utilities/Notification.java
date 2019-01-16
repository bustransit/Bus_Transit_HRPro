/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author NelsonDelaTorre
 */

public class Notification {
    /**
     * Notifications     
     */    
    public void showNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = notifCreator(title, text, pos, delay);        
        notif.show();
    }
    
    public void showWarningNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = notifCreator(title, text, pos, delay);        
        notif.showWarning();
    }        

    public void showConfirmNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = notifCreator(title, text, pos, delay);        
        notif.showConfirm();
    }       
    
    public void showErrorNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = notifCreator(title, text, pos, delay);        
        notif.showError();
    } 

    public void showInfoNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = notifCreator(title, text, pos, delay);       
        notif.showInformation();
    }
    
    // Notif Builder
    private Notifications notifCreator(String title, String text, Pos pos, Double delay){        
        title = (title == null ) ?"" : title;
        text = (text == null ) ? "" : text;
        delay = (delay == null ) ? 3 : delay;
        pos = (pos == null ) ? Pos.BOTTOM_RIGHT : pos;
                
        Notifications notif = Notifications.create()
                .title(title)
                .text(text)                
                .hideAfter(Duration.seconds(delay))                
                .position(pos); 
        
        return notif;
    }    

    /**
     * Dark Notification     
     */
    
    public void showDarkNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = darkNotifCreator(title, text, pos, delay);        
        notif.show();
    }
    
    public void showDarkWarningNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = darkNotifCreator(title, text, pos, delay);                
        notif.showWarning();
    }        

    public void showDarkConfirmNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = darkNotifCreator(title, text, pos, delay);        
        notif.showConfirm();
    }       
    
    public void showDarkErrorNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = darkNotifCreator(title, text, pos, delay);        
        notif.showError();
    } 

    public void showDarkInfoNotif(String title, String text, Pos pos, Double delay){
        Notifications notif = darkNotifCreator(title, text, pos, delay);        
        notif.showInformation();
    }            

    // Dark Notif Builder
    private Notifications darkNotifCreator(String title, String text, Pos pos, Double delay){        
        title = (title == null ) ?"" : title;
        text = (text == null ) ? "" : text;
        delay = (delay == null ) ? 3 : delay;
        pos = (pos == null ) ? Pos.BOTTOM_RIGHT : pos;
                
        Notifications notif = Notifications.create()
                .title(title)
                .text(text)                
                .hideAfter(Duration.seconds(delay))
                .darkStyle()
                .position(pos);         
        return notif;
    }        

    // Client Notif
    public void pushNotif(){
        
    }
    
}
