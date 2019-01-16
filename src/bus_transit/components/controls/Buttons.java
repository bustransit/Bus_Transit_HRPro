/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.components.controls;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;

/**
 *
 * @author NelsonDelaTorre
 */

public class Buttons {
    double p = 4;
    
    public JFXButton BlueButton(){        
        JFXButton b = new JFXButton();
        b.getStyleClass().add("btn");
        b.getStyleClass().add("blue-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    } 
    
    public JFXButton BlueButton(String s){        
        JFXButton b = new JFXButton(s);
        b.getStyleClass().add("btn");
        b.getStyleClass().add("blue-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    }    
    
    public JFXButton GreenButton(){        
        JFXButton b = new JFXButton();
        b.getStyleClass().add("green-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    }   
    
    public JFXButton GreenButton(String s){        
        JFXButton b = new JFXButton(s);
        b.getStyleClass().add("green-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    }      
    
    public JFXButton OrangeButton(){        
        JFXButton b = new JFXButton();
        b.getStyleClass().add("orange-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    }     
    
    public JFXButton RedButton(){        
        JFXButton b = new JFXButton();
        b.getStyleClass().add("red-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    }
    
    public JFXButton RedButton(String s){        
        JFXButton b = new JFXButton(s);
        b.getStyleClass().add("red-button");
        // to add custom style
        b.getStylesheets().add(this.getClass().getResource("ui.css").toExternalForm());
        
        b.setPadding(new Insets(p));                
        return b;
    }    
}
