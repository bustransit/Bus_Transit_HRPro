/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author NelsonDelaTorre
 */

public class TextUtilities {
    public void swapToTextField(Text t){
        String id = t.getId();
        String s = t.getText();
        Node n = t.getParent();
        n.lookup(id);
        if(n instanceof Pane){
            ((Pane) n).getChildren().remove(t);
        }
        TextField tx = new TextField(s);        
        tx.requestFocus();
    }
}
