package utilities;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.corba.se.pept.transport.ContactInfo;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.CustomTextField;

/**
 *
 * @author DelaTorreNelson
 */
public class Validator {
    Notification notif = new Notification();    
    // return true if valid email
    public boolean isValidEmail(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }   
    
    // return true if valid text
    public boolean isTextOnly(String t){
        return t.chars().allMatch(Character::isLetter);   
    }

    // return true if number only
    public boolean isNumberOnly(String t){
        return t.matches("[0-9]+");
    }    
    
    /**
     * Return true if matches on regex
     * @param t is the string input
     * @param n is the length of the integer we want to check
     * @return true
     */
    public boolean isInteger(String t, int n){
        return t.matches("\\d{"+n+"}");
    }
            
    // Double number with a precision of 2 digit
    public boolean isDoubleOnly(String t){        
        return t.matches("\\d+(\\.\\d{0,2})?");
    }
    
    /**
     * Check to see if t is a Double with precision of i
     * @param t text input
     * @param i precision after dot
     * @return true
     */
    public boolean isDoubleOnly(String t, int i){
        return t.matches("\\d+(\\.\\d{1,"+i+"})?");
    }    
            
    // returns true if the input is valid contact number    
    public boolean isValidContactNumber(String n){
        String regexStr = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regexStr);
        java.util.regex.Matcher m = p.matcher(n);
        return m.matches();
    }
    
    public boolean isNull(String n){
        return n.matches(null);
    }
    
    // returns a message notif if not a Number only
    public void validateNumber(CustomTextField t, String msg){            
        String s = t.getText().trim();
        if(!isNumberOnly(s)){notif.showDarkErrorNotif("Wrong input", msg, Pos.CENTER, 2.0);}
    }
    
    public void validateInteger(CustomTextField t, int i, String msg){
        String s = t.getText().trim();
        if(!isInteger(s, i)){notif.showDarkErrorNotif("Wrong input", msg, Pos.CENTER, 2.0);}
    }    
    
    // returns a message notif if not a Number only
    public void validateDouble(CustomTextField t, String msg){            
        String s = t.getText().trim();
        if(!isDoubleOnly(s)){notif.showDarkErrorNotif("Wrong input", msg, Pos.CENTER, 2.0);}
    }    
    
    public void setRequired(Node t){
        t.getStyleClass().add("required");
        Object f;
        
        if(t instanceof TextField){ f = new TextField(); }   
        if(t instanceof CustomTextField){ f = new CustomTextField(); }
        if(t instanceof JFXTextField){ f = new JFXTextField(); }
        if(t instanceof TextArea){ f = new TextArea(); }
        if(t instanceof JFXTextArea){ f = new JFXTextArea(); }                
    }
    

}// end Validator here
