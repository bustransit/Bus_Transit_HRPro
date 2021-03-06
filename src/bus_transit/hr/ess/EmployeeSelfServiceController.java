/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.ess;

import bus_transit.hr.learning.LearningManagementReportController;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class EmployeeSelfServiceController extends Application implements Initializable {

    @FXML
    private JFXButton btn_menu;
    @FXML
    private JFXButton btn_pdf;
    DBUtilities db = new DBUtilities();
    @FXML
    private JFXButton btn_bar;
    private String q;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private PieChart myPiechart;
    @FXML
    private BarChart<?, ?> myBarChart;
    @FXML
    private JFXButton btn_line;
    @FXML
    private StackedAreaChart<?, ?> myLineChart;
   
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        q = "SELECT succession_name, succession_rate FROM succ";
        //para lumabas data sa chart mo 
        db.createPieChart("Internal Placement Rate", q, myPiechart);
        q = "SELECT employee_type, employee_rate FROM barmoto";
        db.createBarChart("Successor Readines For Key Roles", "2018 Month Of September", q, myBarChart);
        
        db.createStackedAreaChart("Position Filled By Nominated Successor","2018 Month Of September",q,myLineChart);
        
      //  db.createStackedAreaChart("Position Filled By Nominated Successor", "2018 Month Of September", q, myLineChart);
        
 
              
        
        
    } 
        
    @FXML
    private void back(ActionEvent event) {
    }


    private void chartToPNG(Chart chart, String fileName) {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        // Your desired location
        File file = new File("src\\bus_transit\\hr\\reports\\imgs\\"
                + fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    // Convert JavaFX chart to JPG format
    private void chartToJPG(Chart b, String fileName) {
        b.setAnimated(false);
        WritableImage image = b.snapshot(
                new SnapshotParameters(), null);
        File file = new File(
                "src\\bus_transit\\hr\\reports\\imgs\\"
                + fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void toPDF(String q, String reportName) {
        try {
            // Instantiate the document object
            Document document = new Document();

            // Set the location file
            PdfWriter.getInstance(document,
                    new FileOutputStream(
                            "src\\bus_transit\\hr\\reports\\imgs\\"
                            +reportName.replaceAll("\\s+","")+".pdf"
                    ));

            // Open the document
            document.open();

            // Add Document Header
            Paragraph heading1
                    = new Paragraph("BUS Transportation System",
                            FontFactory.getFont(FontFactory.TIMES_BOLD,
                                    14, Font.BOLD, BaseColor.BLACK));

            heading1.setAlignment(Element.ALIGN_CENTER);
            document.add(heading1);

            // heading2
            Paragraph heading2
                    = new Paragraph("BSIT 4101 - Batch 2019",
                            FontFactory.getFont(FontFactory.TIMES_BOLD,
                                    12, Font.BOLD, BaseColor.BLACK));

            heading1.setAlignment(Element.ALIGN_CENTER);
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

                
                
                
                
                
                
                
                // This line will add data to pdf table from the database
                while (rs.next()) {                    
                    for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){                     
                        table.addCell(new PdfPCell(
                                      new Paragraph(
                                      rs.getString(
                                      rs.getMetaData()
                                        .getColumnName(i)))));
                    }
                }

                
                
                
                
                
                
                
                
                // finally add table to the document
                document.add(table);
                document.setPageCount(2);

            } catch (SQLException ex) {
                Logger.getLogger(LearningManagementReportController
                      .class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(LearningManagementReportController
                      .class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(new Paragraph("\n\n\n"));
            
            com.itextpdf.text.Image image
                    = com.itextpdf.text.Image
                            .getInstance(
                            "src\\bus_transit\\hr\\reports\\imgs\\bar_chart.png");

            Rectangle imgSize = new Rectangle(300, 250);

            image.scaleToFit(imgSize);
            document.add(image);

            com.itextpdf.text.Image image2
                    = com.itextpdf.text.Image
                            .getInstance(
                            "src\\bus_transit\\hr\\reports\\imgs\\piechart.png");

            image2.scaleToFit(imgSize);
            document.add(image2);

            // close the document
            document.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeSelfServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(EmployeeSelfServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeSelfServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass().getResource("EmployeeSelfService.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
