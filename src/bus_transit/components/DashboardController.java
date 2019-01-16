/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.components;

import bus_transit.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

/**
 *
 * @author Llamera
 */
public class DashboardController implements Initializable {   
    @FXML private AnchorPane container;
    @FXML private JFXButton btn_menu;
    @FXML private JFXButton btn_close;
    @FXML private JFXButton btn_minimize;
    @FXML private JFXButton btn_user;
    @FXML private Label txt_copyright;
    @FXML private Label txt_datetime;
    @FXML private StackPane stackpane;
    @FXML private AnchorPane AccountPanel;
    @FXML private Label txt_user;
    @FXML private Label txt_level;
    @FXML private JFXButton btn_settings;
    @FXML private JFXButton btn_signout;
    @FXML private JFXDrawer drawer;
    
    private int hour;
    private int minute;
    private int second;
    private int am_pm;
    private int month;
    private int day;
    private int year;

    public static AnchorPane root;
    public static JFXDrawer draw;

    private PopOver pop;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TimeDate();
        root = container;
        draw = drawer;
        pop = new PopOver(AccountPanel);
        
        // This will sets the sidepane
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("SidePane.fxml"));
            drawer.setSidePane(pane);
            drawer.open();
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

    @FXML
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

    @FXML
    private void Minimize(ActionEvent event) {
        // Minimize window if minimize button is clicked
        Stage stage = (Stage) btn_minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void UserSettings(ActionEvent event) {
        // Popups a small window if user button is clicked
        pop.setDetachable(false);
        pop.setAnimated(true);
        pop.setAutoFix(true);
        pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        pop.setCornerRadius(0);
        AccountPanel.setVisible(true);
        pop.show(btn_user);
    }

    @FXML
    private void AccountSettings(ActionEvent event) {
        
    }

    @FXML
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
        dialog.setOverlayClose(false);

        // If YES, the scene will back to login form
        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            try {
                dialog.close();

                Stage stage = (Stage) btn_close.getScene().getWindow();
                stage.close();

                Stage login = new Stage(StageStyle.UNDECORATED);
                Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
                Scene scene = new Scene(root);

                login.setScene(scene);
                login.setMaximized(true);
                login.show();
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
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
}
