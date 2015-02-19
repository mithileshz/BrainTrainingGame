package com.example.braintraininggame;

import java.util.Random;

import android.util.Log;

public class Question {
	
	public int[] questionValues;
	private int answer;
	public String[] operators;
	public static int points = 0;
	
	int timeItTook;
	
	public Question(int numberOfValues){
		questionValues = new int[numberOfValues];
		operators = new String[numberOfValues - 1];
	}
	
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
	
	public int getAnswer(){
		return answer;
	}
	
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
        
        Log.wtf("dfdfdklasjdskljdsalkdjaskldsj", answer + "  ");
	}
	
	public void setTimeItTook(boolean outcome, int time){
		Log.wtf("Time Left", Integer.toString(time));
		if(outcome){
			timeItTook = time;
			if(time != 0){
				points = points + Math.round(100/(10-time));
			} else {
				points = points + 100;
			}
		} else {
			points = points + 0;
		}
	}
}
