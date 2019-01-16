/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import bus_transit.DashboardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningManagementController extends Application implements Initializable {

    @FXML
    private JFXButton btnBack;
    @FXML
    private CustomTextField tfTitle;
    @FXML
    private CustomTextField taDuration;
    @FXML
    private TextArea taDescription;
    @FXML
    private CustomTextField tfNofParts;
    @FXML
    private ToggleButton btnOkParts;
    @FXML
    private VBox vbxTestParts;
    @FXML
    private JFXComboBox<?> cmbParts;

    /**
     * @return the stackpane
     */
    public StackPane getStackpane() {
        return stackpane;
    }

    /**
     * @param stackpane the stackpane to set
     */
    public void setStackpane(StackPane stackpane) {
        this.stackpane = stackpane;
    }
    DBUtilities db = new DBUtilities();
    ResultSet rs;  
    
    @FXML private AnchorPane container;
    @FXML private AnchorPane heading;
    @FXML private JFXTabPane content;
    public static Label title;
    private FlowPane flpTestContainer;
    public StackPane stackpane;
    private CustomTextField txt_searchAll;
    private FlowPane flpModules;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //stackpane = stpane;
        populateTestContainer();
        populateLearningModule();
        loadTestResult();
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
    } 

    /**
     * to run module directly
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("LearningManagement.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }    
        
    private void populateTestContainer(){
        String q = "SELECT * FROM test";
        rs = db.displayRecords(q);               
        ArrayList<FXMLLoader> fxmlLoaders = new ArrayList();
        ArrayList<FlowPane> arrFp = new ArrayList();
        ArrayList<TestController> testControllers = new ArrayList();
                        
        try {
            while(rs.next()){
                String testId = rs.getString("test_id");
                String testName = rs.getString("test_name").toUpperCase();
                String description = rs.getString("description").toUpperCase();
                String lastUpdate = rs.getString("last_update").toUpperCase();    
                
                FXMLLoader l = new FXMLLoader(getClass().getResource("Test.fxml"));                                 
                TestController testController = new TestController();
                testController.setTestId(testId);                
                FlowPane fp = l.load();
                l.setController(testController);
                
                fxmlLoaders.add(l);
                arrFp.add(fp);
                
                testControllers.add(testController);
                
                flpTestContainer.getChildren().add(fp);
                System.out.println("From LMS: "+testId);                                                                                                                                                                                                                                            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }      
    
    // to load FileChooser
    private void loadFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        JFXButton b = (JFXButton) event.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        
        File file = fileChooser.showOpenDialog(s);
            
        if(file != null){            
            attach(file.getAbsoluteFile());
        }                
    }    
    
    // to save selected file to database
    private void attach(File f){
        System.out.println(f.getAbsolutePath());
        System.out.println(f);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            FileInputStream fileData = new FileInputStream(f.getAbsolutePath());            
            String fileName = f.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
            System.out.println("Filename: "+fileName);    
            System.out.println("File extenstion: "+ext);                
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bustransit_master","root", "071325");            
            String s = "INSERT INTO learning_module_files (module_id,attachment, filename, extension_name) VALUES (?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(s);
            ps.setString(1, "1");
            ps.setBinaryStream(2, fileData,(int) f.length());
            ps.setString(3, fileName);
            ps.setString(4, ext);
            ps.executeUpdate();
            
            ps.close();
            fileData.close();
            con.close();
            
            getModules("1", fileName);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }                
    }
    
    // to get Blob data from database
    private void getModules(String id, String fName){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bustransit_master","root", "071325");            
            String s = "SELECT attachment,filename FROM learning_module_files WHERE module_id="+id;
            PreparedStatement ps=con.prepareStatement(s); 
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                File file = new File("C:\\"+rs.getString("filename").toString()+"\\");
                file.setWritable(true);
                file.setExecutable(true);
                file.setReadable(true);
                FileOutputStream fos = new FileOutputStream(file);            
                byte b[];
                Blob blob;              
                blob=rs.getBlob("attachment");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);
                fos.close();
            }            
            ps.close();            
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
   
    public int duration = 0;
    public String testTitle;
    public String type;
    public String description;
    
    private void newTest(ActionEvent event) throws IOException {            
        getStackpane().toFront();

        VBox vbx = new VBox();
        vbx.setSpacing(10);
        int row=0;
        
        Font font = new Font("SansSerif", 12);
        
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("New Test")); 
                                
        JFXTextField tfTestTitle = new JFXTextField();
        tfTestTitle.setFont(font);
        tfTestTitle.setPromptText("Title".toUpperCase());        
        vbx.getChildren().add(row, tfTestTitle);
        
        JFXTextField tfTestType = new JFXTextField();
        tfTestType.setPromptText("type".toUpperCase());
        row++;        
        vbx.getChildren().add(row, tfTestType);  
        
        JFXTextArea tfTestDescription = new JFXTextArea();
        tfTestDescription.setPrefHeight(120);
        tfTestDescription.setPromptText("description".toUpperCase());        
        row++;
        vbx.getChildren().add(row, tfTestDescription);         
        
        JFXTextField tfTestDuration = new JFXTextField();
        tfTestDuration.setPromptText("duration".toUpperCase());
        row++;
        vbx.getChildren().add(row, tfTestDuration);         
        
        layout.setBody(vbx);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(getStackpane(), layout, JFXDialog.DialogTransition.LEFT);
        dialog.setOverlayClose(false);

        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            testTitle = tfTestTitle.getText();
            description = tfTestDescription.getText();
            type = tfTestType.getText();
            duration = Integer.parseInt(tfTestDuration.getText());            
            addNewTest();            
            dialog.close();            
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dialog.close();
            getStackpane().toBack();
        });

        layout.setActions(btn_cancel, btn_yes);
        dialog.show();       
    }    

    private void addNewTest() {
        String q = "INSERT INTO test (id,test_id, test_name, "
                + "test_type, description, last_update,duration) "
                + "VALUES (null,'"+getRandom()+
                "','"+testTitle+
                "','"+type+
                "','"+description+
                "',CURDATE(),'"+duration+"')";
        db.execute(q);
        flpTestContainer.getChildren().clear();
        populateTestContainer();
        System.out.println("New Test Added");
    }
    
    private String getRandom(){
        char[] chrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_$#@".toCharArray();
        Random n = new Random();
        StringBuilder sb = new StringBuilder((100000 + n.nextInt(900000)));
        for(int i=0; i < 10; i++){
            sb.append(chrs[n.nextInt(chrs.length)]);
        }
        return sb.toString();
    }
    
    
    public void populateLearningModule(){
        String q = "SELECT * FROM learning_modules";
        rs = db.displayRecords(q);        
        flpModules.getChildren().clear();
        try {
            while(rs.next()){                                                                
                String mId = rs.getString("module_id");                 
                FXMLLoader l = new FXMLLoader(getClass().getResource("LearningModuleCard.fxml"));
                LearningModuleCardController md = new LearningModuleCardController();
                md.setModuleId(mId);                             
                FlowPane fp = l.load();
                l.setController(md);   
                flpModules.getChildren().add(fp);
            }                                    
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void newLearningModule(ActionEvent event) {    
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("New Module"));
        
        VBox vbx = new VBox();        
        
        JFXTextField title = new JFXTextField();
        title.setPromptText("Title");
        vbx.getChildren().add(0, title);
        
        JFXTextField description = new JFXTextField();
        description.setPromptText("Description");        
        vbx.getChildren().add(1, description);
                
        JFXTextField author = new JFXTextField();
        author.setPromptText("Author");        
        vbx.getChildren().add(2, author);        
        
        JFXTextField type = new JFXTextField();
        type.setPromptText("Type");        
        vbx.getChildren().add(3, type);         
        
        dialog.setBody(vbx);
        
        JFXDialog dlg = new JFXDialog(getStackpane(),
                                      dialog,
                                      JFXDialog.DialogTransition.CENTER);
        
        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            try{
                dlg.close();
                String q = "INSERT INTO learning_modules (module_id, title, description, author, type, last_update)"
                        + "VALUES(null, '"+title.getText()+"','"+description.getText()+"','"+author.getText()+"','"+type.getText()+"',CURDATE())";
                db.execute(q);
                populateLearningModule();
                getStackpane().toBack();                   
            }catch(NullPointerException e){
                
            }
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dlg.close();
            getStackpane().toBack();
        });

        dialog.setActions(btn_cancel, btn_yes);              
        dlg.show();            
    }


    private void searchModule(KeyEvent event) {
        String s = txt_searchAll.getText();
        String q = "SELECT * FROM learning_modules WHERE title LIKE '%"+s+"%'";
        rs = db.displayRecords(q);        
        flpModules.getChildren().clear();
        try {
            while(rs.next()){                                                                
                String mId = rs.getString("module_id");                 
                FXMLLoader l = new FXMLLoader(getClass().getResource("LearningModuleCard.fxml"));
                LearningModuleCardController md = new LearningModuleCardController();
                md.setModuleId(mId);                             
                FlowPane fp = l.load();
                l.setController(md);   
                flpModules.getChildren().add(fp);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void loadTestResult(){
//        String qry = "";
//        db.createPieChart("Test Result: General preview", qry, pieResults);
    }    

    @FXML
    private void populateVbxParts(ActionEvent event) {
    }
}