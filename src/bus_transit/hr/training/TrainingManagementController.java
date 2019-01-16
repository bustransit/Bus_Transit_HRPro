
package bus_transit.hr.training;

import bus_transit.LoginController;
import bus_transit.SceneController;
import bus_transit.SidePaneController;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import bus_transit.components.controls.Buttons;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.BasicConfigurator;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrainingManagementController extends Application
       implements Initializable {
    
    private DBUtilities db;
    private ResultSet rs;
    private String loggedInUser;
    SceneController scn = new SceneController();
    @FXML
    private AnchorPane container;
    @FXML private AnchorPane heading;
    @FXML private CustomTextField tfSearch;
    @FXML private TableView<String> tblTrainingList;
    private JFXTextField tfTrainingTitle;
    private JFXTextField tfTrainingLocation;
    private JFXDatePicker dpTrainingDate;
    private JFXComboBox<String> cbTrainingType;
    private JFXTextField tfNumberOfParticipants;
    private JFXButton btnNew;
    private JFXTimePicker tpTrainingTime;
    private Label lblTraineesForTraining;
    private JFXTextField tfSearchTrainees;
    private VBox vbxTrainees;
    
    public String selectedTrainingId = null;
    private VBox vbxAddTrainees;
    private JFXButton btnUpdate;
    private StackPane stackPane;
    @FXML
    private JFXButton btnNewTraining;
    @FXML
    private JFXComboBox<String> cmbFilterBy;
    private JFXTimePicker tpTrainingStart;
    @FXML
    private StackPane stackpane;
    private GridPane grdTraining;
    @FXML
    private JFXCheckBox chbtoggleInActiveTraining;
    
    public final String trainingFields = "SELECT training_id as 'ID',"
            + "title as 'TITLE',"
            + "n_of_participants as 'PARTICIPANTS',"
            + "description as 'DESCRIPTION', "
            + "venue as 'VENUE', "
            + "status as 'STATUS', "
            + "note as 'NOTE', "
            + "TYPE FROM training ";
    @FXML
    private JFXComboBox<String> cmbType;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private MenuItem mnuSetTrainees;
    @FXML
    private MenuItem mnuDetails;
    @FXML
    private MenuItem mnuSchedule;
    @FXML
    private MenuItem mnuTrainors;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Using 1 DButilities instance
        this.db = LoginController.db;
        this.loggedInUser = SidePaneController.employeeFullName;        
        System.out.println(loggedInUser);        
        refreshTable();        
        tblTrainingList.getColumns().get(2).setStyle("-fx-alignment: CENTER;");
        initializedComboBox();        
        backgroundRefresh();       
    }    
    
    public void backgroundRefresh() {
        int refreshRate = 1; // 1 second
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {                                                
            
        }),new KeyFrame(Duration.seconds(refreshRate)));                       
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }    
 
    
    private void initializedComboBox(){
        cmbFilterBy.getItems().add("Title");
        cmbFilterBy.getItems().add("Venue");
        cmbFilterBy.getItems().add("Description");
        cmbFilterBy.getSelectionModel().selectFirst();
        
        cmbType.getItems().add("All");
        cmbType.getItems().add("Inhouse");
        cmbType.getItems().add("Outdoor");
        cmbType.getSelectionModel().selectFirst();
    }
    
    private Integer getTotalEmployee(){
        String q = "SELECT COUNT(emp_id) as 'total' FROM employee";
        rs = db.displayRecords(q);
        int t = 0;
        try {
            if(rs.next()){
                t = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return t;
    }
    
    private void clearTrainingFields(){
        tfTrainingTitle.setText("");
        tfTrainingLocation.setText("");
        tfNumberOfParticipants.setText("");
        tpTrainingTime.setValue(null);
        dpTrainingDate.setValue(null);
        cbTrainingType.getSelectionModel().select(0);        
        btnUpdate.setDisable(true);         
        btnNew.setDisable(false);
    }
    
    
    EventHandler<MouseEvent> newTraining = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String title = tfTrainingTitle.getText();
                int participants = Integer.parseInt(tfNumberOfParticipants.getText());
                LocalTime time = tpTrainingTime.getValue();
                LocalDate date = dpTrainingDate.getValue();
                String venue = tfTrainingLocation.getText();
                String type = (String) cbTrainingType.getSelectionModel()
                                                     .getSelectedItem().toString();

                System.out.println(title);
                System.out.println(participants);
                System.out.println(time);
                System.out.println(date);
                System.out.println(venue);
                System.out.println(type);

                String q = "INSERT INTO training "+
                           "(training_id, "+
                           "title, participants, TIME, target_date, venue, TYPE) " +
                           "VALUES(NULL, '"+title+
                           "', '"+participants+
                           "', '"+time+
                           "', '"+date+
                           "','"+venue+
                           "','"+type+"');";

                db.execute(q);

                refreshTable();
                clearTrainingFields();            
            }
    };
    
    EventHandler<MouseEvent> updateTraining = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String title = tfTrainingTitle.getText();
                int participants = Integer.parseInt(tfNumberOfParticipants.getText());
                LocalTime time = tpTrainingTime.getValue();
                LocalDate date = dpTrainingDate.getValue();
                String venue = tfTrainingLocation.getText();
                String type = (String) cbTrainingType.getSelectionModel()
                                                     .getSelectedItem().toString();

                System.out.println(title);
                System.out.println(participants);
                System.out.println(time);
                System.out.println(date);
                System.out.println(venue);
                System.out.println(type);

                String q = "UPDATE training "
                        + "SET title = '"+title+"', "
                        + "participants='"+participants+"', "
                        + "start_time='"+time+"', "
                        + "target_date='"+date+"', "
                        + "venue='"+venue+"', "
                        + "TYPE='"+type+"' "
                        + "WHERE training_id="+selectedTrainingId;

                db.execute(q);

                refreshTable();
                clearTrainingFields();            
            }
    };    
    
    
    private void updateTraining(){
                String title = tfTrainingTitle.getText();
                int participants = Integer.parseInt(tfNumberOfParticipants.getText());
                LocalTime time = tpTrainingTime.getValue();
                LocalDate date = dpTrainingDate.getValue();
                String venue = tfTrainingLocation.getText();
                String type = (String) cbTrainingType.getSelectionModel()
                                                     .getSelectedItem().toString();

                System.out.println(title);
                System.out.println(participants);
                System.out.println(time);
                System.out.println(date);
                System.out.println(venue);
                System.out.println(type);

                String q = "UPDATE training "
                        + "SET title = '"+title+"', "
                        + "participants='"+participants+"', "
                        + "start_time='"+time+"', "
                        + "target_date='"+date+"', "
                        + "venue='"+venue+"', "
                        + "TYPE='"+type+"' "
                        + "WHERE training_id="+selectedTrainingId;

                db.execute(q);                                
                refreshTable();
                clearTrainingFields();
                btnNew.setDisable(false);
                btnUpdate.setDisable(true);
    }
    
    public static void main(String[] args) {                
        launch(args);        
    }     
    
    @Override
    public void start(Stage stage) {
        try {
            BasicConfigurator.configure();
            Parent root = FXMLLoader.load(getClass()
                    .getResource("TrainingManagement.fxml"));
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showActiveTraining(){
        db.populateTable("SELECT training_id as 'ID', "
                + "title as 'TITLE', "
                + "participants as 'PARTICIPANTS', "
                + "start_time as 'TIME', "
                + "target_date as 'DATE', "
                + "venue as 'VENUE', "
                + "TYPE FROM training WHERE status='active'", tblTrainingList);        
    }
    
    private void showInActiveTraining(){
        db.populateTable(trainingFields + " WHERE status='inactive'", tblTrainingList);        
    }
    
    private void refreshTable(){
        db.populateTable(trainingFields + " WHERE status='active'", tblTrainingList);
        tblTrainingList.getColumns().get(0).setResizable(false);
        tblTrainingList.getColumns().get(0).setPrefWidth(1);        
    }

//    private void addDeleteButtonToTable(TableView tbv){
//        TableColumn<?, ?> delBtn = new TableColumn("Delete");
//        Callback<TableColumn<?,Void>,TableCell<?,Void>> cellFactory = new Callback<TableColumn<,Void>, TableCell<d,Void>>(){
//            @Override
//            public TableCell<?, Void> call(TableColumn<?, Void> param) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//            
//        }
//        
//    }
        

    @FXML    
    private void searchTraining(KeyEvent event) {
        String s = tfSearch.getText().trim();                        
        if(!s.isEmpty()){
            if(!chbtoggleInActiveTraining.isSelected()){
                db.populateTable(trainingFields+ " WHERE title LIKE '%"+s+"%' AND status<>'active'", tblTrainingList);
            }

            if(chbtoggleInActiveTraining.isSelected()){
                db.populateTable(trainingFields+ " WHERE title LIKE '%"+s+"%'", tblTrainingList);
            }            
        }
        tblTrainingList.getColumns().get(0).setResizable(false);
        tblTrainingList.getColumns().get(0).setPrefWidth(1);        
    }
    
    
    
    @FXML
    private void search(){  
//        JFXDialogLayout layout = new JFXDialogLayout();
//        JFXDialog dialog = new JFXDialog();        
//        layout.setHeading(new Text("Heading"));        
//        scn.loadModal(layout, dialog);
        
        String s = tfSearch.getText().trim();        
        String type = cmbType.getSelectionModel().getSelectedItem();
        String col = cmbFilterBy.getSelectionModel().getSelectedItem();
        
        /**
         * Column Filters
         * 
         */
        
        
        if(!s.isEmpty()){
            if(!chbtoggleInActiveTraining.isSelected()){
                db.populateTable(trainingFields+ " WHERE title LIKE '%"+s+"%' AND status<>'active'", tblTrainingList);
            }

            if(chbtoggleInActiveTraining.isSelected()){
                db.populateTable(trainingFields+ " WHERE title LIKE '%"+s+"%'", tblTrainingList);
            }            
        }        
    }

    private void saveNewTraining(ActionEvent event) {          
        try{
            String title = tfTrainingTitle.getText();
            int participants = Integer.parseInt(tfNumberOfParticipants.getText());
            LocalTime time = tpTrainingTime.getValue();
            LocalDate date = dpTrainingDate.getValue();
            String venue = tfTrainingLocation.getText();
            String type = (String) cbTrainingType.getSelectionModel()
                                                 .getSelectedItem().toString();

            System.out.println(title);
            System.out.println(participants);
            System.out.println(time);
            System.out.println(date);
            System.out.println(venue);
            System.out.println(type);

            String q = "INSERT INTO training "+
                       "(training_id, "+
                       "title, participants, start_time, target_date, venue, TYPE) " +
                       "VALUES(NULL, '"+title+
                       "', '"+participants+
                       "', '"+time+
                       "', '"+date+
                       "','"+venue+
                       "','"+type+"');";

            db.execute(q);

            refreshTable();
            clearTrainingFields();            
        }catch(Exception e){
            System.out.println(e);
        }
    }   


    
    private void addToTraining(String trainingId, String empId){
        String q = "INSERT INTO training_participants (training_id,emp_id) "
                + "VALUES("+trainingId+","+empId+")";
        db.execute(q);
    }
    
    private void removeFromTraining(String trainingId, String empId){
        String q = "DELETE FROM training_participants "
                 + "WHERE training_id="+trainingId
                 + " AND emp_id="+empId;
       
        db.execute(q);
    }
    
    private void populateVBXTrainees(){
        String q = "SELECT employee.emp_id AS 'id',"
                + "CONCAT(employee.lastname,', ', employee.firstname, "
                + "' (',employee_position.position_name,')') AS 'NAME',  "
                + "department.department_code AS 'CODE' "
                + "FROM employee, employee_position, department "
                + "WHERE employee.position_id = employee_position.position_id "
                + "AND employee_position.department_code = "
                + "department.department_code;";
        
        rs = db.displayRecords(q);
        
        vbxTrainees.getChildren().clear();
        
        try {
            while(rs.next()){
                JFXCheckBox cbEmployee = new JFXCheckBox(rs.getString("NAME"));
                cbEmployee.setAccessibleText(rs.getString("id"));
                
                String qry = "SELECT * FROM training_participants WHERE training_id="+selectedTrainingId;
                
                ResultSet r = db.displayRecords(qry);
                while(r.next()){
                    if(rs.getString("id").equals(r.getString("emp_id"))){
                        cbEmployee.setSelected(true);
                    }
                }
                                
                vbxTrainees.getChildren().add(cbEmployee);
                
                cbEmployee.setOnAction((event) -> {
                    String empId = cbEmployee.getAccessibleText();
                    if(cbEmployee.isSelected()){
                        addToTraining(selectedTrainingId, empId);
                    }else{
                        removeFromTraining(selectedTrainingId, empId);
                    }
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    public String id;
    @FXML
    private void setSelectedTraining(MouseEvent event) {
        if(tblTrainingList.getSelectionModel().getSelectedItem() != null){                
            Object o = tblTrainingList.getSelectionModel().getSelectedItem();
            id = o.toString().split(",")[0].substring(1);
            
            int dbClick = event.getClickCount();            
            if(dbClick == 2){
                viewDetails();;
            }
            
//            String title = o.toString().split(",")[1].substring(1);
//            String location = o.toString().split(",")[2].substring(1);
//            String nOParticipants = o.toString().split(",")[3].substring(1);
//            String time = o.toString().split(",")[4].substring(1);
//            String date = o.toString().split(",")[5].substring(1);
//            String type = o.toString().split(",")[6].substring(1);           
//            selectedTrainingId = id;
//            
//            System.out.println(selectedTrainingId);
//            
//            String q = "SELECT training_id as 'ID', "
//                + "title as 'TITLE', "
//                + "participants as 'PARTICIPANTS', "
//                + "start_time as 'TIME', "
//                + "target_date as 'DATE', "
//                + "venue as 'VENUE', "
//                + "TYPE FROM training "
//                + "WHERE training_id="+selectedTrainingId;
//            
//            rs = db.displayRecords(q);
//            try {
//                if(rs.next()){
//                    tfTrainingTitle.setText(rs.getString("TITLE"));
//                    lblTraineesForTraining.setText(rs.getString("TITLE"));
//                    
//                    tfNumberOfParticipants.setText(rs.getString("PARTICIPANTS"));
//                    tfTrainingLocation.setText(rs.getString("VENUE"));
//                    dpTrainingDate.setValue(rs.getDate("DATE").toLocalDate());
//                    tpTrainingStart.setValue(rs.getTime("TIME").toLocalTime());
//                    
//                    cbTrainingType.getSelectionModel().select(rs.getString("TYPE").toLowerCase());
//                    
//                    btnUpdate.setDisable(false);
//                    
//                    populateVBXTrainees();                                        
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(TrainingManagementController.class.getName())
//                      .log(Level.SEVERE, null, ex);
//            }  
//            
//            
//            btnNew.setDisable(true);
//            btnUpdate.setDisable(false);
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Table Row Selection Error");
            alert.setContentText("No Table Row Selected.");
        }
    }

    private void searchTrainees(KeyEvent event) {
        String s = tfSearchTrainees.getText().trim();
        String q = "SELECT employee.emp_id AS 'id',"
                + "CONCAT(employee.lastname,', ', employee.firstname, "
                + "' (',employee_position.position_name,')') AS 'NAME',  "
                + "department.department_code AS 'CODE' "
                + "FROM employee, employee_position, department "
                + "WHERE employee.position_id = employee_position.position_id "
                + "AND employee_position.department_code = "
                + "department.department_code "
                + "AND employee.lastname LIKE '%"+s+"%' ";
        
        rs = db.displayRecords(q);
        
        vbxTrainees.getChildren().clear();
        
        try {
            while(rs.next()){
                JFXCheckBox cbEmployee = new JFXCheckBox(rs.getString("NAME"));
                cbEmployee.setAccessibleText(rs.getString("id"));
                
                String qry = "SELECT * FROM training_participants WHERE training_id="+selectedTrainingId;
                
                ResultSet r = db.displayRecords(qry);
                while(r.next()){
                    if(rs.getString("id").equals(r.getString("emp_id"))){
                        cbEmployee.setSelected(true);
                    }
                }
                                
                vbxTrainees.getChildren().add(cbEmployee);                                                
                cbEmployee.setOnAction((even) -> {
                    String empId = cbEmployee.getAccessibleText();
                    if(cbEmployee.isSelected()){
                        addToTraining(selectedTrainingId, empId);
                    }else{
                        removeFromTraining(selectedTrainingId, empId);
                    }
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    
    // JasperReport
    private void generateReport(ActionEvent event) {
        try {
        String lc = "C:\\Users\\NelsonDelaTorre\\Documents\\NetBeansProjects\\Bus_Transit-master\\src\\bus_transit\\hr\\training\\reports\\Training.jrxml";
        
        String q =  "SELECT employee.firstname, employee.lastname, employee_position.position_name " +
                    "FROM training, training_participants, employee, employee_position " +
                    "WHERE training_participants.training_id='"+id+
                    "' AND training_participants.emp_id=employee.emp_id " +
                    "AND employee.position_id=employee_position.position_id " +
                    "GROUP BY employee.emp_id";

            JasperDesign jd = JRXmlLoader.load(lc);
            JRDesignQuery jdq = new JRDesignQuery();
            jdq.setText(q);
            jd.setQuery(jdq);
            JasperReport jr = JasperCompileManager.compileReport(lc);
            JasperPrint jrp = JasperFillManager.fillReport(jr, null, db.getConnection());            
            JasperViewer.viewReport(jrp, false);
        } catch (JRException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    private void printTraining(ActionEvent event) {
        String q =  "SELECT employee.firstname, employee.lastname, employee_position.position_name\n" +
                    "FROM training, training_participants, employee, employee_position\n" +
                    "WHERE training_participants.training_id=2 \n" +
                    "AND training_participants.emp_id=employee.emp_id\n" +
                    "AND employee.position_id=employee_position.position_id\n" +
                    "GROUP BY employee.emp_id;";  
        
        toPDF(q, "Training Participants", "Training Participants");
        
//        //PrintReport printReport = new PrintReport();
//        try {
//        String lc = "C:\\Users\\NelsonDelaTorre\\Documents\\NetBeansProjects\\Bus_Transit-master\\src\\bus_transit\\hr\\training\\reports\\Training.jrxml";
//        String q =  "SELECT employee.firstname, employee.lastname, employee_position.position_name\n" +
//                    "FROM training, training_participants, employee, employee_position\n" +
//                    "WHERE training_participants.training_id=2 \n" +
//                    "AND training_participants.emp_id=employee.emp_id\n" +
//                    "AND employee.position_id=employee_position.position_id\n" +
//                    "GROUP BY employee.emp_id;";
//
//            JasperDesign jd = JRXmlLoader.load(lc);
//            JRDesignQuery jdq = new JRDesignQuery();
//            jdq.setText(q);
//            jd.setQuery(jdq);
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            JasperPrint jrp = JasperFillManager.fillReport(jr, null, db.connection);
//            JRViewer jrv = new JRViewer(jrp);
//            jrv.setVisible(true);
//            JasperViewer.viewReport(jrp);
//        } catch (JRException ex) {
//            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }        
    }
    
    private void toPDF(String q, String reportName, String module) {
        try {          
            // Instantiate the document object
            Document document = new Document();

            // Set the location file
            PdfWriter.getInstance(document,new FileOutputStream("C:\\Users\\NelsonDelaTorre\\Documents\\NetBeansProjects\\Bus_Transit-master\\src\\bus_transit\\hr\\training\\reports\\"+reportName.replaceAll("\\s+","")+".pdf"));

            // Open the document
            document.open();

            // Add Document Header
            Paragraph heading1
                    = new Paragraph("BUS Transportation System",
                            FontFactory.getFont(FontFactory.TIMES_BOLD,
                                    12, Font.BOLD, BaseColor.BLACK));

            heading1.setAlignment(Element.ALIGN_CENTER);
            document.add(heading1);

            // heading2
            Paragraph heading2
                    = new Paragraph("BSIT 4101 - Batch 2019",
                            FontFactory.getFont(FontFactory.TIMES,
                                    12, Font.NORMAL, BaseColor.BLACK));

            heading2.setAlignment(Element.ALIGN_CENTER);
            document.add(heading2);

            document.add(new Paragraph("\n\n\n"));
            // Document Header ends here

            /**
             * Add Table document
             */
            
            // get result from database
            rs = db.displayRecords(q);
            
            try {
                // get number of columns
                int columns = rs.getMetaData().getColumnCount();

                // Set table header
                PdfPTable table = new PdfPTable(columns);
                PdfPCell cell = new PdfPCell(new Paragraph("\n"+reportName+"\n\n"));
                
                cell.setColspan(columns);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                //cell.setBackgroundColor(BaseColor.YELLOW);
    
                // This line will add table header
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    table.addCell(new PdfPCell(
                                  new Paragraph(
                                  rs.getMetaData()
                                  .getColumnName(i))));
                }
                
                // This line will add data to pdf table from the database
                while (rs.next()) {                    
                    for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){                     
                        table.addCell(new PdfPCell(
                                      new Paragraph(
                                      rs.getString(
                                      rs.getMetaData()
                                        .getColumnName(i)).toUpperCase())));
                    }
                }
                
                // finally add table to the document
                document.add(table);
                document.setPageCount(2);

            } catch (SQLException ex) {
                Logger.getLogger(TrainingManagementController
                      .class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(TrainingManagementController
                      .class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(new Paragraph("\n\n\n"));
            
//            Rectangle imgSize = new Rectangle(300, 250);
//            
//            com.itextpdf.text.Image image
//                    = com.itextpdf.text.Image.getInstance( "src\\bus_transit\\hr\\reports\\imgs\\bar_chart_"+module+".png");
//
//            image.scaleToFit(imgSize);
//            document.add(image);
//
//            com.itextpdf.text.Image image2
//                    = com.itextpdf.text.Image.getInstance( "src\\bus_transit\\hr\\reports\\imgs\\pie_chart_"+module+".png");
//
//            image2.scaleToFit(imgSize);
//            document.add(image2);
//            
//            com.itextpdf.text.Image image3
//                    = com.itextpdf.text.Image.getInstance("src\\bus_transit\\hr\\reports\\imgs\\line_chart_"+module+".png");
//
//            image3.scaleToFit(imgSize);
//            document.add(image3);            

            // close the document
            document.close();
            
            // now open the document            
            File reportFile = new File("C:\\Users\\NelsonDelaTorre\\Documents\\NetBeansProjects\\Bus_Transit-master\\src\\bus_transit\\hr\\training\\reports\\"+reportName.replaceAll("\\s+","")+".pdf");
            if(reportFile.exists()){
                if(Desktop.isDesktopSupported()){
                    try{
                        Desktop.getDesktop().open(reportFile);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("AWT Desktop not supported!");
                }              
            }else{
                System.out.println("File not exists.");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }


    private void loadNewTrainingModal(ActionEvent event) {
        loadTrainingUtility("new training");
//        try {
//            dialog = new JFXDialogLayout();
//            
//            Stage stage = new Stage();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingUtility.fxml"));
//            Parent root = loader.load();
//
//            TrainingUtilityController trUtl = loader.getController();
//            
//            dialog.setBody(root);
//            
//            dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);
//            
//            dlg.show();
//            
//            JFXButton save = new JFXButton("Save");
//            save.setOnAction((sEvt) -> {
//                dlg.close();
//            });
//            
//            JFXButton cancel = new JFXButton("Cancel");
//            cancel.setOnAction((sEvt) -> {
//                dlg.close();
//            });
//                
//            dialog.setActions(save,cancel);
//        } catch (IOException ex) {
//            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    private void loadTrainingUtility(String act){
        try {
            if(act.trim() != null){
                JFXDialogLayout dialog;
                JFXDialog dlg;                
                dialog = new JFXDialogLayout();
                dialog.setPadding(new Insets(0, 0, 0, 0));
                          
                //Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingUtility.fxml"));                                
                TrainingUtilityController trUtl = new TrainingUtilityController();
                // new or edit
                trUtl.action = act;
                trUtl.trainingId = selectedTrainingId;
                
                loader.setController(trUtl);                
                Parent root = loader.load();
                dialog.setBody(root);                                
                
                dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);

                dlg.show();

                JFXButton save = new JFXButton("Save");
                save.setOnAction((sEvt) -> {
                    dlg.close();
                });

                JFXButton cancel = new JFXButton("Cancel");
                cancel.setOnAction((sEvt) -> {
                    dlg.close();
                });

                dialog.setActions(save,cancel);
            }
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    private void viewDetails() {
        try {            
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            dialog.setPadding(new Insets(0, 0, 0, 0));
            
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTraining.fxml")); 
            ViewTrainingController controller = new ViewTrainingController();
            controller.trainingId = id;
            //loader.setController(controller);            
            Parent root = loader.load();            
            dialog.setBody(root);
            
            dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);
            
            dlg.show();            
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }       
    

    private void editTraining(ActionEvent event) {
        try {
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            dialog.setPadding(new Insets(0, 0, 0, 0));

            //Stage stage = new Stage();
            FXMLLoader loader =
            new FXMLLoader(getClass().getResource("EditTraining.fxml")); 
            EditTrainingController controller = new EditTrainingController();
            controller.trainingId = id;
            loader.setController(controller);            
                
            Parent root = loader.load();
            dialog.setBody(root);                                

            dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);

            dlg.show();

            JFXButton save = new JFXButton("Save");
            save.setOnAction((sEvt) -> {
                controller.updateTraining(id);
                dlg.close();
            });

            JFXButton cancel = new JFXButton("Cancel");
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });

            HBox hbx = new HBox();
            hbx.setPadding(new Insets(8));
            hbx.setSpacing(10);
            hbx.getChildren().add(save);
            hbx.getChildren().add(cancel);            
            
            dialog.setActions(save,cancel);
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } 
    }

    @FXML
    private void newTraining(ActionEvent event) {
        try {
            //loadTrainingUtility("new training");
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            //dialog.setPadding(new Insets(0, 0, 0, 0));
            
            //Stage stage = new Stage();
            FXMLLoader loader = 
                    new FXMLLoader(getClass().getResource("NewTraining.fxml")); 
            NewTrainingController controller = new NewTrainingController();
            loader.setController(controller);
            Parent root = loader.load();            
            dialog.setBody(root);
            
            dlg = new JFXDialog(stackpane,
                                dialog,
                                JFXDialog.DialogTransition.CENTER);
            
            dlg.show();
            
            JFXButton save = new JFXButton("Save");  
            save.getStyleClass().add("xsm-green-button");
            save.setOnAction((sEvt) -> {
                controller.save();
                refreshTable();
                dlg.close();               
            });
            
            JFXButton cancel = new JFXButton("Cancel");    
            cancel.getStyleClass().add("xsm-green-button");
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
            
            HBox hbx = new HBox();
            hbx.setPadding(new Insets(8));
            hbx.setSpacing(10);
            hbx.getChildren().add(save);
            hbx.getChildren().add(cancel);
            
            dialog.setActions(hbx);
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void toggleInActiveTraining(ActionEvent event) {
        JFXCheckBox cbx = ((JFXCheckBox) event.getSource());
        if(cbx.isSelected()){
           // show active            
           db.populateTable(trainingFields , tblTrainingList);  
        }
        
        if(!cbx.isSelected()){
           // show inactive
           db.populateTable(trainingFields+ " WHERE status<>'inactive'", 
                            tblTrainingList);                        
        }   
        tblTrainingList.getColumns().get(0).setResizable(false);
        tblTrainingList.getColumns().get(0).setPrefWidth(1);        
    }

    @FXML
    private void editDetails(ActionEvent event) {
        try {
            //loadTrainingUtility("new training");
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            dialog.setPadding(new Insets(0, 0, 0, 0));
            
            //Stage stage = new Stage();
            FXMLLoader loader = 
            new FXMLLoader(getClass().getResource("EditTraining.fxml")); 
            EditTrainingController controller = new EditTrainingController();
            controller.trainingId = id;
            loader.setController(controller);            
            Parent root = loader.load();            
            dialog.setBody(root);
            
            dlg = 
            new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);
            
            dlg.show();
                        
            HBox hbx = new HBox();
            hbx.setPadding(new Insets(8));
            hbx.setSpacing(10);
            JFXButton save = new Buttons().GreenButton("Save");
            hbx.getChildren().add(save);
            JFXButton cancel = new Buttons().RedButton("Cancel");
            hbx.getChildren().add(cancel);
                        
            save.setOnAction((sEvt) -> {
                controller.updateTraining(id);
                refreshTable();
                dlg.close();               
            });
            
            
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
                    
            dialog.setActions(hbx);
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }         
    }

    @FXML
    private void editDateTime(ActionEvent event) {
        try {
            //loadTrainingUtility("new training");
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            dialog.setPadding(new Insets(0, 0, 0, 0));
            
            //Stage stage = new Stage();
            FXMLLoader loader = 
            new FXMLLoader(getClass().getResource("AddDateTimeToTraining.fxml")); 
            AddDateTimeToTrainingController controller = 
            new AddDateTimeToTrainingController();
            controller.trainingId = id;
            loader.setController(controller);            
            Parent root = loader.load();            
            dialog.setBody(root);
            
            dlg = 
            new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);
            
            dlg.show();
            
            HBox hbx = new HBox();
            hbx.setPadding(new Insets(8));
            hbx.setSpacing(10);
            JFXButton save = new Buttons().GreenButton("Save");
            hbx.getChildren().add(save);
            JFXButton cancel = new Buttons().RedButton("Cancel");
            hbx.getChildren().add(cancel);            
                        
            save.setOnAction((sEvt) -> {
                controller.save();
                refreshTable();
                dlg.close();               
            });
                        
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
                    
            dialog.setActions(hbx);
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void editSponsorsAndTrainors(ActionEvent event) {
        try {
            //loadTrainingUtility("new training");
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            dialog.setPadding(new Insets(0, 0, 0, 0));
            
            //Stage stage = new Stage();
            FXMLLoader loader =
            new FXMLLoader(getClass().getResource("Sponsors.fxml")); 
            
            SponsorsController controller = new SponsorsController();                                                
            controller.trainingId = id;
            loader.setController(controller);            
            Parent root = loader.load();
            dialog.setBody(root);
            
            dlg =
            new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);
            
            dlg.show();
            
            HBox hbx = new HBox();
            hbx.setPadding(new Insets(8));
            hbx.setSpacing(10);
            JFXButton save = new Buttons().GreenButton("Save");
            hbx.getChildren().add(save);
            JFXButton cancel = new Buttons().RedButton("Cancel");
            hbx.getChildren().add(cancel);            
                        
            save.setOnAction((sEvt) -> {                
                refreshTable();
                
                dlg.close();               
            });
                        
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
                    
            dialog.setActions(hbx);
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void setTrainees(ActionEvent event) {
        try {
            //loadTrainingUtility("new training");
            JFXDialogLayout dialog;
            JFXDialog dlg;            
            dialog = new JFXDialogLayout();
            dialog.setPadding(new Insets(0, 0, 0, 0));
            
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Participants.fxml")); 
            ParticipantsController controller = new ParticipantsController();
            controller.trainingId = id;
            //loader.setController(controller);            
            Parent root = loader.load();            
            dialog.setBody(root);
            
            dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);
            
            dlg.show();
            
            HBox hbx = new HBox();
            hbx.setPadding(new Insets(8));
            hbx.setSpacing(10);
            JFXButton save = new Buttons().GreenButton("Save");
            hbx.getChildren().add(save);
            JFXButton cancel = new Buttons().RedButton("Cancel");
            hbx.getChildren().add(cancel);            
                        
            save.setOnAction((sEvt) -> {                
                refreshTable();
                dlg.close();               
            });
                        
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
                    
            dialog.setActions(hbx);
        } catch (IOException ex) {
            Logger.getLogger(TrainingManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }
    
    /*private void addActionButton(){
        TableColumn actionCol = new TableColumn("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<?, String>, TableCell<?, String>> cellFactory
                = 
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            @Override
            public TableCell call(final TableColumn<Person, String> param) {
                final TableCell<Person, String> cell = new TableCell<Person, String>() {

                    final Button btn = new Button("Just Do It");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Person person = getTableView().getItems().get(getIndex());
                                System.out.println(person.getFirstName()
                                        + "   " + person.getLastName());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(cellFactory);

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, actionCol);            
    }*/
}