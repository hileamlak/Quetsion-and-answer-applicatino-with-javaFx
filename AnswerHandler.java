package QAA;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AnswerHandler {

	public String[][] userAnswerForLoadedQuetsion=new String[5000][1];
	QuetsionHandler quetsionHandler;
	AnchorPane answersolnPane;
	public static Boolean [][] checkedAnswer=new Boolean[5000][1];
	public static int rightAnswers=0;
	public static int wrongAnswers=0;
	
	public static int lastLayoutY=20,SolutionLayoutX=20,quetsionLayoutX =5;
	
	public AnswerHandler(QuetsionHandler quetsionHandler,AnchorPane quetsionPane){
		this.quetsionHandler=quetsionHandler;
		this.answersolnPane=quetsionPane;
	}
	
	public void checkButton(Button showAnswer){
		
		if(QuetsionLoader.AnsweredQuetsions==QuetsionLoader.loadedQuetsionnumber) {
			
			for(int loadedQuetsionCounter=0;loadedQuetsionCounter<QuetsionLoader.loadedQuetsionnumber;loadedQuetsionCounter++) {
					
					String userAnswers=QuetsionLoader.UserAnswers[loadedQuetsionCounter][0];			
					checkedAnswer[loadedQuetsionCounter][0]=checkAnswer(userAnswers,QuetsionLoader.loadedQuetsionA[loadedQuetsionCounter][0]);
					if(checkedAnswer[loadedQuetsionCounter][0]==true){
						rightAnswers=rightAnswers+1;
					}
					else {
						
						wrongAnswers=wrongAnswers+1;
					}
				
			}
			
			Alert alert = new Alert(Alert.AlertType.NONE,"RightAnswers: "+rightAnswers+"\nRongAnswers: "+wrongAnswers,ButtonType.OK); 
			alert.setTitle("Result");
			alert.setHeaderText("You can See the answer and solution for better learning experiances");
	        alert.showAndWait();
	        showAnswer.setDisable(false);
		}
		else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION,"Should Answer All quetsions Before Checking for Solution",ButtonType.OK); 
			alert.setTitle("Answer All Quetsions first");
	        alert.showAndWait();
		}
	}
	protected Boolean checkAnswer(String userAnswer, String correctAnswer) {
		if(userAnswer.equals(correctAnswer)) {		
			return true;
		}
		else {
			return false;
		}
		
	}

	public void showSolution(String solutionFor) {
		answersolnPane.getChildren().clear();
		Label title=new Label("Physics Problems");
		title.setLayoutX(0);
		title.setLayoutY(0);
		title.setStyle("-fx-text-fill:red;-fx-font-size:15;");
		answersolnPane.getChildren().add(title);
		
		loadEverything();
		
	}

	private void loadEverything() {
		for (int quetsionCounter=0;quetsionCounter<QuetsionLoader.loadedQuetsionnumber;quetsionCounter++) {
			if(lastLayoutY>answersolnPane.getHeight()) {
				changePaneHeight(answersolnPane,answersolnPane.getHeight()+quetsionCounter*10);
			}
			loadQuetsion(QuetsionLoader.loadedQuetsion[quetsionCounter]);
			loadQuetsionImg(QuetsionLoader.loadedQuetsionI[quetsionCounter][0]);
			loadUserAnswer(QuetsionLoader.UserAnswers[quetsionCounter][0]);
			loadCorrectAnswer(QuetsionLoader.loadedQuetsionA[quetsionCounter][0]);
			
			if(checkedAnswer[quetsionCounter][0]) {
				answersolnPane.getChildren().add(loadImg("correct.png"));
			}
			else {
				answersolnPane.getChildren().add(loadImg("wrong.png"));
			}
			loadSolns(QuetsionLoader.loadedQuetsionS[quetsionCounter]);			
			}

	}
	
	private void changePaneHeight(AnchorPane pane, double newHeight) {
		pane.setMinHeight(newHeight);
		pane.prefHeight(newHeight);
		pane.setMaxHeight(newHeight);
		
	}
	
	private void loadQuetsion(String loadedQuetsion) {
		
		Label Quetsion=prepareQuetsion("=> "+loadedQuetsion);
		
		Quetsion.setLayoutX(quetsionLayoutX);
		lastLayoutY=lastLayoutY+20;
		Quetsion.setLayoutY(lastLayoutY);
		
		answersolnPane.getChildren().add(Quetsion);
		
	}
	private void loadQuetsionImg(String quetsionImage) {
		if(!(quetsionImage==null)) {
			answersolnPane.getChildren().add(loadImg(quetsionImage));
		}
		
	}
	private Label prepareQuetsion(String label) {
		Label quetsion=new Label(label);
		quetsion.setStyle("-fx-text-fill:#ffa602;-fx-font-family:Monospaced;-fx-font-size:16;");
		return quetsion;
	}
	
	private void loadUserAnswer(String userAnswer) {
		Label UserAnswer=prepareUserAnswer(userAnswer);
		
		UserAnswer.setLayoutX(SolutionLayoutX);
		lastLayoutY=lastLayoutY+20;
		UserAnswer.setLayoutY(lastLayoutY);
		
		answersolnPane.getChildren().add(UserAnswer);
		
	}
	private Label prepareUserAnswer(String label) {
		Label userAnswer=new Label("Your Answer> "+label);
		//if the answer is correct make the color green or make it red
		userAnswer.setStyle("-fx-text-fill:green;-fx-font-family:Monospaced;-fx-font-size:14;");
		return userAnswer;
	}
	
	
	private void loadCorrectAnswer(String userAnswer) {
		Label UserAnswer=prepareCorrectAnswer(userAnswer);
		
		UserAnswer.setLayoutX(SolutionLayoutX);
		lastLayoutY=lastLayoutY+20;
		UserAnswer.setLayoutY(lastLayoutY);
		
		answersolnPane.getChildren().add(UserAnswer);
		
	}
	private Label prepareCorrectAnswer(String label) {
		Label userAnswer=new Label("Correct Answer> "+label);
		userAnswer.setStyle("-fx-text-fill:red;-fx-font-family:Monospaced;-fx-font-size:14;");
		return userAnswer;
	}

	private void loadSolns(String soln[]) {
		
		for (int solnCounter=0;solnCounter<5;solnCounter++) {
			
			if(soln[solnCounter].equals("")) {
				break;

			}
			else {
				if(soln[solnCounter].charAt(0)=='*') {
					if(loadImg(soln[solnCounter].substring(1))!=null) {
						answersolnPane.getChildren().add(loadImg(soln[solnCounter].substring(1)));
					}
					
				}
				else {
					Label solnLabel;
					if(solnCounter==0) {solnLabel=prepareSoln("Solution:=="+soln[solnCounter]);}
					else {
						solnLabel=prepareSoln("         =="+soln[solnCounter]);
						
						
					}
					solnLabel.setLayoutX(SolutionLayoutX+5);
					lastLayoutY=lastLayoutY+10;
					solnLabel.setLayoutY(lastLayoutY);
					
					answersolnPane.getChildren().add(solnLabel);
				}
							
				
			}
			
		}
		
	
	}
	private Label prepareSoln(String label) {
		Label soln=new Label(label);
		soln.setStyle("-fx-text-fill:Orange;-fx-font-family:Monospaced;-fx-font-size:14;");
		return soln;
	}	
	

	private ImageView loadImg(String absolutePath) {
	try {
	Image img=new Image(getClass().getResourceAsStream(absolutePath));
	ImageView imgView=new ImageView(img);
	
	imgView.setLayoutX(SolutionLayoutX+20);
	lastLayoutY=lastLayoutY+30;
	imgView.setLayoutY(lastLayoutY);
	lastLayoutY=lastLayoutY+10;
	imgView.setFitWidth(20);
	imgView.setFitHeight(20);
	
	//set the imgview Properties like width and layout
	
	return imgView;
	}catch(Exception e) {
		System.out.println("No such image path cant load "+absolutePath);
		return null;
		}
	
	}

}











