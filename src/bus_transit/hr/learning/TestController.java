/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import bus_transit.DashboardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestController implements Initializable {
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;
    
    @FXML private JFXButton btnEdit;
    @FXML private FlowPane flpTest;
    @FXML private Label lblTitle;
    @FXML private Text txtDescription;
    @FXML private Label lblDate;    

    private String title;
    private static String description;
    private static String lastUpdate;
    public static String testId;
    public final String id = testId;
    @FXML private JFXButton btnRunTest;
    @FXML private Label lblDuration;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXButton btnShowOptions;
    @FXML
    private JFXButton btnDownload;
    @FXML
    private Label lblItems;
    @FXML
    private VBox vbxContainer;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        loadTest();        
    }    

    public void loadTest(){  
        // lets get some output
        System.out.println(id);
        System.out.println(this.testId);
        System.out.println(getTestId());        
        System.out.println("From loadTest:"+id);     
        
        String q = "SELECT * FROM test WHERE test_id='"+id+"'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblTitle.setText(rs.getString("test_name"));
                lblDate.setText("Last Update: "+rs.getString("last_update"));
                txtDescription.setText(rs.getString("description"));
                lblDuration.setText("Duration: "+rs.getString("duration"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the testId
     */
    public String getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public void setTestId(String testId) {
        this.testId = testId;
    }


    /**
     * @return the lblTitle
     */
    public Label getLblTitle() {
        return lblTitle;
    }

    /**
     * @param lblTitle the lblTitle to set
     */
    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    /**
     * @return the txtDescription
     */
    public Text getTxtDescription() {
        return txtDescription;
    }

    /**
     * @param txtDescription the txtDescription to set
     */
    public void setTxtDescription(Text txtDescription) {
        this.txtDescription = txtDescription;
    }

    /**
     * @return the lblDate
     */
    public Label getLblDate() {
        return lblDate;
    }

    /**
     * @param lblDate the lblDate to set
     */
    public void setLblDate(Label lblDate) {
        this.lblDate = lblDate;
    }    
    
    @FXML
    private void runTest(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();       
        Parent root;        
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource(file));
            TestViewerController testViewerController = new TestViewerController();
            
            testViewerController.setTestId(id);
            
//            System.out.println("From TestController");
//            System.out.println(id);
//            System.out.println(this.testId);
//            System.out.println(getTestId());            
            
            root = l.load();
            
            l.setController(testViewerController);
            
            Stage stage = new Stage();            
            stage.setTitle("Test");
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.show();            
        }catch(IOException e){
            e.printStackTrace();
        }        
    } // runTest ends here    

    @FXML
    private void printResult(ActionEvent event) {
    }
    
    /**
     * OPions
     */    
    private PopOver pop = new PopOver();
    private VBox vbx = new VBox();    
    @FXML
    private void showOptions(ActionEvent event) {
        if(pop.isFocused()){
            
        }else{            
            vbx.getChildren().clear();
            vbx.setFillWidth(true);
            vbx.setSpacing(10);
            double p = 8;
            vbx.setPadding(new Insets(p,p,p,p));
            
            double textSize = 14;
            JFXButton btnEdit = new JFXButton("Edit");                                                
            btnEdit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT));
            btnEdit.setFont(new Font(textSize));                                    
            vbx.getChildren().add(btnEdit);
            btnEdit.setOnAction((avt) -> {
                showEditTest();
            });

            JFXButton btnRun = new JFXButton("Preview");                                                
            btnRun.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLAY)); 
            btnRun.setFont(new Font(textSize));
            btnRun.setAccessibleText("TestViewer.fxml");
            btnRun.setOnAction((evt) -> {
                try {
                    runTest(evt);
                } catch (IOException ex) {
                    Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            vbx.getChildren().add(btnRun);            
            
            JFXButton btnDownload = new JFXButton("Download");                                           
            btnDownload.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD)); 
            btnDownload.setFont(new Font(textSize));
            //btnDownload.setTooltip(new Tooltip("Run Test"));                        
            vbx.getChildren().add(btnDownload);             
            
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
    }  

    // Not yet done
    @FXML
    private void edit(MouseEvent event) {
        if(event.getClickCount() == 2){
            Node n = ((Node) event.getSource());
            n.getId();
            System.out.println(n.getId());
            
        }
    }

    private void showEditTest(){
        try {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            
            dialogLayout.setHeading(new Text("Edit Test"));
            
            Pane pnEditTestBody = FXMLLoader.load(getClass().getResource("EditTest.fxml"));
            
            dialogLayout.setBody(pnEditTestBody);
            
            JFXDialog dialog = new JFXDialog(DashboardController.stckPne, dialogLayout, JFXDialog.DialogTransition.CENTER);
            
            HBox hbxAction = new HBox();
            JFXButton ok = new JFXButton("Ok");
            ok.getStyleClass().add("xsm-green-button");
            hbxAction.getChildren().add(ok);
            ok.setOnAction((evt) -> {                
                dialog.close();
            });
            
            JFXButton cancel = new JFXButton("Cancel");
            cancel.getStyleClass().add("xsm-red-button");
            hbxAction.getChildren().add(cancel);
            cancel.setOnAction((evt) -> {
                dialog.close();
            });
            
            dialogLayout.setActions(hbxAction);                        
            dialog.show();
            
        } catch (IOException ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
