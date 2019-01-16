/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 *
 * @author Llamera
 */
public class DashboardController extends Application implements Initializable {   
    private DBUtilities db;
    private ResultSet rs;
    
    
    @FXML private JFXButton btn_menu;
    private JFXButton btn_close;
    @FXML private JFXButton btn_minimize;
    private JFXButton btn_user;
    @FXML private Label txt_copyright;
    @FXML private Label txt_datetime;
    @FXML private StackPane stackpane;
    private AnchorPane AccountPanel;
    @FXML private JFXDrawer drawer;
    
    private int hour;
    private int minute;
    private int second;
    private int am_pm;
    private int month;
    private int day;
    private int year;

    @FXML private AnchorPane container;
    public static AnchorPane root;
    public static JFXDrawer draw;

    private PopOver pop;
    @FXML
    private AnchorPane header;
    @FXML
    private AnchorPane footer;
    @FXML
    private ScrollPane scrlContent;
    public static ScrollPane scrlCenterContent;
    @FXML
    private VBox vbxLearning;
    @FXML
    private Label lblNofTest;
    @FXML
    private Label lblNofModules;
    @FXML
    private VBox hbxTrainings;
    @FXML
    private AnchorPane mainContainer;
    public static Stage primaryStage;
    public static StackPane stckPne;
    @FXML
    private JFXButton btnNotif;
    @FXML
    private JFXButton btnUser;
    @FXML
    private Label lblNotifCount;
    @FXML
    private BorderPane bdrContainer;
    @FXML
    private JFXButton btnChats;
    @FXML
    private JFXButton btnNetStat1;
    @FXML
    private Label lblNotifCounter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // One DBUtilities instance only
        this.db = LoginController.db;
        
        Screen screen = Screen.getPrimary();
        double screenHeight = screen.getBounds().getHeight();
        double screenWidth = screen.getBounds().getWidth();        
        double headerHeight = header.getHeight();
        double footerHeight = footer.getHeight();
                        
        System.out.println("W: "+screenWidth);
        System.out.println("H: "+screenHeight);

        stackpane.setMinSize(screenWidth, (screenHeight - headerHeight)-footerHeight);        
        bdrContainer.setMinSize(screenWidth, screenHeight);
        
        this.db = LoginController.db;
        
        TimeDate();
        root = container;
        draw = drawer;
        stckPne = stackpane;
        scrlCenterContent = scrlContent;
        scrlCenterContent.setFitToWidth(true);
        pop = new PopOver(AccountPanel);        
        loadSidePane();  
        setNofModules();
        setNofTest();
        
        mainContainer.setOnMouseClicked((event) -> {
            drawer.close();
        });
    }    

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;        
        
        
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));        
        Scene scene = new Scene(root);        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }    
    
    public void loadSidePane(){
        // This will sets the sidepane
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("SidePane.fxml"));
            drawer.setSidePane(pane);
            //drawer.open();
        } catch (IOException e) {
            System.out.println(e);
        }        
    }
        
    @FXML
    private void OpenSideMenu(ActionEvent event) {
        // This shows and hides the sidepane
        if (drawer.isShown()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    private void Close(ActionEvent event) {
        // Shows dialog if close button is clicked
        stackpane.toFront();

        Label header = new Label("Exit Program?");
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

    private void archiveInfo(ActionEvent event) {
        // Shows dialog if close button is clicked
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
    
    
    // initialize number of Tests on dashboard
    private void setNofTest(){
        String q = "SELECT COUNT(test_id) AS 'n' FROM test";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblNofTest.setText(rs.getString("n"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // initialize number of Modules on dashbaord
    private void setNofModules(){
        String q = "SELECT COUNT(module_id) AS 'n' FROM learning_modules WHERE learning_modules.module_status='active'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblNofModules.setText(rs.getString("n"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    private void Minimize(ActionEvent event) {
        // Minimize window if minimize button is clicked
        Stage stage = (Stage) btn_minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    private void UserSettings(ActionEvent event) {
        // Popups a small window if user button is clicked        
        pop.setDetachable(false);
        pop.setAnimated(true);
        //pop.setAutoFix(true);
        pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        pop.setCornerRadius(0);        
        AccountPanel.setVisible(true);
        pop.show(btn_user);
    }

    private void AccountSettings(ActionEvent event) throws IOException{
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("system/Profile.fxml"));
            pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
            DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
            DashboardController.root.getChildren().add(pane);
            DashboardController.draw.close();
            stackpane.toBack();
        } catch (IOException ex) {
            System.out.println(ex);
        }              
    }

    /**
     * External Class user can use this function
     * to load modal from external command
     * @param fxml = FXML file to load
     * @param cntrl = Controller file for FXML
     * @param btnOk = close function from Controller file
     */
    public void loadModal(String fxml, Class cntrl, JFXButton btnOk){
        try {            
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(cntrl.getClass().getResource(fxml));
            stage.setScene(new Scene(root));
            stage.setTitle("New Job Qualification");          
            stage.initModality(Modality.APPLICATION_MODAL);                     
                             
            JFXDialogLayout dialog = new JFXDialogLayout();
            
            dialog.setBody(root);           
            JFXDialog dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);             
            dlg.show();           
            
            JFXButton save = new JFXButton("Save");
            save.setOnAction((sEvt) -> {
                
                dlg.close();
            });
            
            JFXButton cancel = new JFXButton("Cancel");
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });          
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SignOut(ActionEvent event) {
        // Shows dialog if sign out button is clicked
        stackpane.toFront();
        pop.hide();

        Label header = new Label("Sign Out?");
        header.setFont(new Font("SansSerif", 12));

        Label body = new Label("Are you sure you want to sign out?");
        body.setFont(new Font("SansSerif", 14));

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(header);
        layout.setBody(body);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(stackpane, layout, JFXDialog.DialogTransition.LEFT);
        
        //dialog.setOverlayClose(false); // set true if you want to close dialog upon clicking outside dialog area        

        // If YES, the scene will back to secondStage form
        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            try {
                dialog.close();
                Stage stage = (Stage) btn_user.getScene().getWindow();
                stage.close();
                Stage login = new Stage(StageStyle.UNDECORATED);
                Parent root = FXMLLoader.load(getClass()
                                        .getResource("Login.fxml"));
                Scene scene = new Scene(root);
                login.setScene(scene);
                login.setMaximized(true);
                login.show();
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        });

        // If NO, the dialog closes
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
    
    // Show Time and Date
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
                date = ("Jan " + day + ", " + year);
            } else if (month == 1) {
                date = ("Feb " + day + ", " + year);
            } else if (month == 2) {
                date = ("Mar " + day + ", " + year);
            } else if (month == 3) {
                date = ("Apr " + day + ", " + year);
            } else if (month == 4) {
                date = ("May " + day + ", " + year);
            } else if (month == 5) {
                date = ("Jun " + day + ", " + year);
            } else if (month == 6) {
                date = ("Jul " + day + ", " + year);
            } else if (month == 7) {
                date = ("Aug " + day + ", " + year);
            } else if (month == 8) {
                date = ("Sep " + day + ", " + year);
            } else if (month == 9) {
                date = ("Oct " + day + ", " + year);
            } else if (month == 10) {
                date = ("Nov " + day + ", " + year);
            } else if (month == 11) {
                date = ("Dec " + day + ", " + year);
            }

            String day_night = "";
            if (am_pm == 1) {
                day_night = "PM";
            } else {
                day_night = "AM";
            }

            if (hour == 0) {
                txt_datetime.setText(date + "   " + "12" + ":" + minute + ":" + second + " " + day_night);
                txt_copyright.setText("Copyright " + year + ". Transportation System. All Rights Reserved.");
            } else {
                txt_datetime.setText(date + "   " + hour + ":" + minute + ":" + second + " " + day_night);
                txt_copyright.setText("Copyright " + year + ". Transportation System. All Rights Reserved.");
            }            
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void UserManagement(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();       
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(file));
            pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
            DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
            DashboardController.root.getChildren().add(pane);
            DashboardController.draw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }      
    }

    @FXML
    private void showNotifPane(ActionEvent event) {
        lblNotifCounter.setVisible(false);        
        VBox vbxNotif = new VBox();
        vbxNotif.setSpacing(10.0);
        vbxNotif.setPadding(new Insets(10));

        for (int i = 0; i < 10; i++) {
            Label lbl = new Label("this is label");
            JFXButton btnNotifRow = new JFXButton("", lbl);        
            btnNotifRow.setCursor(Cursor.HAND);        
            vbxNotif.getChildren().add(btnNotifRow);            
        }        
        
        ScrollPane scrNotif = new ScrollPane(vbxNotif);

        // Popups a small window if user button is clicked  
        PopOver notifPane = new PopOver();
        notifPane.setDetachable(false);
        notifPane.setAnimated(true);        
        notifPane.setContentNode(scrNotif);
        notifPane.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        notifPane.setCornerRadius(4);                
        notifPane.show(btnNotif);        
    }

    @FXML
    private void showUserSetting(ActionEvent event) {        
        VBox vbxUserSetting = new VBox();
        vbxUserSetting.setSpacing(10.0);
        vbxUserSetting.setPadding(new Insets(10));
                
        JFXButton btnAccntStting = new JFXButton("Account Setting", 
                new MaterialDesignIconView(MaterialDesignIcon.SETTINGS));
        btnAccntStting.setCursor(Cursor.HAND);
        vbxUserSetting.getChildren().add(btnAccntStting);
        btnAccntStting.setOnAction((evt) -> {
            
        });
        
        JFXButton btnUserMgt = new JFXButton("User Management", 
                new MaterialDesignIconView(MaterialDesignIcon.SETTINGS));
        btnUserMgt.setCursor(Cursor.HAND);
        vbxUserSetting.getChildren().add(btnUserMgt);
        btnAccntStting.setOnAction((evt) -> {
            JFXButton b = (JFXButton) evt.getSource();
            String file = b.getAccessibleText().toString();       
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
        
        JFXButton btnSignOut = new JFXButton("Sign Out", 
                new MaterialDesignIconView(MaterialDesignIcon.SETTINGS));
        btnSignOut.setCursor(Cursor.HAND);
        vbxUserSetting.getChildren().add(btnSignOut); 
        btnSignOut.setOnAction((evt) -> {
            
        });
                
        PopOver poUserSetting = new PopOver(vbxUserSetting);
        poUserSetting.setDetachable(false);
        poUserSetting.setAnimated(true);         
        poUserSetting.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
        poUserSetting.setCornerRadius(4);                
        poUserSetting.show(btnUser);        
    }    

    @FXML
    private void showNetworkDialogPane(ActionEvent event) {        
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        
        VBox vbx = new VBox();
        vbx.setPadding(new Insets(10));
        vbx.setSpacing(10.0);
        
        CustomTextField tfDB = new CustomTextField();
        tfDB.setPromptText("Database");
        vbx.getChildren().add(tfDB);  
        
        CustomTextField tfDBUser = new CustomTextField();
        tfDBUser.setPromptText("Username");
        vbx.getChildren().add(tfDBUser);
        
        CustomPasswordField tfDBPassword = new CustomPasswordField();
        tfDBPassword.setPromptText("Password");
        vbx.getChildren().add(tfDBPassword);
        
        CustomTextField tfDBHost = new CustomTextField();
        tfDBHost.setPromptText("Host");
        vbx.getChildren().add(tfDBHost);
        
        CustomTextField tfDBPort = new CustomTextField();
        tfDBPort.setPromptText("Port");
        vbx.getChildren().add(tfDBPort);        
        
        dialogLayout.setBody(vbx);
        
        JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER); 
        
        HBox hbxAction = new HBox();
        hbxAction.setPadding(new Insets(10));
        hbxAction.setSpacing(10.0);        

        JFXButton cancel = new JFXButton("Cancel");
        cancel.getStyleClass().add("sm-red-button");
        hbxAction.getChildren().add(cancel);
        cancel.setOnAction((evt) -> {            
            dialog.close();
        });
        
        JFXButton ok = new JFXButton("OK");
        ok.getStyleClass().add("sm-green-button");
        hbxAction.getChildren().add(ok);
        ok.setOnAction((evt) -> {       
            dialog.close();
        });
        
        dialogLayout.setHeading(new Text("Set network Credential"));
        dialogLayout.setActions(hbxAction);                       
        dialog.show();
    }

    @FXML
    private void showChatBox(ActionEvent event) {                        
        Pane chatPane = new VBox();
        chatPane.setPadding(new Insets(10));
                        
        Label lbl = new Label("This is Chat Box");
        chatPane.getChildren().add(lbl);
        
        CustomTextField msg = new CustomTextField();
        msg.setPromptText("message here...");
        chatPane.getChildren().add(msg);
        
        PopOver p = new PopOver(chatPane);
        p.setTitle("Corporate Chat box");
        p.setArrowLocation(PopOver.ArrowLocation.BOTTOM_RIGHT);
        p.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);        
        p.setCloseButtonEnabled(true);        
        if(!p.isShowing()){p.show(btnChats);}        
    }
    
    private void startClient(){        
    }

    private void startServer(){
        try{
            ServerSocket serverSocket = new ServerSocket(3306);
            Socket s = serverSocket.accept();
            DataInputStream input = new DataInputStream(s.getInputStream());
            String str = (String) input.readUTF();            
            System.out.println("Client Says:" +str);            
            serverSocket.close();            
        }catch(Exception e){
            System.out.println(e);
        }
    }    
}
 