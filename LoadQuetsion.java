package QAA;

import java.io.File;

import javafx.scene.layout.AnchorPane;

public class LoadQuetsion extends QuetsionLoader {

	public LoadQuetsion(AnchorPane quetsionPane, File toRead) {
		super(quetsionPane, toRead);
		prepareAnswerChecker(quetsionPane);
	}
	public void clear() {
		
	}
	/*
	public void AnsChecker(String PhysicsUserAnswer,int quetsion) {
		checkAns(PhysicsUserAnswer,quetsion);
	}*/
	private void prepareAnswerChecker(AnchorPane quetsionPane) {
		//add the check answer button to the quetsion pane
	}
	public QuetsionHandler getQH() {
		return getQhandler();
	}
	
}
