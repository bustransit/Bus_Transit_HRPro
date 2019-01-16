package bus_transit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author NelsonDelaTorre
 */

public class Directories {
    // Component
    public URL employeeInfo;
    
    // MAIN
    public URL login;
    public URL dashboard;
    public URL sidePane;
    
    // SYSTEM
    public URL forgotPassword;
    
    // HR 1
    public URL applicantManagement,
               performanceManagement,
               newHireOnBoard,
               socialReconition,
               recruitment;
    
    // HR 2
    public URL learningManagement,
               trainingManagement,
               competencyManagement,
               successionPlanning,
               ess; 
    
    // HR 3
    public URL shiftAndSchedule,
               timeSheetManagement,
               leaveManagement,
               claimsAndReimbursement,
               timeAndAttendance;
        
    // HR 4
    public URL payroll,
               compensationPlanning,
               hrAnalytics,
               coreHumanCapital;
    
    // LOGISTIC 1
    public URL procurement,
               assetManagement,
               projectManagement,
               warehousing;
    
    // LOGISTIC 2
    public URL auditManagement,
               documentTracking,
               vehicleReservation,
               fleetManagementLog2;
    
    // CORE 1
    public URL fleetManagementCore,
               tripManagement,
               routeManagement,
               gpsTracking;
        
    // CORE 2
    public URL tireAndBatteryManagement,
               fuelManagement,
               expenseManagement,
               driverManagement;
    
    // ADMIN
    public URL documentManagement,
               legalManagement,
               visitorManagement,
               facilitiesReservation;
    
    // FINANCE
    public URL accounts,
               collection,
               disbursement,
               budgetManagement,
               generalLedger;
    
    public URL HRSidePane,
               HRDashboard;
        
    public Directories() {
        try {
            
            this.employeeInfo = new URL( "src/bus_transit/system/entities/EmployeeeInfo.fxml");
            
            // MAIN
            this.login = new URL("src/bus_transit/Login.fxml");
            this.dashboard = new URL("src/bus_transit/Dashboard.fxml");
            this.sidePane = new URL("src/bus_transit/Login.fxml");
                        
            // SYSTEM
            this.forgotPassword = new URL("src/bus_transit/system/ForgotPassword.fxml");
            
            // HR Dashboard
            this.HRDashboard = new URL("src/bus_transit/hr/HRDashboard.fxml");

            // HR SidePane
            this.HRSidePane = new URL("src/bus_transit/hr/HRSidePane.fxml");
            
            // HR 1
            this.applicantManagement = new URL("");
            this.performanceManagement = new URL("");
            this.newHireOnBoard = new URL("");
            this.socialReconition = new URL("");
            this.recruitment = new URL("");
                        
            // HR 2
            this.learningManagement = new URL("src/bus_transit/hr/learning/LearningManagement.fxml");
            this.trainingManagement = new URL("src/bus_transit/hr/training/TrainingManagement.fxml");
            this.competencyManagement = new URL("src/bus_transit/hr/competency/CompetencyManagement.fxml");
            this.successionPlanning = new URL("src/bus_transit/hr/succession/SuccessionPlanning.fxml");
            this.ess = new URL("src/bus_transit/hr/ess/EmployeeSelfService.fxml");
                        
            // HR 3
            this.shiftAndSchedule = new URL("");
            this.timeSheetManagement = new URL("");
            this.leaveManagement = new URL("");
            this.claimsAndReimbursement = new URL("");
            this.timeAndAttendance = new URL("");
            
            // HR 4
            this.payroll = new URL("");
            this.compensationPlanning = new URL("");
            this.hrAnalytics = new URL("");
            this.coreHumanCapital = new URL("");
            
            // LOGISTIC 1
            this.procurement = new URL("");
            this.assetManagement = new URL("");
            this.projectManagement = new URL("");
            this.warehousing = new URL("");
            
            // LOGISTIC 2
            this.auditManagement = new URL("");
            this.documentTracking = new URL("");
            this.vehicleReservation = new URL("");
            this.fleetManagementLog2 = new URL("");
            
            // CORE 1
            this.fleetManagementCore = new URL("");
            this.tripManagement = new URL("");
            this.routeManagement = new URL("");
            this.gpsTracking = new URL("");
            
            // CORE 2
            this.tireAndBatteryManagement = new URL("");
            this.fuelManagement = new URL("");
            this.expenseManagement = new URL("");
            this.driverManagement = new URL("");
    
            // ADMIN
            this.documentManagement = new URL("");
            this.legalManagement = new URL("");
            this.visitorManagement = new URL("");
            this.facilitiesReservation = new URL("");

            // FINANCE
            this.accounts = new URL("");
            this.collection = new URL("");
            this.disbursement = new URL("");
            this.budgetManagement = new URL("");
            this.generalLedger = new URL("");
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Directories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ObservableMap<String, URL> learningMgtReportDirecotires(){
        ObservableMap<String, URL> report = FXCollections.observableMap(new HashMap<String, URL>());        
        try {
            report.put("test result", new URL("src/bus_transit/hr/learning/report"));
            
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Directories.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return report;
    }
            
    public void loadTestViewer(){                
        String file = this.learningManagement.toString();
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
}
