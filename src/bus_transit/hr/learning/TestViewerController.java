package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestViewerController extends Application implements Initializable {
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;
    
    Timeline timeLine = new Timeline();
    public String applicantId;
    public String examineeId;
    public VBox vbxQuestionContainer;    
    public static int score = 0;
    
    public static String testId;
    public int duration = 0;
    
    @FXML private AnchorPane heading;
    @FXML private Label lblTesTitle;
    @FXML private Label lblExaminee;
    @FXML private Label lblRemaining;
    @FXML private JFXButton btnSubmit;
    @FXML private StackPane stackPane;

    private static Integer TEST_DURATION = 1; 
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        getExamineeInfo();        
        
        btnSubmit.setOnAction((event) -> {            
            try {
                String q =  "SELECT question.question_id, question.answer "+
                            "AS 'correct_answer', question_examinee.answer "+
                            "AS 'examinee_answer', "+
                            "question_examinee.answer_remark "+
                            "AS 'remark' FROM question "+
                            "INNER JOIN question_examinee "+
                            "ON question.question_id = "+
                            "question_examinee.question_id "+
                            "AND question_examinee.examinee_id="+getExamineeId();
                
                rs = db.displayRecords(q);
                
                while(rs.next()){
                    String cAnswer = rs.getString("correct_answer").toLowerCase();
                    String eAnswer = rs.getString("examinee_answer").toLowerCase();
                    
                    if(!eAnswer.isEmpty()){
                        String remark = "";
                        if(cAnswer.equals(eAnswer)){
                            remark = "correct";
                        }else{
                            remark = "wrong";
                        }
                        String qr = "UPDATE question_examinee "+
                                    "SET answer_remark = '"+remark+
                                    "' WHERE question_id='"+
                                    rs.getString("question_id")+
                                    "' AND examinee_id='"+examineeId+
                                    "' AND DATE = CURDATE();";                                        

                        System.out.println(examineeId);
                        db.execute(qr);
                        timeLine.stop();                      
                    }else{
                        System.out.println("Done");
                    }
                }
                submit();
            } catch (SQLException ex) {
                Logger.getLogger(TestViewerController.class.getName())
                      .log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TestViewerController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        });        
        
    }    
    
    
    // a function to get exminee info
    // modal use by TestViewer
    public void getExamineeInfo(){
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("Examinee Info"));
        
        VBox vbx = new VBox();        
        
        JFXTextField applicantId = new JFXTextField();
        applicantId.setPromptText("Applicant ID: ");
        vbx.getChildren().add(0, applicantId);
        
//        JFXTextField lastname = new JFXTextField();
//        lastname.setPromptText("LastName");        
//        vbx.getChildren().add(1, lastname);
                
        dialog.setBody(vbx);
        
        JFXDialog dlg = new JFXDialog(stackPane,
                                      dialog,
                                      JFXDialog.DialogTransition.CENTER);
        
        JFXButton btnOK = new JFXButton("OK");
        
        btnOK.setOnAction((event) -> {
            String f = applicantId.getText(); 
//            String l = lastname.getText();
            if(!f.isEmpty()){
                try {
                    String q = "INSERT INTO examinee (examinee_id,  test_date) "+
                            "VALUES ('"+f+"',CURDATE())";
                    db.execute(q);
                    //lblExaminee.setText(l+", "+f);
                    
                    String qr = "SELECT MAX(examinee_id) AS 'id' FROM examinee";
                    rs = db.displayRecords(qr);
                    if(rs.next()){
                        setExamineeId(rs.getString("id"));                        
                        System.out.println("Get from Dialog Examinee ID:"+getExamineeId());
                    }
                    
                    populateQuestionContainer();
                    runTimer(TEST_DURATION);
                    
                    dlg.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestViewerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                dialog.setHeading(new Text("Please enter required data"));
            }
        });
        dialog.setActions(btnOK);                
        dlg.show();
    }
    
    public void showEndDialog(){
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("Examinee Info"));
                                
        dialog.setBody(new Text("Time is up!"));
        
        JFXDialog dlg = new JFXDialog(stackPane,dialog,JFXDialog.DialogTransition.CENTER);
        
        JFXButton btnOK = new JFXButton("OK");
        btnOK.setOnAction((event) -> {          
            dlg.close();
        });
        
        dialog.setActions(btnOK);                
        dlg.show();
    }    
    
    /**
     * Load questions to TestViewer
     * and create a copy of questions on question_examinee table
     * on the database base on examinee_id
     */
    public void populateQuestionContainer() {
        System.out.println("from testviewercontroller:"+getTestId());
        //q = "SELECT * FROM question, test WHERE question.test_id="+getTestId();
        q = "SELECT * FROM test INNER JOIN question ON test.test_id = question.test_id AND test.test_id ='"+getTestId()+"' ORDER BY RAND()";
        rs = db.displayRecords(q);        
        int i = 1;        
        ObservableList<FlowPane> p = null;
        try {
            while(rs.next()){                
                FXMLLoader l = 
                        new FXMLLoader(getClass().getResource("Question.fxml"));                                                            
                QuestionController qCntrl = new QuestionController();                
                qCntrl.setId(rs.getString("question_id"));                                               
                qCntrl.setnOfQ(i++);
                qCntrl.setTestId(rs.getInt("test_id"));
                qCntrl.setExaminee(getExamineeId());
                FlowPane pane = l.load();                
                l.setController(qCntrl);    
                TEST_DURATION = rs.getInt("duration");
                vbxQuestionContainer.getChildren().addAll(pane);
                
                q = "INSERT INTO question_examinee "+
                    "(examinee_id, question_id, question, test_id, DATE) "+
                    "VALUES ('"+
                    examineeId+"','"+
                    rs.getString("question_id")+"','"+
                    rs.getString("question")+"','"+
                    rs.getString("test_id")+"', CURDATE())";               
                db.execute(q);          
            }                        
        } catch (SQLException ex) {
            Logger.getLogger(TestViewerController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestViewerController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * add new record to test_result table on the database
     * 
     */

    
    private int getItem(){
        int item = 0;
        String q = "SELECT COUNT(answer_remark) AS 'item' "
                 + "FROM question_examinee WHERE examinee_id="+examineeId;
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                item = rs.getInt("item");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestViewerController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } 
        return item;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("TestViewer.fxml"));        
        Scene scene = new Scene(root);        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }     

    public GridPane createQuestionComponent(String questionId){              
        GridPane gridPane = new GridPane();
        gridPane.prefWidth(900);
        gridPane.prefHeight(220);
        
        int row = 0;
        int col = 0;                       
                        
        Label lblNumber = new Label();
        gridPane.add(lblNumber, col, row); // 0,0
        
        Text question = new Text(getQuestion(questionId));        
        gridPane.add(question, col++, row); // 1,0
        
//        ImageView img = getImage(questionId);
//        gridPane.add(img, col, row,1, 2);
        
        VBox choices = new VBox();
        choices.getChildren().addAll(getChoices(questionId));        
        gridPane.add(choices, col, row++);        
        return gridPane;        
    }
    
    public String getQuestion(String questionId){
        String q = "SELECT question FROM question "+testId+
                   "WHERE question_id="+questionId;
        rs = db.displayRecords(q);
        String question="";
        try {
            if(rs.next()){
                question = rs.getString("question");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestViewerController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        
        return question;
    }
    
    public ArrayList<JFXRadioButton> getChoices(String questionId){
        String q = "SELECT choice FROM answer_meta " +
                   "WHERE question_id='"+questionId+"' ORDER BY RAND()";
        rs = db.displayRecords(q);
        ArrayList<JFXRadioButton> choices = new ArrayList();
        try {
            while(rs.next()){
                //JFXRadioButton rb = new JFXRadioButton(rs.getString("choice"));
                choices.add(new JFXRadioButton(rs.getString("choice")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestViewerController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }                
        return choices;
    }
    
    public ImageView getImage(String questionId){
        String q = "SELECT image FROM question WHERE question_id"+questionId;
        rs = db.displayRecords(q);
        ImageView image = null;
        
        try {
            if(rs.next()){
                image = new ImageView(rs.getString("image"));                
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestViewerController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }                
        return image;
    }
    
     

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the applicantId
     */
    public String getApplicantId() {
        return applicantId;
    }

    /**
     * @param applicantId the applicantId to set
     */
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
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
     * @return the examineeId
     */
    public String getExamineeId() {
        return examineeId;
    }

    /**
     * @param examineeId the examineeId to set
     */
    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }  
        
    /**     
     * @param duration represents minutes
     */
    
    private void runTimer(Integer duration){                 
        duration = (duration * 60) + 1;        
        IntegerProperty timeSeconds = new SimpleIntegerProperty(duration);        
        if (timeLine != null) {
            timeLine.stop();
        }
        
        timeSeconds.set(duration);        
        
        timeLine.getKeyFrames().add(
                new KeyFrame(Duration.seconds(duration+1),
                new KeyValue(timeSeconds, 0)));  
        
        timeSeconds.addListener(new ChangeListener(){          
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {   
                int h = (timeSeconds.getValue() / 60) / 60;
                int m = (timeSeconds.getValue() / 60) % 60;
                int s = timeSeconds.getValue() % 60;

                if(s == 0 && m == 0 && h == 0){
                    System.out.println("Timer end");
                }
                
                // use this if you want to display current time in a single string
//                DateFormat t = new SimpleDateFormat("hh:mm:ss");
//                int s = LocalDateTime.now().getSecond();
//                int m = LocalDateTime.now().getMinute();
//                int h = LocalDateTime.now().getHour();                                 
                String time  = String.format("%02d:%02d:%02d",h,m,s);
                lblRemaining.setText("Remaing: "+ time);                
            }        
        });        
        timeLine.play();
    }       
    
    @FXML
    private void submit() {        
        String id = getExamineeId();
        
        System.out.println("Examinee ID: "+id);
        System.out.println("Test ID: "+testId);
        
        String gScore = "SELECT COUNT(answer_remark) AS 'score' "
                + "FROM question_examinee "
                + "WHERE examinee_id='"+id
                + "' AND answer_remark='correct'";
        
        rs = db.displayRecords(gScore);
        try {
            if(rs.next()){
                int scr = rs.getInt("score");
                Double passing = Math.ceil(getItem() * .75);
                System.out.println("Passing: "+passing);
                String remarks;
                if(scr >= passing){
                    remarks = "passed";
                }else{
                    remarks = "failed";
                }                
                String q = "INSERT INTO test_result (test_id, examinee_id, score, remarks) "
                        + "VALUES('"+testId+"','"+examineeId+"','"+scr+"','"+remarks+"')";  
                db.execute(q);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestViewerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        timeLine.stop();
        System.exit(1);
    }
}
