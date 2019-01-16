/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.Tab;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningManagementReportController extends Application implements Initializable {

    @FXML
    private AnchorPane heading;
    @FXML
    private Tab tabCompletionRate;
    @FXML
    private Tab tabLearnersSatisfaction;
    @FXML
    private Tab tabParticiationRate;
    @FXML
    private Tab tabResultAssessment;

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String qry;
    
    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane content;
    private BarChart<?, ?> barChartDefensiveDriving;
    private LineChart<?, ?> lineChartDefensiveDriving;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private JFXButton btnToPDF;
    private String imgLocation;
    @FXML
    private PieChart pieChart;
    @FXML
    private JFXButton btnToPDF_competency;
    @FXML
    private LineChart<?, ?> lineChart_competency;
    @FXML
    private BarChart<?, ?> barChart_competency;
    @FXML
    private PieChart pieChart_competency;
    @FXML
    private JFXButton btnToPDF_training;
    @FXML
    private LineChart<?, ?> lineChart_training;
    @FXML
    private BarChart<?, ?> barChart_training;
    @FXML
    private PieChart pieChart_training;
    @FXML
    private LineChart<?, ?> lineChart_succession;
    @FXML
    private BarChart<?, ?> barChart_succession;
    @FXML
    private PieChart pieChart_succession;
    @FXML
    private Tab tabResultAssessment1;
    @FXML
    private JFXButton btnToPDF_ess;
    @FXML
    private LineChart<?, ?> lineChart_ess;
    @FXML
    private BarChart<?, ?> barChart_ess;
    @FXML
    private PieChart pieChart_ess;
    @FXML
    private JFXButton btnToPDF_succession;

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
/*        
        // Learning
        String qry_learning = "SELECT title, completion_rate FROM learning_modules";    
        displayCharts(qry_learning, "Learning Modules Completion Rate", lineChart, barChart, pieChart);
        btnToPDF.setOnAction(e -> {
            chartToPNG(barChart, "bar_chart_learning.png");
            chartToPNG(pieChart, "pie_chart_learning.png");
            chartToPNG(lineChart, "line_chart_learning.png");
            toPDF(qry_learning, "Learning Management Report", "learning");
        });
        
        // Competency
        String qry_cmptcy = "SELECT remarks, population FROM competency_report";       
        displayCharts(qry_cmptcy, "Competency Report", lineChart_competency, barChart_competency, pieChart_competency);       
        btnToPDF_competency.setOnAction(e -> {
            chartToPNG(barChart_competency, "bar_chart_competency.png");
            chartToPNG(pieChart_competency, "pie_chart_competency.png");
            chartToPNG(lineChart_competency, "line_chart_competency.png");
            toPDF(qry_cmptcy, "Competency Management Report", "competency");
        });
        
        // ESS
        String qry_ess = "SELECT employee_type, login_count FROM ess;";
        displayCharts(qry_ess, "ESS Report",lineChart_ess, barChart_ess, pieChart_ess);
        btnToPDF_ess.setOnAction(e -> {
            chartToPNG(barChart_ess, "bar_chart_ess.png");
            chartToPNG(pieChart_ess, "pie_chart_ess.png");
            chartToPNG(lineChart_ess, "line_chart_ess.png");
            toPDF(qry_ess, "ESS Report", "ess");
        });

        // succession
        String qry_succesion = "SELECT succession_name, succession_rate FROM succ;";        
        displayCharts(qry_succesion, "Succession Planning Report", lineChart_succession, barChart_succession, pieChart_succession);
        btnToPDF_succession.setOnAction(e -> {
            chartToPNG(barChart_succession, "bar_chart_succession.png");
            chartToPNG(pieChart_succession, "pie_chart_succession.png");
            chartToPNG(lineChart_succession, "line_chart_succession.png");
            toPDF(qry_succesion, "Succession Planning Report", "succession");
        });

        // Training
        String qry_training = "SELECT DATE, participants FROM training";
        displayCharts(qry_training, "Training Report", lineChart_training, barChart_training, pieChart_training);
        btnToPDF_training.setOnAction(e -> {
            chartToPNG(barChart_training, "bar_chart_training.png");
            chartToPNG(pieChart_training, "pie_chart_training.png");
            chartToPNG(lineChart_training, "line_chart_training.png");            
            toPDF(qry_training, "Training Management Report", "training");
        });
*/       
    }

    
//    private Image getAsPNG(BarChart b) {
//        WritableImage image = b.snapshot(new SnapshotParameters(), null);
//        Image img = image;
//        File file = new File("barchart.png");
//        try {
//            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        return img;
//    }    
    
    
    //chartToPNG(NAMEOFCHART, FILENAMe);
    
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

    private void displayCharts(String q, String chartTitle, LineChart l, BarChart b, PieChart p){
        //q = "SELECT `title`,`completion_rate`,`date_implemented` FROM `bustransit_master`.`learning_modules`;";
        rs = db.displayRecords(q);                
        try {
            if (rs.next()) {
                db.createLineChart(chartTitle, rs.getMetaData().getColumnName(1), q, l);
                db.createBarChart(chartTitle, rs.getMetaData().getColumnName(1), q, b);
                db.createPieChart(chartTitle, q, p);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementReportController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }    
    }
    
    
    
  
    
    
    
    
    /**
     * Naming Convention
     * 
     * Database: database_name
     * table : table_name
     * column: column_name
     * 
     * Java
     * PascalCase example JOptionPane
     * camelCase example getText()
     * 
     * Scene Builder
     * class-name example .btn-default
     * fxml ID example btnDefault
     */   
    
    // Convert JavaFX chart to JPG format
//    private void chartToJPG(Chart b, String fileName) {
//        b.setAnimated(false);
//        WritableImage image = b.snapshot(
//                new SnapshotParameters(), null);
//        File file = new File(
//                "src\\bus_transit\\hr\\reports\\imgs\\"
//                + fileName);
//        try {
//            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }

    private void toPDF(String q, String reportName, String module) {
        try {
            // Instantiate the document object
            Document document = new Document();

            // Set the location file
            PdfWriter.getInstance(document,
                    new FileOutputStream(
                            "src\\bus_transit\\hr\\reports\\"
                            +reportName.replaceAll("\\s+","")+".pdf"
                    ));

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
                Logger.getLogger(LearningManagementReportController
                      .class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(LearningManagementReportController
                      .class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(new Paragraph("\n\n\n"));
            
            Rectangle imgSize = new Rectangle(300, 250);
            
            com.itextpdf.text.Image image
                    = com.itextpdf.text.Image
                            .getInstance(
                            "src\\bus_transit\\hr\\reports\\imgs\\bar_chart_"+module+".png");

            image.scaleToFit(imgSize);
            document.add(image);

            com.itextpdf.text.Image image2
                    = com.itextpdf.text.Image
                            .getInstance(
                            "src\\bus_transit\\hr\\reports\\imgs\\pie_chart_"+module+".png");

            image2.scaleToFit(imgSize);
            document.add(image2);
            
            com.itextpdf.text.Image image3
                    = com.itextpdf.text.Image
                            .getInstance(
                            "src\\bus_transit\\hr\\reports\\imgs\\line_chart_"+module+".png");

            image3.scaleToFit(imgSize);
            document.add(image3);            

            // close the document
            document.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LearningManagementReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(LearningManagementReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    private void addTableToPDFReport(ResultSet r, Document d) {
        // while result set has records

        try {
            // get number of columns
            int columns = r.getMetaData().getColumnCount();

            // get number of rows
            int rows = 0;

            PdfPTable table = new PdfPTable(13);
            PdfPCell cell = new PdfPCell(new Paragraph("Information"));
            cell.setColspan(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.YELLOW);
            while (r.next()) {

                rows++;
            }

            d.add(table);

            System.out.println("n of Columns:" + columns);
            System.out.println("n of rows:" + rows);
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(LearningManagementReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LearningManagementReport.fxml"));
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