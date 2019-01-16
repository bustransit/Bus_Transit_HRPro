/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.system;

import bus_transit.DashboardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class UserManagementController extends
             Application implements
             Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String qry;
    Modals mdls = new Modals();
    
    private Stage stage;

    @FXML private JFXTextField tfEmpCode;
    @FXML private JFXTextField tfUsername;
    @FXML private JFXButton btnAddUser;
    @FXML private JFXPasswordField pfPassword;
    @FXML private TableView<?> tblUser;
    @FXML private VBox newUser;
    @FXML private JFXButton btnAddNewUser;
    @FXML private JFXTextField tfEmpId;
    @FXML private Label lblResult;
    @FXML private ContextMenu context;
    @FXML private MenuItem viewProfile;
    @FXML private MenuItem editInfo;
    @FXML private MenuItem archiveAccount;
    String selectedRec;
    @FXML
    private StackPane stackpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle ddddrb) {
        qry = "SELECT emp_id, username FROM user";        
        db.buildData(qry, tblUser);
        
        btnAddNewUser.setOnAction(e->{
            BooleanProperty b = newUser.visibleProperty();
            if(b.getValue()){
                newUser.setVisible(false);                
                System.out.println(b.getValue());
            }else{
                newUser.setVisible(true);
                System.out.println(b.getValue());
            }                        
        });
        
        
        
        btnAddUser.setOnAction(e -> {
            addUser(tfEmpCode.getText(),                    
                    tfUsername.getText(),
                    pfPassword.getText());            
            mdls.showAlert("New User");
        });
        
        tblUser.setOnMouseClicked(ev->{
            ObservableList<String> r = (ObservableList<String>) 
                    tblUser.getSelectionModel().getSelectedItem();            
            String id = r.get(0);
            selectedRec = id;
            
//            String q = "SELECT * FROM user WHERE emp_id="+selectedRec;
//            try {
//                if(rs.next()){ 
//                    
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
        });   
        
        tfEmpId.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JFXTextField tf = (JFXTextField) e.getSource();
                String searchID = tf.getText();                
            }
        });        
        
        tfEmpId.setOnKeyTyped(e->{
            JFXTextField tf = (JFXTextField) e.getSource();
            String searchID = tf.getText();
            qry = "SELECT emp_id, lastname, firstname, middlename, birthdate FROM employee WHERE emp_id='"+searchID+"'";
            rs = db.displayRecords(qry);
            try {
                if(rs.next()){
                    db.buildData(qry, tblUser);
                    lblResult.setText("");
                }else{
                    qry = "SELECT emp_id, lastname, firstname, middlename, birthdate FROM employee";
                    db.buildData(qry, tblUser);
                    lblResult.setText("Employee ID Not Found!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });
        
        tfEmpCode.setOnKeyTyped(e->{
            JFXTextField tf = (JFXTextField)e.getSource();
            String s = tf.getText();
            qry = "SELECT emp_id, lastname, firstname FROM employee WHERE emp_id='"+s+"'";
            rs = db.displayRecords(qry);
            try {
                if(rs.next()){
                    db.buildData(qry, tblUser);
                    String u = rs.getString("firstname").toLowerCase();
                    String p = rs.getString("lastname").toLowerCase();
                    
                    tfUsername.setText(u);
                    pfPassword.setText(p);                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });
        
        editInfo.setOnAction(e->{
            JOptionPane.showMessageDialog(null,"editInfo");
        });
        
        viewProfile.setOnAction(e->{            
            String file = "Profile.fxml";                
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource(file));
                pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
                DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
                DashboardController.root.getChildren().add(pane);
                DashboardController.draw.close();
            } catch (IOException ex) {
                System.out.println(ex);
            } 
        });
        
        archiveAccount.setOnAction(e->{                                    
            Alert al = new Alert(Alert.AlertType.CONFIRMATION, "You are deleting record");
            al.setContentText("You are deleting records of "+selectedRec+"!");
            al.showAndWait();
            String m = al.getResult().getText().toLowerCase();
            if(m.equals("ok")){
                rs = db.displayRecords("SELECT COUNT(emp_id) as 'row' FROM bustransit_archieve.user");
                try {
                    archive("emp_id", selectedRec, "bustransit_master.user", "bustransit_archieve.user");
                    if(rs.next()){
                        int r = rs.getInt("row");
                        if(r > getArchiveCount()){

                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }                 
            }           
        });        
    }

    private void createEditorPane(String id){
        VBox editor = new VBox();
        editor.setFillWidth(true);
        editor.setSpacing(10);
        
        JFXTextField eID = new JFXTextField();
        editor.getChildren().add(eID);
        JFXTextField u = new JFXTextField();
        editor.getChildren().add(u);
        JFXPasswordField p = new JFXPasswordField();
        editor.getChildren().add(p);
        
    }
    
    private int getArchiveCount(){
        int i = 0;
        rs = db.displayRecords("SELECT COUNT(emp_id) as 'row' FROM bustransit_archieve.user");
        try {
            if(rs.next()){
                i = rs.getInt("row");                
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    @FXML
    private void archiveInfo(ActionEvent event) {
        // Shows dialog if close button is clicked
        StackPane stackpane = new StackPane();
        stackpane.toFront();

        Label header = new Label("Archive?");
        header.setFont(new Font("SansSerif", 12));

        Label body = new Label("Are you sure you want to exit?");
        body.setFont(new Font("SansSerif", 14));

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(header);
        layout.setBody(body);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(stackpane, layout, JFXDialog.DialogTransition.LEFT);
        dialog.setOverlayClose(false);

        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            dialog.close();
            Stage stage = (Stage) btn_yes.getScene().getWindow();
            stage.close();
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dialog.close();
            stackpane.toBack();
        });

        layout.setActions(btn_cancel, btn_yes);
        dialog.show();
    }      
    
    private void addUser(String e, String u, String p) {
        if (!(e.isEmpty() && u.isEmpty() && p.isEmpty())) {

            qry = "SELECT emp_id FROM user WHERE emp_id=" + e;

            rs = db.displayRecords(qry);

            try {
                if (!rs.next()) {
                    if(isOnEmployeeMasterList(e)){
                        qry = "INSERT INTO "
                                + "`user` (`emp_id`, `username`, `password`) "
                                + "VALUES ('" + e + "', '" + u + "', '" + p + "')";

                        db.execute(qry);

                        JOptionPane.showMessageDialog(null, 
                                "Successfully added new user");                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Not on Employee Master List!");                                                    
                    }
                }else{
                    JOptionPane.showMessageDialog(null, 
                            "System User Already Registered!");                            
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserManagementController
                        .class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isOnEmployeeMasterList(String id){
        boolean v = false;
        String q = "SELECT emp_id FROM employee WHERE emp_id='"+id+"'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                v = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
    private void archive(String columnNameForID, String deletingID, String sourceTbl, String archiveTbl){                        
        String q = "INSERT INTO " +archiveTbl+" "+
                   "SELECT * FROM "+sourceTbl+" WHERE "+sourceTbl+"."+columnNameForID+" = '"+deletingID+"'";
        db.execute(q);
        q = "DELETE FROM "+sourceTbl+" WHERE "+sourceTbl+"."+columnNameForID+" = '"+deletingID+"'";
        db.execute(q);
        new Alert(Alert.AlertType.INFORMATION,"Record is on archive now.").show();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("UserManagement.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }    

    @FXML
    private void selectUserData(MouseEvent event) {
        
    }
}