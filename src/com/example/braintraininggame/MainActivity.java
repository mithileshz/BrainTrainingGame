package com.example.braintraininggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutBtn = (Button) findViewById(R.id.about_button);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about(v);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exit_button);
        exitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                exit(v);
            }
        });

        Button newGameBtn = (Button) findViewById(R.id.new_game_button);
        newGameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newGame(v);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void about(View view){
        new AlertDialog.Builder(this)
                .setTitle(R.string.about_label)
                .setMessage(R.string.about_summary)
                .show();
    }

    private void exit(View view){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //saveCurrentGame();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            }
        };

        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_label)
                .setMessage(R.string.exit_summary)
                .setPositiveButton(R.string.yes,dialogClickListener)
                .setNegativeButton(R.string.no,dialogClickListener)
                .show();

    }

    private void newGame(View view){
        new AlertDialog.Builder(this)
                .setTitle(R.string.difficulty)
                .setItems(R.array.difficulty,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialogInterface, int i){
                                startGame(i);
                            }
                        })
                .show();
    }

    private void startGame(int i){
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("GAME_DIFFICULTY",i);
        startActivity(intent);
    }
}
