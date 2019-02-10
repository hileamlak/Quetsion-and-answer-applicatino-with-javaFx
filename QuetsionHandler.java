package QAA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class QuetsionHandler {
	
	//private static final Exception NoTypeIdentfierError = new Exception("No type identifier has been found so the identifier has been set to 0");
	private static final Exception NoChoiceError = new Exception("No Choice exist though the choice marker exists");
	private static final Exception NoImageError = new Exception("No Image exist though the image marker exists");
	
	private final char QUETSION_IDENTIFIER='^'; 
	private final char ANSWER_IDENTIFIER='@';
	private final char QUETSION_TYPE_IDENTIFIER='%';
	private final char CHOICE_IDENTIFIER='-';
	private final char IMAGE_IDENTIFIER='*';
	private final char SOLUTION_BIGNING_IDENTIFIER='<';
	private final char SOLUTION_ENDING_IDENTIFIER='>';
	private final char QUETSION_ENDGING_IDENTIFIER=';';
	private final char COMENT_IDENTIFIER='/';
	
	private String[] choices=new String[5]; 
	private String[] choicesimg=new String[5];
	private String[] quetsions=new String[50];
	private String answer="";
	private int Q_type=0;
				//S I  
	private String[] solution=new String[5];
	private String[] solutionimg=new String[5];
	private String[] quetsionImages=new String[3];
				//Q I C A S SI	
	private String[][] Q_A_S=new String[50][5];
	private String[][] Q_A_A=new String[50][1];
	private String[][] Q_A_C=new String[50][5];
	private String[][] Q_A_CI=new String[50][5];
	private String[][] Q_A_I=new String[50][1];
	private String[][] Q_A_SI=new String[50][5];
	private String[][] Q_A_T=new String[50][1];
	private String[] Quetsion=new String[50];
	
	public int QuetsionNumber=0;
	
	private Scanner readScanner;
	
	public QuetsionHandler(File quetsionFile ){
		readScanner=readFile(quetsionFile);
		parseFile(readScanner);
	}
	
	
	private Scanner readFile(File quetsionFile) {
		FileInputStream file;
		try {
			file = new FileInputStream(quetsionFile);
			Scanner read = new Scanner(file);
			return read;
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return null;
			
		}
		
	}
	private void parseFile(Scanner reader) {

		String string;
		int choice=0;
		int soln=0;
		int quetsionimg=0;
		int quetsionNumber=0;
		
		
		while (reader.hasNext()) {
			string = reader.nextLine();	
			outerloop:
			if (string.length()==0) {continue;}
			else {
				switch (string.toString().trim().charAt(0)) {
					
				    case  QUETSION_IDENTIFIER:
				    	//System.out.println(QUETSION_IDENTIFIER);
						
				   		quetsions[quetsionNumber]=string.toString().substring(1);
				     	//if the quetsion has image deal with it before or after the quetsion in the Image Identifier Case
				    	break;
					
					case ANSWER_IDENTIFIER:
						//System.out.println(ANSWER_IDENTIFIER);
						answer=string.toString().trim().substring(1);		    	
						break;
					
					case QUETSION_TYPE_IDENTIFIER:
						//System.out.println(QUETSION_TYPE_IDENTIFIER);
						try{

							Q_type=Integer.parseInt(string.toString().trim().substring(1));
								
							
							
						}catch(Exception e) {System.out.println(e);}
		    	
				    	break;
					
					case CHOICE_IDENTIFIER:
						//System.out.println(CHOICE_IDENTIFIER);
						try{
							if(string.toString().trim().length()>1) {
						    	if(string.toString().trim().charAt(1)==IMAGE_IDENTIFIER) {
						    		choices[choice]=string.toString().substring(1);
									choicesimg[choice]=string.toString().substring(2);
								}
						    	else {
						    		choices[choice]=string.toString().trim().substring(1);
									choicesimg[choice]="";
						    	}
						    	choice=choice+1;
							}
							else {throw NoChoiceError;}
						}catch(Exception e) {System.out.println(e);}
						break;
					
					case IMAGE_IDENTIFIER:
						//System.out.println(IMAGE_IDENTIFIER);
						try {
							if(string.toString().trim().length()>1) {
								quetsionImages[quetsionimg]=string.toString().trim().substring(1);
								quetsionimg=quetsionimg+1;
							}
							else {
								throw NoImageError;
							}
						}catch(Exception e) {System.out.println(e);}
				    	
						break;
					
					case SOLUTION_BIGNING_IDENTIFIER:
						//System.out.println(SOLUTION_BIGNING_IDENTIFIER);						
						String solutionString;
						while (reader.hasNext()) {
							
							solutionString=reader.nextLine().toString();
							
							if(solutionString.length()==0) {continue;}
							
							else {
								switch (solutionString.trim().charAt(0)){
									case SOLUTION_ENDING_IDENTIFIER:
										//System.out.println(SOLUTION_ENDING_IDENTIFIER);
										//System.out.println("Solution Endeed");
										//nothing is needed to be done her
										break outerloop;
									default:
										//System.out.println("SOLUTION");
										if(solutionString.trim().charAt(0)==IMAGE_IDENTIFIER) {
											solution[soln]="";
											solutionimg[soln]=solutionString.trim().substring(1);
									    	
									   }
										else {
											solution[soln]=solutionString.trim();
											solutionimg[soln]="";
										}
										soln=soln+1;
										break;
								}
								
							}
						}					
						
						break;
					
					case QUETSION_ENDGING_IDENTIFIER:
						//System.out.println(";");
						while(choice<5) {
							choices[choice]="";
							choicesimg[choice]="";
							choice=choice+1;
						}
						while(soln<5) {
							solution[soln]="";
							solutionimg[soln]="";
							soln=soln+1;
						}
						
						if(Q_type==0) {
							addToQ_A_S(quetsions[quetsionNumber],quetsionImages, choices,choicesimg,answer,solution,solutionimg);
						}
						else if(Q_type==1) {
							addToQ_A_S(quetsions[quetsionNumber],quetsionImages, answer,solution,solutionimg);
						}
						
						quetsionNumber=quetsionNumber+1;
						answer="";
						Q_type=0;
						
						choice=0;
						soln=0;	
						
						break;
					case COMENT_IDENTIFIER:
						//we dont want to do nothing with the comment
						break;
					default:
						//anything else could be put by a mistake so we dont want to concern about any thing else for now
						break;
					
				}
				
			
		}
		
		}
	}
	
	private void addToQ_A_S(String quetsions2, String[] quetsionImages2, String[] choices2, String[] choicesimg2, String answer2, String[] solution2, String[] solutionimg2) {
		Q_A_T[QuetsionNumber][0]="0";
		Quetsion[QuetsionNumber]=quetsions2;
		Q_A_A[QuetsionNumber][0]=answer2;
		Q_A_I[QuetsionNumber][0]=quetsionImages2[0];
		
		for(int choicesCounter=0;choicesCounter<5;choicesCounter++) {
			Q_A_C[QuetsionNumber][choicesCounter]=choices2[choicesCounter];
			Q_A_CI[QuetsionNumber][choicesCounter]=choicesimg2[choicesCounter];
		}
		
		for(int solnCounter=0;solnCounter<5;solnCounter++) {
			Q_A_S[QuetsionNumber][solnCounter]=solution2[solnCounter];
			Q_A_SI[QuetsionNumber][solnCounter]=solutionimg2[solnCounter];
		}
		QuetsionNumber=QuetsionNumber+1;
		
		
		
		
		
	}
	private void addToQ_A_S(String quetsions2, String[] quetsionImages2, String answer2, String[] solution2,String[] solutionimg2) {
		Q_A_T[QuetsionNumber][0]="1";
		Quetsion[QuetsionNumber]=quetsions2;
		Q_A_A[QuetsionNumber][0]=answer2;
		Q_A_I[QuetsionNumber][0]=quetsionImages2[0];
		
		
		
		for(int solnCounter=0;solnCounter<5;solnCounter++) {
			Q_A_S[QuetsionNumber][solnCounter]=solution2[solnCounter];
			Q_A_SI[QuetsionNumber][solnCounter]=solutionimg2[solnCounter];
		}
		QuetsionNumber=QuetsionNumber+1;
		
	}

	//getter functions
	public String[][] getQ_A_S() {
		return Q_A_S;
	}
    public String[][] getQ_A_A() {
		return Q_A_A;
	}
    public String[][] getQ_A_C() {
		return Q_A_C;
	}
    public String[][] getQ_A_CI() {
		return Q_A_CI;
	}
    public String[][] getQ_A_I() {
		return Q_A_I;
	}
    public String[][] getQ_A_SI() {
		return Q_A_SI;
	}
    public String[][] getQ_A_T() { 	
		return Q_A_T;
	}
	public String[] getQuetsion() {
		return Quetsion;
	}


	
	 
}
