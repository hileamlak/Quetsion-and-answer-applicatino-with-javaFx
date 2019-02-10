package QAA;


import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class QuetsionLoader  {
	
	private AnchorPane quetsionPane;
	public static String[] loadedQuetsion=new String[5000];
	public static String[][] loadedQuetsionC=new String[5000][5];
	public static String[][] loadedQuetsionA=new String[5000][1];
	public static String[][] loadedQuetsionS=new String[5000][5];
	public static String[][] loadedQuetsionI=new String[5000][1];
	public static String[][] UserAnswers=new String[5000][1];
	
	
	public static int loadedQuetsionnumber=0;
	public static int AnsweredQuetsions=0;
	
	//Delete the static method and see the big difference it makes
	//the static method enabels the program to keep track of the variab;es even after the object is destroyed
	public static double quetsionLayoutX =5,LastLayoutY=20, choiceLayoutX =30;
	
	QuetsionHandler quetsionHandler;
	public QuetsionLoader(AnchorPane quetsionPane, File toRead){
		this.quetsionPane=quetsionPane;
		this.quetsionHandler=new QuetsionHandler(toRead);
		
		loadEveryThing(quetsionHandler);
	}
	
	private void loadEveryThing(QuetsionHandler quetsionHandler) {
		
		for (int quetsionCounter=0;quetsionCounter<quetsionHandler.QuetsionNumber;quetsionCounter++) {
			if(LastLayoutY>quetsionPane.getHeight()) {
				changePaneHeight(quetsionPane,quetsionPane.getHeight()+loadedQuetsionnumber*10);
			}
			loadQuetsionImg(quetsionHandler.getQ_A_I()[quetsionCounter][0]);
			loadQuetsion(quetsionHandler.getQuetsion()[quetsionCounter],quetsionCounter);
			if(quetsionHandler.getQ_A_T()[quetsionCounter][0].equals("0")) {
				loadChoice(quetsionHandler.getQ_A_C()[quetsionCounter],quetsionHandler.getQ_A_CI()[quetsionCounter]);
			}
			else if(quetsionHandler.getQ_A_T()[quetsionCounter][0].equals("1")) {
				prepareForShortAnswer(loadedQuetsionnumber );
			}
			loadAnswer(quetsionHandler.getQ_A_A()[quetsionCounter][0]);
			loadSoln(quetsionHandler.getQ_A_S()[quetsionCounter],quetsionHandler.getQ_A_SI()[quetsionCounter]);
			loadedQuetsionnumber=loadedQuetsionnumber+1;	
		}
	}
	
	private void loadAnswer(String answer) {
		loadedQuetsionA[loadedQuetsionnumber][0]=answer;
		
	}

	private void changePaneHeight(AnchorPane quetsionPane, double newHeight) {
		quetsionPane.setMinHeight(newHeight);
		quetsionPane.prefHeight(newHeight);
		quetsionPane.setMaxHeight(newHeight);
		
	}

	
	private void loadQuetsion(String quetsion,int index) {
		//if the quetsion string is a little bit long use to labels to display it in different Labels
		loadedQuetsion[loadedQuetsionnumber]=quetsion;
		Label Quetsion=prepareQuetsion("=> "+quetsion);
		
		Quetsion.setLayoutX(quetsionLayoutX);
		LastLayoutY=LastLayoutY+20;
		Quetsion.setLayoutY(LastLayoutY);
		
		quetsionPane.getChildren().add(Quetsion);
	}
	private void loadQuetsionImg(String quetsionImage) {
		loadedQuetsionI[loadedQuetsionnumber][0]=quetsionImage;
		if(!(quetsionImage==null)) {
			quetsionPane.getChildren().add(loadImg(quetsionImage));
		}
		
	}
	private void loadChoice(String choice[],String choiceImg[]) {
		
		for (int choiceCounter=0;choiceCounter<5;choiceCounter++) {
			
			if(choice[choiceCounter].equals("")) {
				if(choiceImg[choiceCounter].equals("")) {
					break;
				}
				else {
					//give the radio buttons an id that can be used for a later application in the answer checking proccess
					RadioButton choiceButton=new RadioButton();
					choiceButton.setId(Integer.toString(loadedQuetsionnumber)+Integer.toString(choiceCounter));
					choiceButton.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							//make the other radio buttons with the same question disable
							choiceButton.setSelected(true);
							AnsweredQuetsions=AnsweredQuetsions+1;
							UserAnswers[Integer.parseInt(choiceButton.getId().substring(0, 1))][0]=loadedQuetsionC[Integer.parseInt(choiceButton.getId().substring(0, 1))][Integer.parseInt(choiceButton.getId().substring(1, 2))];
							for(Node childrenNodes:quetsionPane.getChildren()) {
								try {
								if(childrenNodes.getId().substring(0, 1).equals(choiceButton.getId().substring(0, 1))) {
									
									childrenNodes.setDisable(true);
									//System.out.println(loadedQuetsionC[Integer.parseInt(choiceButton.getId().substring(0, 1))][Integer.parseInt(choiceButton.getId().substring(1, 2))]);
									
								}
								}catch(Exception e) {}
							}
							
						}
						
					});
					choiceButton.setLayoutX(choiceLayoutX);
					LastLayoutY=LastLayoutY+20;
					choiceButton.setLayoutY(LastLayoutY);
					quetsionPane.getChildren().add(choiceButton);
					
					quetsionPane.getChildren().add(loadImg(choiceImg[choiceCounter]));
					loadedQuetsionC[loadedQuetsionnumber][choiceCounter]=choiceImg[choiceCounter];
				}
			}
			else {
				RadioButton choiceButton=new RadioButton();
				choiceButton.setId(Integer.toString(loadedQuetsionnumber)+Integer.toString(choiceCounter));
				choiceButton.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						//make the other radio buttons with the same question disable
						choiceButton.setSelected(true);
						AnsweredQuetsions=AnsweredQuetsions+1;
						UserAnswers[Integer.parseInt(choiceButton.getId().substring(0, 1))][0]=loadedQuetsionC[Integer.parseInt(choiceButton.getId().substring(0, 1))][Integer.parseInt(choiceButton.getId().substring(1, 2))];
						for(Node childrenNodes:quetsionPane.getChildren()) {
							try {
							if(childrenNodes.getId().substring(0, 1).equals(choiceButton.getId().substring(0, 1))) {
								
								childrenNodes.setDisable(true);						
								//System.out.println(loadedQuetsionC[Integer.parseInt(choiceButton.getId().substring(0, 1))][Integer.parseInt(choiceButton.getId().substring(1, 2))]);
								
							}
							}catch(Exception e) {}
						}
						
					}
					
				});
				choiceButton.setLayoutX(choiceLayoutX);
				LastLayoutY=LastLayoutY+20;
				choiceButton.setLayoutY(LastLayoutY);
				quetsionPane.getChildren().add(choiceButton);
				
				Label Choice=prepareChoice(choice[choiceCounter]);
				
				Choice.setLayoutX(choiceLayoutX+20);
				Choice.setLayoutY(LastLayoutY);
				LastLayoutY=LastLayoutY+20;
				
				quetsionPane.getChildren().add(Choice);
				loadedQuetsionC[loadedQuetsionnumber][choiceCounter]=choice[choiceCounter];
			}
			
		}
		
		
	}
	
	private ImageView loadImg(String absolutePath) {
		Image img=new Image(getClass().getResourceAsStream(absolutePath));
		ImageView imgView=new ImageView(img);
		
		imgView.setLayoutX(choiceLayoutX+20);
		imgView.setLayoutY(LastLayoutY);
		LastLayoutY=LastLayoutY+40;
		imgView.setFitWidth(20);
		imgView.setFitHeight(20);
		
		//set the imgview Properties like width and layout
		
		return imgView;
	}
	private void prepareForShortAnswer(int index) {
		//give it id for later usage
		TextField shortAnswer=new TextField();/*
		shortAnswer.textProperty().addListener((observable,oldValue,newValue)->{
			if(oldValue.equals("")) {
				if(!newValue.equals("")) {
					
					shortAnswer.setEditable(false);
					AnsweredQuetsions=AnsweredQuetsions+1;
					UserAnswers[loadedQuetsions][0]=newValue;
					
				}
			}
		});*/
		shortAnswer.focusedProperty().addListener(new ChangeListener<Boolean>() {
			
			@Override
			public void changed(ObservableValue<? extends Boolean> observable2, Boolean oldValue2, Boolean newValue2) {
				if(oldValue2) {
					if(!newValue2) {
						if(!shortAnswer.getText().equals("")){
							UserAnswers[index][0]=shortAnswer.getText();
							AnsweredQuetsions=AnsweredQuetsions+1;
							shortAnswer.setEditable(false);
							shortAnswer.setDisable(true);
							
							
							
							
						}
					}
				}
				else {
					//outofFocus
					
				}
				
			}
			
		});
		LastLayoutY=LastLayoutY+20;
		shortAnswer.setLayoutY(LastLayoutY);
		shortAnswer.setLayoutX(choiceLayoutX);
		LastLayoutY=LastLayoutY+10;
		shortAnswer.setPrefWidth(100);
		quetsionPane.getChildren().add(shortAnswer);
		
	}
	
	private Label prepareQuetsion(String label) {
		Label quetsion=new Label(label);
		quetsion.setStyle("-fx-text-fill:#ffa602;-fx-font-family:Monospaced;-fx-font-size:16;");
		return quetsion;
	}
	private Label prepareChoice(String label) {
		Label choice=new Label(label);
		choice.setStyle("-fx-text-fill:white;-fx-font-family:Monospaced;-fx-font-size:14;");
		return choice;
	}
	public QuetsionHandler getQhandler() {
		return quetsionHandler;
	}
	
	private void loadSoln(String soln[],String solnImg[]) {
		
		for (int solnCounter=0;solnCounter<5;solnCounter++) {
			
			if(soln[solnCounter].equals("")) {
				if(solnImg[solnCounter].equals("")) {
					loadedQuetsionS[loadedQuetsionnumber][solnCounter]="";
					break;
				}
				else {
					loadedQuetsionS[loadedQuetsionnumber][solnCounter]="*"+solnImg[solnCounter];
				}
			}
			else {
				loadedQuetsionS[loadedQuetsionnumber][solnCounter]=soln[solnCounter];
			}
			
		}
		
		
	}
	
	/*
	public Boolean checkAns(String Answer,int question) {
		return checkAnswer(Answer,quetsionHandler.getQ_A_A()[question][0]);
	}*/
	
	
}
