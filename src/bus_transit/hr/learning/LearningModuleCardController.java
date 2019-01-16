/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import bus_transit.SceneController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningModuleCardController implements Initializable {
    @FXML
    private FlowPane modulePane;
    @FXML private Label lblTitle;
    @FXML private JFXButton btnEdit;
    private JFXButton btnView;
    @FXML private JFXButton btnAttachFile;
    @FXML private Text txtDescription;
    @FXML private Label lblType;
    @FXML private Label lblLastUpdate;
    @FXML private VBox vbxAttachments;
    
    public static String moduleId; 
    public final String id = moduleId;
    public String description;
    public String lasUpdate;
    public String author;
    public String type;
    public String title;
 

    DBUtilities db = new DBUtilities();
    ResultSet rs;  
    @FXML
    private JFXCheckBox chkToggleModuleActivation;
    private JFXButton btnPrint;
    @FXML
    private Text txtSource;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLearningModuleDetails(id);
    } 
    
    @FXML
    private void loadFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        JFXButton b = (JFXButton) event.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        
        File file = fileChooser.showOpenDialog(s);
            
        if(file != null){            
            attach(file.getAbsoluteFile(), id);
        }                
    }    

    /**
     * to attach file to database with module id
     * @param f
     * @param mID 
     */
    private void attach(File f, String mID){
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
            ps.setString(1, mID);
            ps.setBinaryStream(2, fileData,(int) f.length());
            ps.setString(3, fileName);
            ps.setString(4, ext);
            ps.executeUpdate();
            
            ps.close();
            fileData.close();
            con.close();
            
            //getModules(mID, fileName);
            setAttachment(mID);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        }               
    }    

    /**
     * to get files from database
     * @param id
     * @param fName 
     */
    private void getModules(String modId, String reId, File file){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bustransit_master","root", "071325");            
            String s = "SELECT attachment, filename, extension_name FROM learning_module_files WHERE module_id="+modId+" AND record_id="+reId;
            PreparedStatement ps=con.prepareStatement(s); 
            ResultSet rs=ps.executeQuery();
            
            
            while(rs.next()){
                //File file = new File("C:\\"+rs.getString("filename").toString()+"\\");
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

    private void getLearningModuleDetails(String id){        
        String q = "SELECT * FROM learning_modules WHERE module_id="+id;
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblTitle.setText(rs.getString("title"));
                lblLastUpdate.setText("Last Update: "+rs.getString("last_update"));
                txtDescription.setText(rs.getString("description"));
                txtSource.setText(rs.getString("source"));
                
                if(rs.getString("module_status").toLowerCase().equals("active")){
                    chkToggleModuleActivation.setSelected(true);
                    btnAttachFile.setDisable(false);
                    btnEdit.setDisable(false);
//                    btnView.setDisable(false);
//                    btnPrint.setDisable(false);   
                    vbxAttachments.setDisable(false);
                    vbxAttachments.getChildren().forEach((t) -> {
                        t.setDisable(false);
                    });                    
                }else{
                    chkToggleModuleActivation.setSelected(false);
                    btnAttachFile.setDisable(true);
                    btnEdit.setDisable(true);
//                    btnView.setDisable(true);
//                    btnPrint.setDisable(true);     
                    vbxAttachments.setDisable(true);
                    vbxAttachments.getChildren().forEach((t) -> {
                        t.setDisable(true);
                    });                    
                }
                setAttachment(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAttachment(String id){
        String q = "SELECT * FROM learning_module_files WHERE module_id="+id;
        rs = db.displayRecords(q);   
        vbxAttachments.getChildren().clear();
        try {
            while(rs.next()){
                String fileName = rs.getString("filename");
                String fileExtension = rs.getString("extension_name");                
                String recId = rs.getString("record_id");
                
                Group group = new Group();
                
                HBox hb = new HBox();
                hb.setSpacing(12);
                
                // download file
                FontAwesomeIconView f = new FontAwesomeIconView();
                f.setIcon(FontAwesomeIcon.DOWNLOAD);
                Label l = new Label(fileName, f);
                Tooltip lToolTip = new Tooltip("Download a copy of this attachment.");
                l.setTooltip(lToolTip);                
                l.setContentDisplay(ContentDisplay.RIGHT);
                l.setAlignment(Pos.CENTER_RIGHT);                
                hb.getChildren().add(l);                                                                              
                l.setCursor(Cursor.HAND);
                l.setOnMouseClicked((event) -> {                    
                    Label b = (Label) event.getSource();
                    Stage s = (Stage) b.getScene().getWindow();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialFileName(b.getText());
                    File file = fileChooser.showSaveDialog(s);                                                            
                    System.out.println(l.getText());
                    if(file != null){            
                        getModules(id, recId, file);
                    }                     
                });
                
                // delete attachment file
                FontAwesomeIconView d = new FontAwesomeIconView();
                d.setIcon(FontAwesomeIcon.TRASH);                  
                Label del = new Label(fileName, d);
                Tooltip delToolTip = new Tooltip("Remove attachment file from this Module.");
                del.setTooltip(delToolTip);
                del.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);                
                del.setAlignment(Pos.CENTER);                
                hb.getChildren().add(del);   
                del.setCursor(Cursor.HAND);
                del.setOnMouseClicked((event) -> {                    
                    Label b = (Label) event.getSource();
                    System.out.println(b.getText());
                    
                    removeAttachment(recId);
                    
                });
                
                group.getChildren().add(hb);
                vbxAttachments.getChildren().add(group);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    private void removeAttachment(String recId) {        
        Alert dlg = new Alert(AlertType.CONFIRMATION);                
        dlg.setTitle("You do want dialogs right?");        
        dlg.getDialogPane().setContentText("You are removing attachment file. Continue?");                        
        Optional<ButtonType> option = dlg.showAndWait();
        String r = option.get().getText();
        System.out.println(r);

        if(r.toLowerCase().equals("ok")){
            String q = "INSERT INTO bustransit_archive.learning_module_files (record_id, module_id, attachment,filename, extension_name)" +
                       "SELECT record_id, module_id, attachment,filename, extension_name" +
                       "FROM bustransit_master.learning_module_files" +
                       "WHERE bustransit_master.learning_module_files.record_id="+recId;
            
            db.execute(q);
            
            q = "DELETE FROM bustransit_master.learning_module_files WHERE bustransit_master.learning_module_files.record_id = "+recId;
            db.execute(q);
            
            Alert removed = new Alert(AlertType.INFORMATION);                
            removed.setTitle("Attachment Removed");        
            removed.getDialogPane().setContentText("Attachment successfully removed");             
            
            rebuildModule();
            
        }else{
            // Cancel
        }        
    }      

    /**
     * @return the moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @FXML
    private void toHoverState(MouseEvent event) {
    }

    @FXML
    private void toggleModuleActivation(ActionEvent event){        
        if(chkToggleModuleActivation.isSelected()){           
            // Activate Selected Module
            activateModule(id);                        
        }else{            
            // Deactivate Selected module
            deactivateModule(id);
        }                       
    }
    
    private void activateModule(String sId) { 
        btnAttachFile.setDisable(false);
        btnEdit.setDisable(false);
//        btnView.setDisable(false);
//        btnPrint.setDisable(false);   
        vbxAttachments.setDisable(false);
        vbxAttachments.getChildren().forEach((t) -> {
            t.setDisable(false);
        });                
        String actQry;
        actQry = "UPDATE learning_modules SET module_status='active' WHERE module_id="+sId;
        db.execute(actQry);
    }
    
    private void deactivateModule(String sId) {                
        btnAttachFile.setDisable(true);
        btnEdit.setDisable(true);
//        btnView.setDisable(true);
//        btnPrint.setDisable(true);     
        vbxAttachments.setDisable(true);
        vbxAttachments.getChildren().forEach((t) -> {
            t.setDisable(true);
        });
        String actQry;
        actQry = "UPDATE learning_modules SET module_status='inactive' WHERE module_id="+sId;
        db.execute(actQry);
    }    
    
    @FXML
    private void edit(ActionEvent event) {    
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("New Module"));
        
        VBox vbx = new VBox(); 
        
        FXMLLoader l = new FXMLLoader(getClass().getResource("LearningModules.fxml"));
        StackPane stc = ((LearningModulesController) l.getController()).stackpane;
        
        JFXDialog dlg = new JFXDialog(stc,
                                      dialog,
                                      JFXDialog.DialogTransition.CENTER);        
        
        //dialog.setActions(btn_cancel, btn_yes);              
        dlg.show();         
    }
    
    SceneController scn = new SceneController();
    @FXML
    private void editModule(ActionEvent event) {
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog d = new JFXDialog();        
        layout.setHeading(new Text("Heading"));        
        scn.loadModal(layout, d);
        
        String q = "SELECT * FROM learning_modules WHERE module_id="+id;
        rs = db.displayRecords(q); 
        try {
            if(rs.next()){        
                // Create the custom dialog.
                Dialog dialog = new Dialog();
                dialog.setTitle(rs.getString("title"));

//                // Set the button types.
                ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                ButtonData s = dialog.getDialogPane().getButtonTypes().get(0).getButtonData();
                System.out.println(s);
                
                HBox hbx = new HBox();
                VBox vbx = new VBox();
                VBox labels = new VBox();

                JFXTextField title = new JFXTextField();
                title.setText(rs.getString("title"));
                vbx.getChildren().add(title);
                
                JFXTextField description = new JFXTextField();
                description.setText(rs.getString("description"));
                vbx.getChildren().add(description);
                
                JFXTextField source = new JFXTextField();
                source.setText(rs.getString("source"));
                vbx.getChildren().add(source); 
//                
//                JFXTextField type = new JFXTextField();
//                type.setText(rs.getString("type"));
//                vbx.getChildren().add(type);                 
                
                JFXButton btnOk = new JFXButton("OK");
                //vbx.getChildren().add(btnOk);
                btnOk.setOnAction((evt) -> {
//                    String t = title.getText();
//                    String d = description.getText();
//                    String qr = "UPDATE learning_modules "
//                            + "SET title ='"+title.getText()+"', "
//                            + "description='"+description.getText()+"', "
//                            + "source='"+source.getText()+"', "
//                            + "type='"+type.getText()+"', "
//                            + "last_update=CURDATE() WHERE module_id="+id;
//                    db.execute(qr);
//                    rebuildModule();
//                    dialog.close();                    
                    dialog.setOnCloseRequest(btnOk.getOnAction());
                });
                                                                                                
                JFXButton btnCancel = new JFXButton("Cancel");
                //vbx.getChildren().add(btnCancel);  
                btnCancel.setOnAction((evt) -> {
//                    dialog.close();
                    dialog.setOnCloseRequest(btnCancel.getOnAction());
                });
                
                dialog.getDialogPane().setContent(vbx);
                
                dialog.setOnCloseRequest((ev) -> {
                    if(s.toString().equals("OK_DONE")){
                        // if ok
                        String qr = "UPDATE learning_modules "
                                + "SET title ='"+title.getText()+"', "
                                + "description='"+description.getText()+"', "
                                + "source='"+source.getText()+"', "
//                                + "type='"+type.getText()+"', "
                                + "last_update=CURDATE() WHERE module_id="+id;
                        db.execute(qr);
                        rebuildModule();
                        dialog.close();                                      
                    }else{
                        // cancel
                        dialog.close();                    
                    } 
                });                
                
               
                
                // Request focus on the username field by default.
//                Platform.runLater(() -> from.requestFocus());

//                // Convert the result to a username-password-pair when the login button is clicked.
//                dialog.setResultConverter(dialogButton -> {
//                    if (dialogButton == loginButtonType) {
//                        return new Pair<>(from.getText(), to.getText());
//                    }
//                    return null;
//                });
//
                  dialog.show();
//                Optional<Pair<String, String>> result = dialog.showAndWait();
//                result.ifPresent(pair -> {
//                    System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue());
//                });                   
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }    
         
    public void rebuildModule(){
        String q = "SELECT * FROM learning_modules WHERE module_id="+id;
        rs = db.displayRecords(q); 
        try {
            if(rs.next()){
                lblTitle.setText(rs.getString("title"));
                //lblType.setText(rs.getString("type"));
                txtDescription.setText(rs.getString("description"));
                lblLastUpdate.setText("Last Update: "+rs.getString("last_update"));
                setAttachment(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
