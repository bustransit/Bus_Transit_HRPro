/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.components.controls;

import bus_transit.Directories;
import javafx.scene.control.Hyperlink;

/**
 *
 * @author NelsonDelaTorre
 */

public class EmployeeInfoLink {
    Directories dir = new Directories();
    public Hyperlink EmployeeInfo(String t){
        Hyperlink l = new Hyperlink(t);
        l.setUserData(l);
        //l.setAccessibleText(dir.employeeInfo);        
        return l;
    }
    
    public Hyperlink goToPositionInfo(String id){
        Hyperlink l = new Hyperlink();
        String f = "";
        
        return l;        
    }
}
