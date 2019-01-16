/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class QuestionController implements Initializable {

    @FXML private Label lblNumber;
    public Text txtQuestion;
    @FXML private VBox vbxChoices;
    
    public TestViewerController testViewerController;

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;
    
    public int testId;
    public static String id;
    public static String correctAnswer = "";
    public static int nOfQ = 0;
    public static String examineeId;
    @FXML private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setQuestion(getId());
        getChoices(getId());               
    }    
            
    public void setQuestion(String id){
        String qry = "SELECT question_id, question_type, question, answer " +
                     "FROM question WHERE question_id="+id;
        
        rs = db.displayRecords(qry);
        
        lblNumber.setText(getnOfQ()+". ");
        
        try {
            if(rs.next()){                
                getTxtQuestion().setText(rs.getString("question"));
                setCorrectAnswer(rs.getString("answer"));
                getTxtQuestion().setText(rs.getString("question"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }                                
    }
    
    public void getChoices(String id){                
        q = "SELECT choice, question_id " +
            "FROM answer_meta WHERE question_id = "+id;
        
        rs = db.displayRecords(q);        
        
        ToggleGroup t = new ToggleGroup();             
        
        try {
            while(rs.next()){
                String c = rs.getString("choice");
                JFXRadioButton rb = new JFXRadioButton(rs.getString("choice"));
                rb.setToggleGroup(t);
                rb.setFont(new Font("SansSerif", 14));
                String qID = rs.getString("question_id");
                rb.setOnAction(value->{
                    String q = "UPDATE question_examinee SET answer = '"+rb.getText()+
                               "' WHERE question_id='"+id+
                               "' AND examinee_id='"+getExaminee()+
                               "' AND DATE = CURDATE();";                  
                    db.execute(q);
                });                
                vbxChoices.getChildren().add(rb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }    
    
    public void checkAnswer(String exmnee, String a, String i){        
        String q = "SELECT answer FROM question WHERE question_id="+i;        
        rs = db.displayRecords(q);        
        try {
            if(rs.next()){
                String ans = rs.getString("answer").toLowerCase();
                if(ans.equals(a.toLowerCase())){
                    System.out.println("Correct");
                    incrementScore("1");
                    //testViewerController.score++;
                }else{
//                    System.out.println("Wrong");                    
//                    dicrementScore("1");
//                    testViewerController.score--;
                }
                System.out.println("Score: "+testViewerController.score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
    
    public void dicrementScore(String examineeId){
        int s = getScore(examineeId);
        if(s != 0){
            s--;
            String q = "UPDATE test_result SET score="+s+
                    " WHERE examinee_id="+examineeId;
            db.execute(q);         
        }
    }
    
    public void incrementScore(String examineeId){
        int s = getScore(examineeId);
        if((getTestItems() != getScore(examineeId))){
            s++;
            String q = "UPDATE test_result SET score="+s+
                    " WHERE examinee_id="+examineeId;
            db.execute(q);            
        }      
    }
    
    public int getTestItems(){
        String q = "SELECT COUNT(question) AS items FROM question "+
                   "WHERE test_id = "+this.getTestId();
        
        rs = db.displayRecords(q);
        
        int r = 0;
        
        try {
            if(rs.next()){
                r = rs.getInt("items");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public int getScore(String examineeId){
        int s = 0;
        String gtScore = "SELECT COUNT(answer_remark) as 'score' FROM question_examinee WHERE answer_remark='correct' AND examinee_id="+id;
        rs = db.displayRecords(gtScore);
        try {
            if(rs.next()){
                s = rs.getInt("score");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    /**
     * @return the txtQuestion
     */
    public Text getTxtQuestion() {
        return txtQuestion;
    }

    /**
     * @param txtQuestion the txtQuestion to set
     */
    public void setTxtQuestion(Text txtQuestion) {
        this.txtQuestion = txtQuestion;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the correctAnswer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer the correctAnswer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * @return the nOfQ
     */
    public int getnOfQ() {
        return nOfQ;
    }

    /**
     * @param nOfQ the nOfQ to set
     */
    public void setnOfQ(int nOfQ) {
        this.nOfQ = nOfQ;
    }

    /**
     * @return the examinee
     */
    public String getExaminee() {
        return examineeId;
    }

    /**
     * @param aExaminee the examinee to set
     */
    public void setExaminee(String aExaminee) {
        examineeId = aExaminee;
    }

    /**
     * @return the testId
     */
    public int getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /**
     * @return the testViewerController
     */
    public TestViewerController getTestViewerController() {
        return testViewerController;
    }

    /**
     * @param testViewerController the testViewerController to set
     */
    public void setTestViewerController
        (TestViewerController testViewerController) {
        this.testViewerController = testViewerController;
    }
    public void setTestViewerController() {
        FXMLLoader l = 
                new FXMLLoader(getClass().getResource("TestViewer.fxml")); 
        this.testViewerController = l.getController();
    }
}