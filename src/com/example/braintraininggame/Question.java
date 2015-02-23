package com.example.braintraininggame;

import java.util.Random;

// This class creates the questions and the answers
public class Question{
	
	// The values for the question
	public int[] questionValues;
	// The answer to the question
	public int answer;
	//The operators for the question
	public String[] operators;
	// The overall score for these questions
	public static int points = 0;
	
	// The time it took for the user to answer it
	private int timeItTook;
	
	public Question(int numberOfValues){
		questionValues = new int[numberOfValues];
		operators = new String[numberOfValues - 1];
	}
	
	// Creates the question
	public void createQuestion(){
		
		Random random = new Random();
		
		for(int i = 0; i<questionValues.length;i++){
            questionValues[i] = random.nextInt(100) + 1;
        }
		
		for(int i = 0; i < operators.length; i++){
            int placeHolder = random.nextInt(4);
            if(placeHolder == 0) operators[i] = "+";
            if(placeHolder == 1) operators[i] = "-";
            if(placeHolder == 2) operators[i] = "*";
            if(placeHolder == 3) operators[i] = "/";
        }
		
		getFinalAnswer();

	}
	
	// Calculates the answer
	private void getFinalAnswer(){
		answer = 0;
    	answer += questionValues[0];
        for(int i = 0; i < operators.length; i++){
        	if(operators[i] == "+"){
        		answer += questionValues[i+1];
        	} else if (operators[i] == "-"){
        		answer -= questionValues[i+1];
        	} else if (operators[i] == "*"){
        		answer = Math.round(answer * questionValues[i+1]);
        	} else if (operators[i] == "/"){
        		answer = Math.round(answer / questionValues[i+1]);
        	}
        }
	}
	
	// Sets the values to get the final score of the user
	public void setTimeItTook(boolean outcome, int time){
		time--;
		if(outcome){
			timeItTook = 10 - time;
			if(timeItTook != 10){
				points = points + Math.round(100/(10-time));
			} else {
				points = points + 100;
			}
		} else {
			points = points + 0;
		}
	}
}
