package com.vivi.timer.timervivi;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    private CountDownTimer countdowntimer;
    private TextView hrsTxt, minTxt, secTxt;
    private long milisecondsFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        int date [] = intent.getIntArrayExtra("date"); //yyyy/mm/dd
        int time [] = intent.getIntArrayExtra("time"); //hr/min

        if(date != null && time != null){
            // set the time out in seconds
            // calculate the current time in ms
            long current_ms = System.currentTimeMillis();
            GregorianCalendar future_date = new GregorianCalendar(date[0], date[1], date[2], time[0], time[1]);
            this.milisecondsFuture = future_date.getTimeInMillis() - current_ms;
        }else{
            milisecondsFuture = 0;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hrsTxt = (TextView) findViewById(R.id.hoursText);
        minTxt = (TextView) findViewById(R.id.minutesText);
        secTxt = (TextView) findViewById(R.id.secondsText);

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
            // TODO open settings
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void drawCountDown(long millisToFinish){
        long seconds, minutes, hours = 0;

        seconds = millisToFinish/1000 % 60;

        minutes = millisToFinish/(1000 * 60) % 60;

        hours = millisToFinish/(1000 * 60 * 60);

        hrsTxt.setText(hours + "");
        minTxt.setText(minutes + "");
        secTxt.setText(seconds + "");
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
                //System.out.println("======== Finished!!!! =========");
            }
        };
    }
}
