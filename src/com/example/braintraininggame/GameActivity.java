package com.example.braintraininggame;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {

	int difficulty;
    int maxNumberOfValues;
    int minNumberOfValues;
    int correctAnswer;
    int hashBtnCounter = 0;
    int questionCount = 0;
    String finalEquation;
    int[] questionValues;
    CountDownTimer ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            difficulty = extras.getInt("GAME_DIFFICULTY");
        }

        Button BtnOne = (Button) findViewById(R.id.one);
        BtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(1);
            }
        });
        Button BtnTwo = (Button) findViewById(R.id.two);
        BtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(2);
            }
        });
        Button BtnThree = (Button) findViewById(R.id.three);
        BtnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(3);
            }
        });
        Button BtnFour = (Button) findViewById(R.id.four);
        BtnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(4);
            }
        });
        Button BtnFive = (Button) findViewById(R.id.five);
        BtnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(5);
            }
        });
        Button BtnSix = (Button) findViewById(R.id.six);
        BtnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(6);
            }
        });
        Button BtnSeven = (Button) findViewById(R.id.seven);
        BtnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(7);
            }
        });
        Button BtnEight = (Button) findViewById(R.id.eight);
        BtnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(8);
            }
        });
        Button BtnNine = (Button) findViewById(R.id.nine);
        BtnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(9);
            }
        });
        Button BtnZero = (Button) findViewById(R.id.zero);
        BtnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input(0);
            }
        });
        
        Button BtnDelete = (Button) findViewById(R.id.delete);
        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCharacter();
            }
        });
        
        Button BtnHash = (Button) findViewById(R.id.hash);
        BtnHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        
        Button BtnMinus = (Button) findViewById(R.id.minus);
        BtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.answer_text_field);
                String answer = tv.getText().toString();
                if(!answer.contains("?")){
                	int answerInt = Integer.parseInt(answer);
                	answerInt = -answerInt;
                	tv.setText(String.valueOf(answerInt));
                }
            }
        });

        createOneQuestion();
    }
    
    private void deleteCharacter(){
    	TextView tv = (TextView) findViewById(R.id.answer_text_field);
    	String tvString = tv.getText().toString();
    	int length = tvString.length();
    	if(length > 0)	tvString = tvString.substring(0, length - 1);
    	tv.setText(tvString);
    }
    
    private void checkAnswer(){
    	if(hashBtnCounter == 0){
	    	TextView answerField = (TextView) findViewById(R.id.answer_text_field);
	    	int answer = Integer.parseInt(answerField.getText().toString());
	    	if(answer == correctAnswer){
	    		TextView tv = (TextView) findViewById(R.id.correct_or_wrong);
	    		tv.setText("CORRECT!");
	    		tv.setTextColor(Color.GREEN);
	    		hashBtnCounter++;
	    		stopCountdown();
	    	} else {
	    		TextView tv = (TextView) findViewById(R.id.correct_or_wrong);
	    		tv.setText("WRONG!");
	    		tv.setTextColor(Color.RED);
	    	}
    	} else if(hashBtnCounter == 1) {
    		createOneQuestion();
    		TextView answerField = (TextView) findViewById(R.id.answer_text_field);
    		answerField.setText("?");
    		TextView correctOrWrong = (TextView) findViewById(R.id.correct_or_wrong);
    		correctOrWrong.setText("");
    		TextView timeRemaining = (TextView) findViewById(R.id.countdown_timer);
    		timeRemaining.setText("Time Remaining: 10 Seconds");
    		hashBtnCounter = 0;
    	}
    }

    private void createOneQuestion() {
    	questionCount++;
    	if(questionCount == 10){
    		calculateScore();
    	}
        if(difficulty == 0){
            maxNumberOfValues = 2;
            minNumberOfValues = 2;
        }
        if(difficulty == 1){
            maxNumberOfValues = 3;
            minNumberOfValues = 2;
        }
        if(difficulty == 2){
            maxNumberOfValues = 4;
            minNumberOfValues = 2;
        }
        if(difficulty == 3){
            maxNumberOfValues = 6;
            minNumberOfValues = 4;
        }

        Random random = new Random();
        int numberOfValues = random.nextInt(maxNumberOfValues - minNumberOfValues + 1) + minNumberOfValues;

        questionValues = new int[numberOfValues];

        for(int i = 0; i<questionValues.length;i++){
            questionValues[i] = random.nextInt(100) + 1;
        }

        String[] operators = new String[numberOfValues - 1];
        finalEquation = null;

        for(int i = 0; i < operators.length; i++){
            int placeHolder = random.nextInt(4);
            if(placeHolder == 0) operators[i] = "+";
            if(placeHolder == 1) operators[i] = "-";
            if(placeHolder == 2) operators[i] = "*";
            if(placeHolder == 3) operators[i] = "/";
        }

        operate(operators, questionValues);

        for(int i = 0; i < questionValues.length; i++){
            if(i == 0)
                finalEquation = Integer.toString(questionValues[i]) + operators[i];
            else if (i == questionValues.length - 1)
                finalEquation = finalEquation + Integer.toString(questionValues[i]);
            else
                finalEquation = finalEquation + Integer.toString(questionValues[i]) + operators [i];
        }

        TextView tv = (TextView) findViewById(R.id.question_text_field);
        tv.setText(finalEquation);
        startCountdown();
    }

    public void operate(String[] operator, int[] questionValues) {
        correctAnswer = 0;
    	correctAnswer += questionValues[0];
        for(int i = 0; i < operator.length; i++){
        	if(operator[i] == "+"){
        		correctAnswer += questionValues[i+1];
        	} else if (operator[i] == "-"){
        		correctAnswer -= questionValues[i+1];
        	} else if (operator[i] == "*"){
        		correctAnswer = Math.round(correctAnswer * questionValues[i+1]);
        	} else if (operator[i] == "/"){
        		correctAnswer = Math.round(correctAnswer / questionValues[i+1]);
        	}
        }
        
        Log.wtf("dfdfdklasjdskljdsalkdjaskldsj", correctAnswer + "  ");
    }

    public void input(int i){
        TextView tv = (TextView) findViewById(R.id.answer_text_field);
        String text = tv.getText().toString();
        if(text.contains("?")){
            text = "";
        }
        text = text + Integer.toString(i);
        tv.setText(text);
    }

    private void startCountdown(){
    	ct = new CountDownTimer(10000,1000){
    		public void onTick(long millisUntilFinished) {
    			TextView tv = (TextView) findViewById(R.id.countdown_timer);
    			tv.setText("Time Remaining: " + millisUntilFinished / 1000 + " Seconds");
    	     }

    	     public void onFinish() {
    	    	 TextView tv = (TextView) findViewById(R.id.countdown_timer);
    	         tv.setText("");
    	         checkAnswer();
    	     }
    	  }.start();
    }
    
    private void stopCountdown(){
    	ct.cancel();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Preferences.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
