package com.example.braintraininggame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Game Activity where the game takes place
public class GameActivity extends Activity {

	private int difficulty = 0;
    private int maxNumberOfValues = 0;
    private int minNumberOfValues = 0;
    private int numberOfTries = 0;
    private int hashBtnCounter = 0;
    private Question[] questions = new Question[10];
    private int questionCount = 0;
    private CountDownTimer ct;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Gets the extra information that was passed through the intent
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            difficulty = extras.getInt("GAME_DIFFICULTY");
        }

        // On-click listeners for all the keypad buttons
        /* START */
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
                	int answerInt;
                	try {
                		answerInt = Integer.parseInt(answer);
                		answerInt = -answerInt;
                    	tv.setText(String.valueOf(answerInt));
                	} catch (Exception e){
                		e.printStackTrace();
                	}
                }
            }
        });

        /* END */
        
        createTenQuestions();
        displayQuestion(questionCount);
    }
    
    // Deletes the character that the user has wrongly typed
    private void deleteCharacter(){
    	TextView tv = (TextView) findViewById(R.id.answer_text_field);
    	String tvString = tv.getText().toString();
    	int length = tvString.length();
    	if(length > 0)	tvString = tvString.substring(0, length - 1);
    	tv.setText(tvString);
    }
    
    // Checks the answer in the Answer field that the user has input. 
    private void checkAnswer(){
    	if(hashBtnCounter == 0){
	    	TextView answerField = (TextView) findViewById(R.id.answer_text_field);
	    	int answer;
	    	try{
	    		answer = Integer.parseInt(answerField.getText().toString());
	    	} catch (Exception e){
	    		answer = 999999999;
	    		// Since this value is out of the range, it is used as a value that can be a placeholder to compare
	    		// to.
	    	}
	    	if(answer == questions[questionCount].answer){
	    		TextView tv = (TextView) findViewById(R.id.correct_or_wrong);
	    		tv.setText("CORRECT!");
	    		tv.setTextColor(Color.GREEN);
	    		hashBtnCounter++;
	    		stopCountdown();
	    		questions[questionCount].setTimeItTook(true,time);
	    		questionCount++;
	    	} else {
	    		TextView tv = (TextView) findViewById(R.id.correct_or_wrong);
	    		tv.setText("WRONG!");
	    		tv.setTextColor(Color.RED);
	    		if(!PreferencesActivity.getHints(getBaseContext())){
	    			hashBtnCounter++;
	    			stopCountdown();
	    			questions[questionCount].setTimeItTook(false, time);
	    			questionCount++;
	    		} else {
	    			numberOfTries++;
	    			// if user runs out of time or has reached maximum number of tries, it goes to the next question.
	    			if(answer < questions[questionCount].answer){
	    				tv.setText("Greater");
	    			} else if (answer > questions[questionCount].answer) {
	    				tv.setText("Less");
	    			}
	    			if(time == 1 || numberOfTries == 4){
	    				hashBtnCounter++;
	    				numberOfTries = 0;
	    				stopCountdown();
	    				questions[questionCount].setTimeItTook(false,time);
	    				questionCount++;
	    				checkAnswer();
	    			}
	    		}
	    	}
    	} else if(hashBtnCounter == 1) {
    		// This block starts the next question and the countdown for it.
    		displayQuestion(questionCount);
    		TextView answerField = (TextView) findViewById(R.id.answer_text_field);
    		answerField.setText("?");
    		TextView correctOrWrong = (TextView) findViewById(R.id.correct_or_wrong);
    		correctOrWrong.setText("");
    		TextView timeRemaining = (TextView) findViewById(R.id.countdown_timer);
    		timeRemaining.setText("Time Remaining: 10 Seconds");
    		hashBtnCounter = 0;
    	}
    }
    
    //Creates all the ten questions at once before the game has started.
    private void createTenQuestions(){
    	// These if statements decide the difficulty of the game and how many numbers should be shown to the
    	// user
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
        
        // This creates the questions using a random number of values depending on the difficulty above
        for(int i = 0; i < questions.length; i++){
	        Random random = new Random();
	        int numberOfValues = random.nextInt(maxNumberOfValues - minNumberOfValues + 1) + minNumberOfValues;
	        questions[i] = new Question(numberOfValues);
	        questions[i].createQuestion();
        }

    }

    // Displays one of the questions on the screen for the user.
    private void displayQuestion(int index) {
    	if(questionCount == 10){
    		calculateScore();
    		return;
    	}
    	
    	String finalEquation = null;

        Question ques = questions[index];
        
        for(int i = 0; i < ques.questionValues.length; i++){
            if(i == 0)
                finalEquation = Integer.toString(ques.questionValues[i]) + ques.operators[i];
            else if (i == ques.questionValues.length - 1)
                finalEquation = finalEquation + Integer.toString(ques.questionValues[i]);
            else
                finalEquation = finalEquation + Integer.toString(ques.questionValues[i]) + ques.operators[i];
        }

        TextView tv = (TextView) findViewById(R.id.question_text_field);
        tv.setText(finalEquation);
        startCountdown();
    }

    // Calculates the score of the user at the end of the game.
    private void calculateScore() {
    	new AlertDialog.Builder(this)
        .setTitle(R.string.score_title)
        .setMessage("You got " + Question.points + " points! Please go back to the main screen")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
        .show();
    	
    	difficulty = 0;
        maxNumberOfValues = 0;
        minNumberOfValues = 0;
        numberOfTries = 0;
        hashBtnCounter = 0;
        questions = new Question[10];
        questionCount = 0;
        time = 0;
	}

    // When user presses the buttons, this updates the answer field with the numbers on the buttons
    private void input(int i){
        TextView tv = (TextView) findViewById(R.id.answer_text_field);
        String text = tv.getText().toString();
        if(text.contains("?")){
            text = "";
        }
        text = text + Integer.toString(i);
        tv.setText(text);
    }

    // Starts the countdown for the user.
    private void startCountdown(){
    	ct = new CountDownTimer(11000,1000){
    		public void onTick(long millisUntilFinished) {
    			TextView tv = (TextView) findViewById(R.id.countdown_timer);
    			tv.setText("Time Remaining: " + Math.round(millisUntilFinished / 1000) + " Seconds");
    			time = (int) Math.floor((millisUntilFinished/1000));
    	     }

    	     public void onFinish() {
    	    	 TextView tv = (TextView) findViewById(R.id.countdown_timer);
    	         tv.setText("");
    	         checkAnswer();
    	     }
    	  }.start();
    }
    
    // Stops the countdown when user has entered a correct or wrong answer
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
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
