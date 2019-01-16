/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.succession;

import bus_transit.hr.training.TrainingManagementController;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SuccessionPlanningController extends Application implements Initializable {

    private JFXButton btn_report;
    @FXML
    private JFXButton btn_pdf;
    private String External;
    private String Internal;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DBUtilities db = new DBUtilities();
    @FXML
    private PieChart myPiechart;
    private String q;
    @FXML
    private JFXButton btn_pdf2;
    @FXML
    private BarChart<?, ?> myBarChart;
    @FXML
    private StackedAreaChart<?, ?> myLineChart;
    @FXML
    private JFXButton btn_report3;
    @FXML
    private Tab modules;
    @FXML
    private CustomTextField txt_searchAll;
    @FXML
    private JFXButton btnNew;
    @FXML
    private Tooltip btnNewToolTip;
    @FXML
    private StackPane stackPane;
    @FXML
    private FlowPane fpSuccessionPlan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //database data
        q = "SELECT succession_name, succession_rate FROM succ";
        //para lumabas data sa chart mo 
        db.createPieChart("Internal Placement Rate", q, myPiechart);
        q = "SELECT employee_type, employee_rate FROM barmoto";
        db.createBarChart("Successor Readines For Key Roles", "2018 Month Of September", q, myBarChart);
                
      //  q = "SELECT employee_type, employee_rate FROM barmoto";
        db.createStackedAreaChartLap("Position Filled By Nominated Successor","2018 Month Of September",q,myLineChart);
        
        q = "SELECT Quarter, Quarter_rate FROM stackmoto";
        db.createStackedAreaChartLap("Position Filled By Nominated Successor","2018 Month Of September",q,myLineChart);
        
        btn_pdf.setOnAction(e -> {            
            chartToPNG(myBarChart,"BarChartReport.png");
            chartToPNG(myLineChart,"BarChartReport.jpg");
            chartToPNG(myPiechart,"piechart.png");
        });
        
       populateSuccesionPlanePane();
    }
    
    private void chartToPNG(Chart b, String filename){
        b.setAnimated(false);
        WritableImage image = b.snapshot(
            new SnapshotParameters(), null);
        File file = new File("scr\\bus_transit\\hr\\reports\\"+filename);
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg",file);
        }catch(IOException e){
            System.out.println(e);
        }
                                
        btn_pdf2.setOnAction(e -> {
            chartToPNG(myPiechart, "BarChartReport.jpg");
            chartToPNG(myBarChart, "BarChartReport.jpg");
            chartToPNG(myLineChart, "BarChartReport.jpg");
        });
    }
        
    
    private void chartToJPG(Chart b, String filename){
        b.setAnimated(false);
        WritableImage image = b.snapshot(
            new SnapshotParameters(), null);
        File file = new File("scr\\bus_transit\\hr\\reports\\img\\"+filename);
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg",file);
        }catch(IOException e){
            System.out.println(e);
        }        
    }
    
    
    
      @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass().getResource("SuccessionPlanning.fxml"));
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
    private void SearchAllRequest(KeyEvent event) {
    }

    public void populateSuccesionPlanePane(){
        String q = "SELECT * FROM succession_plan";
        rs = db.displayRecords(q);
        fpSuccessionPlan.getChildren().clear();
        try {
            while(rs.next()){
                FXMLLoader l =  new FXMLLoader(getClass().getResource("SuccessionCard.fxml"));                                                            
                SuccessionCardController successionCardController = new SuccessionCardController();                
                successionCardController.setPositionId(rs.getString("position_id"));
                
                AnchorPane fp = l.load();
                l.setController(successionCardController);
                
                fpSuccessionPlan.getChildren().addAll(fp);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuccessionPlanningController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SuccessionPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    LocalDate dateExit;
    String positionId;
    String employeeId;
    String readiness;    
    
    @FXML
    private void loadNewSuccesionPlan(ActionEvent event) throws InterruptedException {
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("Examinee Info"));
        
        VBox vbx = new VBox();
        vbx.setFillWidth(true);
        
        String q = "";
        
        JFXComboBox cbPosion = new JFXComboBox();
        q = "SELECT CONCAT(position_id,', ', position_name) FROM employee_position";        
        db.populateComboBox(q, cbPosion);
        vbx.getChildren().add(cbPosion);
        cbPosion.setOnAction((e) -> {
            String s = cbPosion.getSelectionModel().getSelectedItem().toString();
            setSelectedPosition(s);
        });        
        
        JFXComboBox cbEmployee = new JFXComboBox();
        q = "SELECT CONCAT(emp_id,', ',lastname,"
            + "', ', firstname,' ', middlename) AS 'employee' FROM employee";
                        
        db.populateComboBox(q, cbEmployee);
        vbx.getChildren().add(cbEmployee);
        
        cbEmployee.setOnAction((e) -> {
            String s = cbEmployee.getSelectionModel().getSelectedItem().toString();
            setSelectedEmployee(s);
        });
        
//        JFXComboBox<String> cbReadiness = new JFXComboBox();
//        cbReadiness.getItems().add("IMMEDIATE");
//        cbReadiness.getItems().add("1-3 YEARS");
//        cbReadiness.getItems().add("HIGH POTENTIALS");
//        cbReadiness.setOnAction((evt) -> {
//            setReadiness(cbReadiness);
//        });        
//        vbx.getChildren().add(cbReadiness);
        
        JFXDatePicker dp = new JFXDatePicker();
        vbx.getChildren().add(dp);
        
        dp.setOnAction((evt) -> {
            dateExit = dp.getValue();
        });
        
        dialog.setBody(vbx);
        
        JFXDialog dlg = new JFXDialog(stackPane,dialog,JFXDialog.DialogTransition.CENTER);
        
        JFXButton btnOK = new JFXButton("OK");
        vbx.getChildren().add(btnOK);
        btnOK.setOnAction((evt) -> {                                         
            dlg.close();
        });
        
        JFXButton btnCancel = new JFXButton("CANCEL");
        vbx.getChildren().add(btnCancel);
        btnOK.setOnAction((evt) -> {          
            dlg.close();
        });        
        
                                      
        dlg.show();
        dialog.setActions(btnOK);        
        
        dlg.setOnDialogClosed((evt) -> {
            System.out.println("Date: "+dateExit);
            System.out.println("Employee ID: "+employeeId);
            System.out.println("Position ID: "+positionId);
            System.out.println("Readiness: "+readiness); 
            System.out.println("Hi");
            try{
                String qry = "INSERT INTO succession_plan "
                        + "(position_id, emp_id, exit_date) "
                        + "VALUES("+positionId
                        +","+employeeId
                        +",'"+dateExit+"')";
                db.execute(qry);
            }catch(Exception e){
                System.out.println(e);
            }
            populateSuccesionPlanePane();
        });
        
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
                Logger.getLogger(SuccessionPlanningController
                      .class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(SuccessionPlanningController
                      .class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(new Paragraph("\n\n\n"));
            
            Rectangle imgSize = new Rectangle(300, 250);
            
            com.itextpdf.text.Image image
                    = com.itextpdf.text.Image.getInstance( "src\\bus_transit\\hr\\succession\\reports\\imgs\\bar_chart_"+module+".png");

            image.scaleToFit(imgSize);
            document.add(image);

            com.itextpdf.text.Image image2
                    = com.itextpdf.text.Image.getInstance( "src\\bus_transit\\hr\\succession\\reports\\imgs\\pie_chart_"+module+".png");

            image2.scaleToFit(imgSize);
            document.add(image2);
            
            com.itextpdf.text.Image image3
                    = com.itextpdf.text.Image.getInstance("src\\bus_transit\\hr\\succession\\reports\\imgs\\line_chart_"+module+".png");

            image3.scaleToFit(imgSize);
            document.add(image3);            

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
            Logger.getLogger(SuccessionPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SuccessionPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SuccessionPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
    
    private void setSelectedPosition(String s){        
        positionId = s.toString().split(",")[0].substring(1);         
    }
    
    private void setSelectedEmployee(String s){
        employeeId = s.toString().split(",")[0].substring(1);         
    }
    
    private void setReadiness(JFXComboBox c){
        readiness = c.getSelectionModel().getSelectedItem().toString();        
    }
}
