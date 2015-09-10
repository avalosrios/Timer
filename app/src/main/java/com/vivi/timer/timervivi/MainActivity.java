package com.vivi.timer.timervivi;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    private CountDownTimer countdowntimer;
    private TextView hrsTxt, minTxt, secTxt;
    private long milisecondsFuture;
    private Button timerSetupButton;
    private boolean setup_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set view objects

        Intent intent = getIntent();
        int date [] = intent.getIntArrayExtra("date"); //yyyy/mm/dd
        int time [] = intent.getIntArrayExtra("time"); //hr/min


        if(date != null && time != null){
            // set the time out in seconds
            // calculate the current time in ms
            long current_ms = System.currentTimeMillis();
            GregorianCalendar future_date = new GregorianCalendar(date[0], date[1], date[2], time[0], time[1]);
            this.milisecondsFuture = future_date.getTimeInMillis() - current_ms;
            setup_done = true;
        }else{
            milisecondsFuture = 0;
            //make button visible again
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hrsTxt = (TextView) findViewById(R.id.hoursText);
        minTxt = (TextView) findViewById(R.id.minutesText);
        secTxt = (TextView) findViewById(R.id.secondsText);

        this.timerSetupButton = (Button) findViewById(R.id.timerSetupButton);
        if(setup_done)
            timerSetupButton.setVisibility(View.INVISIBLE);
        this.setCountDown();
        countdowntimer.start();
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
            System.out.println("============== SETTINGS!! =================");
            // TODO open settings and pass a view object
            this.startSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
                //System.out.println("======== Finished!!!! =========");
            }
        };
    }
}
