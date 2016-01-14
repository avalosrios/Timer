package com.vivi.timer.timervivi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends Activity {

    private CountDownTimer countdowntimer;
    private TextView hrsTxt, minTxt, secTxt, runningTxt, stopTxt;
    private long milisecondsFuture;
    private Button timerSetupButton;
    private boolean setup_done;
    private SharedPreferences mPrefs;
    private GregorianCalendar future_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mPrefs = getSharedPreferences("ViviTimer.prefs", Context.MODE_PRIVATE);
        Log.i(this.getLocalClassName(), "prefs " + this.mPrefs);
        this.setMilisecondsFuture();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //initialize all text views
        hrsTxt = (TextView) findViewById(R.id.hoursText);
        minTxt = (TextView) findViewById(R.id.minutesText);
        secTxt = (TextView) findViewById(R.id.secondsText);

        runningTxt = (TextView) findViewById(R.id.runningText);
        stopTxt = (TextView) findViewById(R.id.stopText);

        this.timerSetupButton = (Button) findViewById(R.id.timerSetupButton);
        if(setup_done){
            timerSetupButton.setVisibility(View.INVISIBLE);
            runningTxt.setVisibility(View.VISIBLE);
            stopTxt.setVisibility(View.INVISIBLE);
        }
        this.setCountDown();
        countdowntimer.start();
    }

    private int[] extractFromPreferences(String [] keys){
        int result [] = new int [keys.length];
        for(int i=0; i< keys.length; i++){
            int val =  mPrefs.getInt(keys[i], -1);
            if (val >= 0){
                result[i] = val;
            }else {
                // TODO throw exception
                Log.i(this.getLocalClassName(), "Key not found "+keys[i]);
            }
        }
        return result;
    }

    private void setMilisecondsFuture(){
        Intent intent = getIntent();
        int [] date = intent.getIntArrayExtra("date"); //yyyy/mm/dd
        int [] time = intent.getIntArrayExtra("time"); //hr/min

        if(!this.mPrefs.getAll().isEmpty()){
            // try to populate date and time with shared preferences
            Log.d(this.getLocalClassName(), "Extracting from shared preferences");

            // For date we got 3 keys yyyy mm and dd
            String date_keys[] = new String[] {"yyyy","mm","dd"};
            date = extractFromPreferences(date_keys);

            String time_keys[] = new String[] {"h", "m"};
            time = extractFromPreferences(time_keys);

        }

        if(date != null && time != null){
            // set the time out in seconds
            // calculate the current time in ms
            long current_ms = System.currentTimeMillis();

            this.future_date = new GregorianCalendar(date[0], date[1], date[2], time[0], time[1]);//yyyy/mm/dd hr/min

            this.milisecondsFuture = future_date.getTimeInMillis() - current_ms;
            this.setup_done = true;

        } else {
            this.milisecondsFuture = 0;
        }
        Log.d(this.getLocalClassName(), "milisecondsFuture " + this.milisecondsFuture);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Log.d(this.getLocalClassName(), "============== SETTINGS!! =================");
            // TODO open settings and pass a view object
            this.startSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause(){
        super.onPause();
    }

    /* This is not a great idea
    @Override
    public void onBackPressed(){
        Log.d(this.getLocalClassName(), "Back button pressed");
    }*/

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void buttonClickHandler(View v){
        startSettingsActivity();
    }

    private void drawCountDown(long millisToFinish){
        long seconds, minutes, hours = 0;

        seconds = millisToFinish/1000 % 60;

        minutes = millisToFinish/(1000 * 60) % 60;

        hours = millisToFinish/(1000 * 60 * 60);

        hrsTxt.setText(numberToFormattedString(hours));
        minTxt.setText(numberToFormattedString(minutes));
        secTxt.setText(numberToFormattedString(seconds));
    }

    private String numberToFormattedString(long n){
        if(n < 10)
            return "0"+n;
        return n+"";
    }

    private void setCountDown(){
        //long millisFuture = 3600000 + 120000; //TODO get this config from settings
        long millisFuture = milisecondsFuture;
        long interval = 1000;
        countdowntimer =  new CountDownTimer(millisFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                drawCountDown(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                drawCountDown(0);
                setup_done = false;
                timerSetupButton.setVisibility(View.VISIBLE);
                runningTxt.setVisibility(View.INVISIBLE);
                stopTxt.setVisibility(View.VISIBLE);
                // Clar all shared preferences
                mPrefs.edit().clear().commit();
            }
        };
    }
}
