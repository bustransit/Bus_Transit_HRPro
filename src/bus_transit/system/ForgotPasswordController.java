/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.system;


import bus_transit.Directories;
import bus_transit.DashboardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class ForgotPasswordController 
       implements Initializable {

    @FXML private JFXButton btnBack;
    @FXML private JFXTextField tfEmpCode;
    @FXML private JFXTextField tfUsername;
    @FXML private JFXTextField tfSuperVisorCode;
    @FXML private JFXButton btnReset;
    @FXML private Label generatedPassword;
    @FXML private VBox vBoxReset;
    
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;
    int r;


    public ObservableList chr;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        // back
//        btnBack.setOnAction(e->{
//            try {
//                Stage stage = (Stage) btnBack.getScene().getWindow();
//                stage.close();
//                Stage dash = new Stage(StageStyle.UNDECORATED);
//                Parent root;               
//                root = FXMLLoader.load(getClass().getResource("src/bus_transit/Login.fxml"));
//                Scene scene = new Scene(root);
//                dash.setScene(scene);
//                dash.setMaximized(true);
//                dash.show();                
//            } catch (IOException ex) {
//                Logger.getLogger(ForgotPasswordController.class.getName()).log(Level.SEVERE, null, ex);
//            }        
//        });
        
        vBoxReset.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch (e.getCode()) {
                    case ENTER:                        
                        if (checkFromDB(tfEmpCode.getText(), tfUsername.getText(), tfSuperVisorCode.getText())) {
                            btnReset.setDisable(false);
                            btnReset.requestFocus();
                        }
                        break;
                    default:
                        break;
                }
            }
        });    
        
        btnReset.setOnAction(e->{
            char[] chrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_$#@".toCharArray();
            Random n = new Random();
            StringBuilder sb = new StringBuilder((100000 + n.nextInt(900000)));
            for(int i=0; i < 8; i++){
                sb.append(chrs[n.nextInt(chrs.length)]);
            }
            
            generatedPassword.setText(sb.toString());
            
            q = "UPDATE `user` "
                    + "SET `password` = '"+sb.toString()+"' "
                    + "WHERE `user`.`emp_id` = "+tfEmpCode.getText();
            
            db.execute(q);
            
            showAlert("Account Password Reset", 
                    "Password Successfully Reset", 
                    "We highly recommend you!\n"
                    + "to change this "
                    + "generated password immediately.\n"
                    + "New Password: "+ sb.toString(), 
                    AlertType.INFORMATION);
            
            vBoxReset.setDisable(true);
        });                         
    }   

    private void showAlert(String title, String header, String content, AlertType type) {        
        Alert dlg = new Alert(type, "");
        dlg.setHeaderText(header);
        dlg.setTitle(title);
        dlg.setContentText(content);
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.show();        
    }     
    
    private Alert createAlert(AlertType type) {
        //Window owner = cbSetOwner.isSelected() ? stage : null;
        Alert dlg = new Alert(type, "");
        dlg.initModality(Modality.APPLICATION_MODAL);
        //dlg.initOwner(window);
        return dlg;
    }     
    
    private boolean checkFromDB(String empID, 
                              String username,
                              String supervisorCode){        
        boolean r = false;
        q = "SELECT employee.immediate_supervisor_code, "
                + "user.username, "
                + "user.emp_id "
                + "FROM employee, user "
                + "WHERE user.emp_id='"+empID+"'"
                + " AND user.username = '"+username+"'"
                + " AND employee.immediate_supervisor_code="+supervisorCode;

        
        rs = db.displayRecords(q);
        
        try {
            if(rs.next()){
                r = true;
            }else{
                return r;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return r;
    }    
    
    private boolean checkEmployeeUsername(String table, 
                              String column,
                              String textToSearch){        
        boolean r = false;
        q = "SELECT '"+column
                      +"' FROM "
                      +table
                      +" WHERE "
                      +column+"='"
                      +textToSearch
                      +"'";
        
        rs = db.displayRecords(q);
        
        try {
            if(rs.next()){
                r = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @FXML
    private void back(ActionEvent event) throws IOException {        
        Stage stage = (Stage) btnBack.getScene().getWindow();         
        stage.close();
        Stage dash = new Stage(StageStyle.UNDECORATED);
        Directories dir = new Directories();
        //Parent root = FXMLLoader.load(getClass().getResource(dir.login.getFile()));
        Parent root = FXMLLoader.load(getClass().getResource(dir.login.getPath()));
        Scene scene = new Scene(root);
        dash.setScene(scene);
        dash.setMaximized(true);
        dash.show();                
    }
    
    private void loadFunction(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();
        System.out.println(file);
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(file));
            pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
            DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
            DashboardController.root.getChildren().add(pane);
            DashboardController.draw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    } // loadFunction ends here    
    
//    @Override
//    public void start(Stage stage) throws Exception {
//        //f.loadFunctions("hr");
//        Parent root = FXMLLoader.load(getClass()
//                                .getResource("ForgotPassword.fxml"));        
//        Scene scene = new Scene(root);        
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setMaximized(true);
//        stage.setScene(scene);
//        stage.show();                
//    }
//    
//    public static void main(String[] args) {
//        launch(args);
//    }           
}
