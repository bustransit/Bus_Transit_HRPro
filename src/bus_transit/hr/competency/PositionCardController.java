/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.competency;

import bus_transit.DashboardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class PositionCardController implements Initializable {
    ResultSet rs;
    DBUtilities db = new DBUtilities();
    @FXML
    private VBox vbxJobDetails;
    @FXML
    private Hyperlink hplPosition;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Hyperlink hplRate;
    @FXML
    private Tab tbQualities;
    @FXML
    private JFXNodesList jfxNdListQualities;
    @FXML
    private Tab tbResponsibilities;
    @FXML
    private JFXNodesList jfxNdListResponsibilities;
    @FXML
    private Tab tbBenefits;
    @FXML
    private JFXNodesList jfxNdListBenefits;
    @FXML
    private Label lblPositionCode;
    /**
     * @return the positionId
     */
    public String getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String positionId;
    @FXML
    private AnchorPane ancPosition;
    private Label lblPosition;
    @FXML
    private JFXButton btnShowOptions;
    @FXML
    private VBox vbxPosition;
    @FXML
    private Text txtDescription;
    
    public static String positionCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // to set tab fit width header
        this.positionCode = positionId;
        tabPane.tabMinWidthProperty().bind(tabPane.widthProperty().divide(tabPane.getTabs().size()).subtract(10));
        loadJobDetails();         
    }    
    
    /**
     * Job Position Details
     */    
    private void loadJobDetails(){
        String q = "SELECT CONCAT('P',FORMAT(monthly_rate,2)) as 'monthly_rate', " +
                   "position.position_code, " +
                   "position.department_code," +
                   "position.description, " +
                   "position.position_name, " +
                   "position.salary_grade " +
                   "FROM POSITION, salary_grade " +
                   "WHERE "+
                   "position.position_code ='"+positionId+"'";

        rs = db.displayRecords(q);        
        try {          
            if(rs.next()){
                hplPosition.setText(rs.getString("position_name").toUpperCase());
                hplRate.setText(rs.getString("monthly_rate"));
                txtDescription.setText(rs.getString("description"));
                lblPositionCode.setText(rs.getString("position_code"));                
                
                positionId = rs.getString("position_code");
                loadQualities(positionId, jfxNdListQualities);
                loadResponsibilities(positionId, jfxNdListResponsibilities);
                loadBenefits(positionId, jfxNdListBenefits);
                
            }} catch (SQLException ex) {
            Logger.getLogger(PositionCardController.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    private void loadQualities(String psCode, JFXNodesList lst){
        lst.getChildren().clear();
        String q = "SELECT qualifications FROM position_qualification WHERE position_code = '"+psCode+"'";
        rs = db.displayRecords(q);
        try {
            while(rs.next()){
                HBox hbx = new HBox();
                Text txt = new Text(rs.getString("qualifications"));
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
                hbx.getChildren().add(icon);
                hbx.getChildren().add(txt);
                lst.getChildren().add(hbx);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadResponsibilities(String psCode, JFXNodesList lst){
        lst.getChildren().clear();
        String q = "SELECT responsibilities FROM position_responsibilities WHERE position_code = '"+psCode+"'";
        rs = db.displayRecords(q);
        try {
            while(rs.next()){
                HBox hbx = new HBox();
                Text txt = new Text(rs.getString("responsibilities"));
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
                hbx.getChildren().add(icon);
                hbx.getChildren().add(txt);
                lst.getChildren().add(hbx);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void loadBenefits(String psCode, JFXNodesList lst){
        lst.getChildren().clear();
        String q = "SELECT benefits FROM position_benefits WHERE position_code = '"+psCode+"'";
        rs = db.displayRecords(q);
        try {
            while(rs.next()){
                HBox hbx = new HBox();
                Text txt = new Text(rs.getString("benefits"));
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
                hbx.getChildren().add(icon);
                hbx.getChildren().add(txt);
                lst.getChildren().add(hbx);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    /**
     * OPions
     */    
  
    @FXML
    private void showOptions(ActionEvent event) {
        PopOver pop = new PopOver();
        VBox vbx = new VBox();  
        vbx.getChildren().clear();
        vbx.setFillWidth(true);
        vbx.setSpacing(10);
        double p = 8;
        vbx.setPadding(new Insets(p,p,p,p));

        double textSize = 14;

        JFXButton btnEdit = new JFXButton("Edit");                                                
        btnEdit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT));
        btnEdit.setAccessibleText("EditJobQualification.fxml");
        EditJobQualificationController.positionCode = positionId;
        btnEdit.setFont(new Font(textSize));                       
        vbx.getChildren().add(btnEdit);
        btnEdit.setOnAction((evt) -> {
            loadEditPositionModal();
            pop.hide();
        });

        JFXButton btnViewDetails = new JFXButton("Details");                                           
        btnViewDetails.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD)); 
        btnViewDetails.setFont(new Font(textSize));
        vbx.getChildren().add(btnViewDetails);        
        btnViewDetails.setOnAction((evt) -> {
            pop.hide();
        });

        pop.setContentNode(vbx);
        pop.setAnimated(true);        
        pop.setId("OptionPopOver"); 

        pop.setDetachable(false);
        pop.setAnimated(true);
        pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        pop.setCornerRadius(4);        
        pop.show(btnShowOptions);
        String popOverId = pop.getId();
        System.out.println(popOverId);      
    }  
    
    private void loadEditPositionModal(){
        try {                        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditJobQualification.fxml"));
            Node pane = loader.load();            
            EditJobQualificationController.positionCode = positionId;
                        
            JFXDialogLayout dialog = new JFXDialogLayout();
            dialog.setHeading(new Text("Edit Job Qualification"));                                    
            dialog.setBody(pane);      
            JFXDialog dlg = new JFXDialog(DashboardController.stckPne,dialog,JFXDialog.DialogTransition.CENTER);                 
            
            
            JFXButton save = new JFXButton("Save");
            save.getStyleClass().add("xsm-green-button");
            save.setOnAction((sEvt) -> {
                //((EditJobQualificationController) loader.getController()).save();
                dlg.close();
            });
                                 
            JFXButton cancel = new JFXButton("Cancel");
            cancel.getStyleClass().add("xsm-red-button");
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
                            
            HBox hbxAction = new HBox(save, cancel);
            hbxAction.setSpacing(10);            
            dialog.setActions(hbxAction);
            dlg.show();
        } catch (IOException ex) {
            Logger.getLogger(JobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }


    @FXML
    private void goToSalaryGradeInfo(ActionEvent event) {
    }

    @FXML
    private void goToPositionFullInfo(MouseEvent event) {
        
    }
    
}
