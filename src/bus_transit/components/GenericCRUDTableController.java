/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class GenericCRUDTableController extends Application implements Initializable {

    @FXML private JFXTextField search;
    @FXML private JFXComboBox<String> filterBy;
    @FXML private Label resultStatus;
    @FXML private TableView<?> table;
    @FXML private ContextMenu context;
    @FXML private MenuItem add;
    @FXML private MenuItem view;
    @FXML private MenuItem edit;
    @FXML private MenuItem archive;

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    public String qry;
    public String tbl = "";
    public ObservableList<String> cols = FXCollections.observableArrayList();
    public ObservableList<String> c;
    public String colID;
    @FXML private JFXButton selectCols;
    @FXML private GridPane grpCRUD;
    @FXML private ColumnConstraints colConstrainstRight;
    @FXML private VBox vbxCRUD;
    @FXML private VBox vbxCols;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTbl("employee");
        setColID("emp_id");                        
        db.setDatabase_name("bustransit_master");
        
        if(!tbl.isEmpty()){
            setQry("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA`='"+db.getDatabase_name()+"' AND `TABLE_NAME`='"+getTbl()+"'");
            filterBy.getItems().add("None");
            db.appendToJFXComboBox(qry, filterBy);                                    
            setQry("SELECT * FROM " + getTbl());
            db.populateTable(getQry(), table);            
        }
        
        filterBy.setOnAction(e->{
            String s = filterBy.getSelectionModel().getSelectedItem().toString();
            filterBy(s);
        });     
        
        getColumnType();
        
        vbxCols.getChildren().forEach(cnsmr->{
            if(cnsmr.getTypeSelector().equals("JFXCheckBox")){
                JFXCheckBox cb = (JFXCheckBox) cnsmr;                
                cb.setOnAction(value->{
                    String t = cb.getText();
                    if(cb.isSelected()){
                        cols.add(t);
                    }else{
                        cols.remove(t);
                    }
                    queryBuilder(cols);
                });
            }            
        });  
        
        table.setOnMouseClicked(value->{
            System.out.println(table.getSelectionModel().getSelectedIndex());
        });
    }   
    
    @Override
    public void start(Stage stage) throws Exception {        
        Parent root = FXMLLoader.load(getClass().getResource("GenericCRUDTable.fxml"));
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
    private void addNew(ActionEvent event) {
    }

    @FXML
    private void viewRecord(ActionEvent event) {
    }

    @FXML
    private void editRecord(ActionEvent event) {
        
    }

    @FXML
    private void archiveRecord(ActionEvent event) {
        
    }

    private ResultSet getRecord(TableView t){
        ObservableList<String> r = (ObservableList<String>) t.getSelectionModel().getSelectedItem();            
        String id = r.get(0);        
        ResultSet rst = db.displayRecords(qry);        
        return rst;
    }
    
    /**
     * @return the qry
     */
    public String getQry() {
        return qry;
    }

    /**
     * @param qry the qry to set
     */
    public void setQry(String qry) {
        this.qry = qry;
    }

    /**
     * @return the tbl
     */
    public String getTbl() {
        return tbl;
    }

    /**
     * @param tbl the tbl to set
     */
    public void setTbl(String tbl) {
        this.tbl = tbl;
    }

    @FXML
    private void search(ActionEvent event) {
        String s = search.getText();
        qry = "SELECT * FROM "+getTbl()+" WHERE "+getColID()+" LIKE '%"+s+"%'";
        System.out.println(qry);
        db.populateTable(qry, table);
    }
        
    private void search() {
        String s = search.getText();
        qry = "SELECT * FROM "+getTbl()+" WHERE "+getColID()+" LIKE '%"+s+"%'";
        System.out.println(qry);
        db.populateTable(qry, table);
    }    
    
    private void filterBy(String f) {
        String s = search.getText();
        String si = filterBy.getSelectionModel().getSelectedItem().toLowerCase();
        if(!si.equals("none")){
            qry = "SELECT * FROM "+getTbl()+" WHERE "+f+"="+s;
            System.out.println(qry);
            db.populateTable(qry, table);
        }else{
            search();
        }                  
    }
    
    public void queryBuilder(ObservableList e){
        String f = e.toString().replace("[", "").replace("]", "");
        qry = "SELECT "+f+" FROM "+getTbl();
        db.populateTable(qry, table);
    }
    
    public void getColumnType(){
        try {
            rs = db.displayRecords("SELECT * FROM user");
            ResultSetMetaData rsmd = rs.getMetaData();
                        
//            while(rs.next()){
//                int a = rs.getRow();
//                for(int i = 0; i < a; i++){
//                    System.out.print(rsmd.getColumnName(i));
//                    System.out.print(",");
//                    System.out.print(rsmd.getColumnType(i));
//                    System.out.print(",");
//                    System.out.print(rsmd.getColumnTypeName(i));
//                    System.out.print(",");
//                    System.out.print(rsmd.getColumnLabel(i));
//                    System.out.print(",");
//                    System.out.print(rsmd.getColumnDisplaySize(i)); 
//                    System.out.print(",");
//                    System.out.println(i);
//                }                
//            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GenericCRUDTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateVBox();
    }

    /**
     * @return the colID
     */
    public String getColID() {
        return colID;
    }

    public void toggleColumnSelector(){
        double vbxColsWidth = vbxCols.getPrefWidth();
        double grpCRUDWidth = grpCRUD.getPrefWidth();
        double vbxCRUDWidth = vbxCRUD.getPrefWidth();
        
        if(vbxCols.isVisible()){
            vbxCols.setVisible(false);            
            grpCRUD.getColumnConstraints().remove(1);
            grpCRUD.getColumnConstraints().get(0).setPrefWidth(vbxCRUDWidth + vbxColsWidth);
            grpCRUD.getColumnConstraints().get(0).setMinWidth(vbxCRUDWidth + vbxColsWidth);
            grpCRUD.getColumnConstraints().get(0).setFillWidth(true);
        }else{
            vbxCols.setVisible(true);
            grpCRUD.getColumnConstraints().add(1,colConstrainstRight);
            grpCRUD.getColumnConstraints().get(0).setPrefWidth(vbxCRUDWidth - vbxColsWidth);
            grpCRUD.getColumnConstraints().get(0).setMinWidth(vbxCRUDWidth - vbxColsWidth);
            grpCRUD.getColumnConstraints().get(0).setFillWidth(true);
        }
    }
    
    private void populateVBox(){
        setQry("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA`='"+db.getDatabase_name()+"' AND `TABLE_NAME`='"+getTbl()+"'");
        rs = db.displayRecords(qry);
        JFXCheckBox cb;
        try {
            while(rs.next()){
                cb = new JFXCheckBox(rs.getString(1));
                cols.add(cb.getText());
                cb.prefWidth(300);
                cb.setSelected(true);                
                vbxCols.getChildren().add(cb);
            }
            System.out.println(cols);
        } catch (SQLException ex) {
            Logger.getLogger(GenericCRUDTableController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private VBox createColumnSelector(){
        setQry("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA`='"+db.getDatabase_name()+"' AND `TABLE_NAME`='"+getTbl()+"'");
        rs = db.displayRecords(qry);
        VBox v = new VBox();
        v.setFillWidth(true);
        v.setSpacing(10);
        JFXCheckBox cb;
        try {
            while(rs.next()){
                cb = new JFXCheckBox(rs.getString(1));
                v.getChildren().add(cb);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericCRUDTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
    /**
     * @param colID the colID to set
     */
    public void setColID(String colID) {
        this.colID = colID;
    }

    @FXML
    private void showColSelector(ActionEvent event) {
    }
    
}