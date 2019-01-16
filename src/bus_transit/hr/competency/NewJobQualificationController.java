/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.competency;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utilities.DBUtilities;
import utilities.Validator;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class NewJobQualificationController extends Application implements Initializable {

    @FXML private VBox vbxJobInfo;
    @FXML private VBox vbxResponsibilities;
    @FXML private VBox vbxQualification;
    @FXML private VBox vbxBenefits;
    @FXML private JFXComboBox<String> cbSalaryGrade;    
    
    @FXML private JFXTabPane tbJobDetails;
    @FXML private Tab tbQualifications;
    @FXML private JFXButton btnAddToQualification;
    @FXML private Tab tbResponsibilities;
    @FXML private JFXButton btnAddToResponsibilities;
    @FXML private Tab tbBenefits;
    @FXML private JFXButton btnAddToBenefits;
    @FXML private Label lblJobTitle;
    @FXML
    private Label lblManpower;
    @FXML
    private Label lblDepartment;
    @FXML
    private Label lblSalaryGrade;
    @FXML
    private Label lblDescription;


    DBUtilities db = new DBUtilities();
    ResultSet rs;
    Validator v = new Validator();
    
    @FXML
    private TextField txtJobTitle;
    @FXML
    private TextField txtVacancies;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextArea txtQualification;
    public static JFXButton btnSave;
    @FXML
    private TextArea txtResponsibilities;
    @FXML
    private TextArea txtBenefits;
    @FXML
    private JFXComboBox<String> cbDepartment;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        vbxBenefits.getChildren().clear();
        vbxResponsibilities.getChildren().clear();
        vbxQualification.getChildren().clear();
        db.populateComboBox("SELECT UPPER(CONCAT(department_name,'(', department_code,')')) FROM department", cbDepartment);
        cbDepartment.getSelectionModel().selectFirst();
        db.populateComboBox("SELECT CONCAT(rec_id, '(P',monthly_rate,')') AS 'sGrade' FROM salary_grade", cbSalaryGrade);
        cbSalaryGrade.getSelectionModel().selectFirst();
                                       
        txtJobTitle.setOnKeyReleased((event) -> {
            // get the text
            TextField node = (TextField) event.getSource();
            String nodeText = node.getText().trim();            
            
            try{
                if(!nodeText.isEmpty()){
                    if(!v.isNumberOnly(nodeText)){
                        String s = "";                                    
                        // Check if there is spaces
                        Matcher m = Pattern.compile("\\s").matcher(nodeText);

                        // set length of charactr you need
                        int l = 3;
                        if(!m.find()){
                           // just get the last 3 character of the word
                           s = nodeText.substring(0, Math.min(nodeText.length(), l)).toUpperCase();
                           System.out.println("no spaces");
                        }else{
                            System.out.println("there is spaces");                
                            // if does not containe spaces
                            Pattern ptrn = Pattern.compile("\\b[a-zA-Z]");
                            Matcher ma = ptrn.matcher(nodeText);                                                
                            while(ma.find()){                    
                                s = s.concat(ma.group());
                            }                                             
                        }
                        //job.positionCode = s.substring(0, Math.min(s.length(), l)).toUpperCase();                        
                        node.setStyle("-fx-border-color:  #DCDCDC");
                    }else{
                        node.setStyle("-fx-border-color: red");
                    }                
                }                
            }catch (NullPointerException e){
                
            }
        });
        
        txtVacancies.setOnKeyReleased((event) -> {
            TextField node = (TextField) event.getSource(); 
            String nodeText = node.getText().trim();
            try{
                if(!(nodeText.isEmpty())){
                    if(v.isNumberOnly(nodeText)){                            
                        //job.manpower = Integer.parseInt(txtVacancies.getText());        
                        node.setStyle("-fx-border-color:  #DCDCDC;");
                        lblManpower.setText("Number of Personnel nedded: ");
                        lblManpower.setStyle("-fx-text-fill: black;");
                    }else{                        
                        lblManpower.setText("Number of Personnel nedded: Input number only*");
                        lblManpower.setStyle("-fx-text-fill: blue;");
                        node.setStyle("-fx-border-color:  red;");
                        node.setText(null);
                    }               
                }                
            }catch(NullPointerException e){                
            }
        });
        
        cbSalaryGrade.setOnAction((event) -> { 
            System.out.println(cbSalaryGrade.getSelectionModel().getSelectedItem());
            //job.salaryGrade = getSalaryLevel(cbSalaryGrade.getSelectionModel().getSelectedItem().toString());            
        });
        
        cbDepartment.setOnAction((event) -> {
            System.out.println(cbDepartment.getSelectionModel().getSelectedItem());
            System.out.println(getDepartmentCode(cbDepartment.getSelectionModel().getSelectedItem()));
//            Matcher m = Pattern.compile("\\(([^)]+)\\)")
//                               .matcher(cbDepartment.getSelectionModel().getSelectedItem().toLowerCase());
//            while(m.find()){
//                System.out.println(m.group(1));
//            }
//            setDepartmentCode(cbDepartment.getSelectionModel().getSelectedItem().toString());            
        });
        
        txtDescription.setOnKeyReleased((event) -> {            
//            if(!txtDescription.getText().trim().isEmpty()){
//                setDescription(txtDescription.getText());
//                System.out.println(txtDescription.getText());                
//            }
        });                
    }    
   
    public void save(){                   
        try {               
            Job job = jobCreator();
            
            String q = "INSERT INTO position "+
                       "(position_code, department_code, description, n_of_manpower, position_name, salary_grade, position_registration_status) "+
                       "VALUES('"+job.positionCode+
                       "','"+job.departmentCode+
                       "','"+job.description+
                       "','"+job.manpower+
                       "','"+job.position_name+
                       "','"+job.salaryGrade+"', 'final')";    
            
            db.execute(q);
                                    
            job.qualities.forEach((vl ) -> {
                String qry = "INSERT INTO position_qualification (qualifications, position_code) "+
                           "VALUES ('"+vl+"', '"+job.positionCode+"')";
                db.execute(qry);
            });

            job.responsibilies.forEach((vl ) -> {
                String qry = "INSERT INTO position_responsibilities (responsibilities, position_code) "+
                           "VALUES ('"+vl+"', '"+job.positionCode+"')";
                db.execute(qry);
            });    
            
            job.benefits.forEach((vl ) -> {
                String qry = "INSERT INTO position_benefits (benefits, position_code) "+
                           "VALUES ('"+vl+"', '"+job.positionCode+"')";
                db.execute(qry);
            });                        
        } catch (NullPointerException ex){
            Logger.getLogger(NewJobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveAsDraft(){                   
        try {               
            Job job = jobCreator();
            
            String q = "INSERT INTO position "+
                       "(position_code, department_code, description, n_of_manpower, position_name, salary_grade, position_registration_status) "+
                       "VALUES('"+job.positionCode+
                       "','"+job.departmentCode+
                       "','"+job.description+
                       "','"+job.manpower+
                       "','"+job.position_name+
                       "','"+job.salaryGrade+"', 'draft')";    
            
            db.execute(q);
                                    
            job.qualities.forEach((vl ) -> {
                String qry = "INSERT INTO position_qualification (qualifications, position_code) "+
                           "VALUES ('"+vl+"', '"+job.positionCode+"')";
                db.execute(qry);
            });

            job.responsibilies.forEach((vl ) -> {
                String qry = "INSERT INTO position_responsibilities (responsibilities, position_code) "+
                           "VALUES ('"+vl+"', '"+job.positionCode+"')";
                db.execute(qry);
            });    
            
            job.benefits.forEach((vl ) -> {
                String qry = "INSERT INTO position_benefits (benefits, position_code) "+
                           "VALUES ('"+vl+"', '"+job.positionCode+"')";
                db.execute(qry);
            });                        
        } catch (NullPointerException ex){
            Logger.getLogger(NewJobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
      @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("NewJobQualification.fxml"));        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();        
    }   
       
    public static void main(String[] args) {        
        launch(args);
    }       
      
    private void swaptToText(Node node){        
        if(node instanceof Label){
            String txt = ((Label) node).getText();
            Pane pane = (Pane) node.getParent();
            int index = pane.getChildren().indexOf(node);
            TextField d = TextFields.createClearableTextField();            
            d.setText(txt);
            d.setOnAction((evt) -> {
                //Pane pane = ((TextField)evt.getSource()).getParent();
                String s = ((TextField) evt.getSource()).getText();

            });
            System.out.println(node.getId());
            pane.getChildren().remove(node);
            pane.getChildren().add(index, d);         
        }
    }
    
//    @FXML
//    private void setClearableText(Event event) {
//        VBox vbx = vbxJobInfo;
//        int i = vbx.getChildren().indexOf(event.getSource());
//        Node node = (Node) event.getSource();
//        if(node instanceof TextField){
//            vbx.getChildren().remove(i);
//            TextField b = TextFields.createClearableTextField();
//            b.setId(node.getId());            
//            vbx.getChildren().add(i, b);
//            b.requestFocus();
//        }
//    }
    
    @FXML
    private void setDepartment(ActionEvent event) {
        //setDepartmentCode((String) cbDepartment.getSelectionModel().getSelectedItem());        
    }

    @FXML
    private void setSalaryCategory(ActionEvent event) {
    }

        
    /**
     * Function that create list item
     * @param txt
     * @param fromTextArea
     * @param toTextFlow
     * @return JFXButton with text and icon
     */
    private JFXButton createListItem(TextArea fromTextArea, VBox toTextFlow){                
        JFXButton b = null;
        try{
            if(!fromTextArea.getText().trim().isEmpty()){
                String txt = fromTextArea.getText();
                // List item icon style
                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CHECKBOX_BLANK_CIRCLE);
                icon.setSize("10");
                icon.setFill(Color.GREY);

                // Button Style
                b = new JFXButton(txt,icon);
                b.setCursor(Cursor.HAND);
                b.setStyle("-fx-background-color: white");
                b.setButtonType(JFXButton.ButtonType.RAISED);
                double w = toTextFlow.getWidth() - 20;
                b.setMinWidth(w);
                b.setMaxWidth(w);
                b.setPrefWidth(w);
                b.setWrapText(true);
                b.setAlignment(Pos.CENTER_LEFT);

                b.setOnMouseClicked((bEvt) -> {                
                    if(bEvt.getClickCount() == 2){
                        toTextFlow.getChildren().remove(bEvt.getSource());
                        fromTextArea.setText(null);
                    }                
                    fromTextArea.setText(((JFXButton) bEvt.getSource()).getText());
                });
                toTextFlow.getChildren().add(b);
                fromTextArea.setText(null);                          
            }             
        } catch(NullPointerException e){
            System.out.println(e);
        }
        return b;
    }
    
    @FXML
    private void addToList(ActionEvent event) {                                
        String listOf = ((JFXButton) event.getSource()).getAccessibleText().toLowerCase();
        TextArea from = null;
        VBox to = null;
                        
        if(listOf.equals("qualifications")){
            System.out.println(listOf);
            from = txtQualification;
            to = vbxQualification;
        }
        
        if(listOf.equals("responsibilities")){
            System.out.println(listOf);
            from = txtResponsibilities;
            to = vbxResponsibilities;
        }
                
        if(listOf.equals("benefits")){
            System.out.println(listOf);
            from = txtBenefits;
            to = vbxBenefits;
        }
        createListItem(from, to);
    }      

    private String getDepartmentCode(String s){        
        String q = "SELECT department_code as 'deptCode' FROM department WHERE CONCAT(department_name,'(', department_code,')')='"+s.toLowerCase()+"'";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                return rs.getString("deptCode");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewJobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return "";
    }
    
    private int getSalaryLevel(String s){                
        System.out.println(s);
        String q = "SELECT salary_grade AS 'sGrade' FROM salary_grade WHERE CONCAT(rec_id, '(P',basic_pay,')') = '"+s+"';";
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                return rs.getInt("sGrade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewJobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    private HBox createRowContent(String s){
        HBox hbx = new HBox();
                                        
        return hbx;
    }
    

    @FXML
    private void keyPressed(KeyEvent event) {
        
    }
    
    private String getPositionCode(TextField nd){
         // get the text
            TextField node = nd;
            String nodeText = node.getText().trim();                        
            try{
                if(!nodeText.isEmpty()){
                    if(!v.isNumberOnly(nodeText)){
                        String s = "";                                    
                        // Check if there is spaces
                        Matcher m = Pattern.compile("\\s").matcher(nodeText);

                        // set length of charactr you need
                        int l = 3;
                        if(!m.find()){
                           // just get the last 3 character of the word
                           s = nodeText.substring(0, Math.min(nodeText.length(), l)).toUpperCase();
                           System.out.println("no spaces");
                        }else{
                            System.out.println("there is spaces");                
                            // if does not containe spaces
                            Pattern ptrn = Pattern.compile("\\b[a-zA-Z]");
                            Matcher ma = ptrn.matcher(nodeText);                                                
                            while(ma.find()){                    
                                s = s.concat(ma.group());
                            }                                             
                        }
                        //job.positionCode = s.substring(0, Math.min(s.length(), l)).toUpperCase();                        
                        node.setStyle("-fx-border-color:  #DCDCDC");
                        return s.substring(0, Math.min(s.length(), l)).toUpperCase();                                                
                    }else{
                        node.setStyle("-fx-border-color: red");
                    }                
                }                
            }catch (NullPointerException e){
                
            }

        return "";
    }
    
    private Job jobCreator(){
        Job job = new Job();        
        job.position_name = !txtJobTitle.getText().trim().isEmpty() ? txtJobTitle.getText().trim() : "" ;
        job.description = !txtDescription.getText().trim().isEmpty() ? txtDescription.getText().trim() : "" ;
        job.manpower = !txtVacancies.getText().trim().isEmpty() ? Integer.parseInt(txtVacancies.getText().trim()) : 0 ;        
        job.salaryGrade = getSalaryLevel(cbSalaryGrade.getSelectionModel().getSelectedItem());
        job.positionCode = getPositionCode(txtJobTitle);                
        job.departmentCode = getDepartmentCode(cbDepartment.getSelectionModel().getSelectedItem());
                        
        vbxBenefits.getChildren().forEach((btn) -> {
            if(btn instanceof JFXButton){                
                job.benefits.add(((JFXButton) btn).getText());
            }
        });
        
        vbxQualification.getChildren().forEach((btn) -> {
            if(btn instanceof JFXButton){                
                job.qualities.add(((JFXButton) btn).getText());
            }
        }); 
        
        vbxResponsibilities.getChildren().forEach((btn) -> {
            if(btn instanceof JFXButton){                
                job.responsibilies.add(((JFXButton) btn).getText());
            }
        });        
        
        return job;
    }

    private void addToBenifitsList(ActionEvent event) {
        if(!txtBenefits.getText().isEmpty()){
            
        }
    }
    
    private class Job{
        String departmentCode = "";
        String positionCode = "";
        String description = "";
        String position_name = "";        
        int salaryGrade = 0;
        int manpower = 0;    
        
        ObservableList<String> benefits = FXCollections.observableArrayList();
        ObservableList<String> qualities = FXCollections.observableArrayList();
        ObservableList<String> responsibilies = FXCollections.observableArrayList();        
    }
    
}