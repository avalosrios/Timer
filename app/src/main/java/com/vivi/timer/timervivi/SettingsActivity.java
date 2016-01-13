package com.vivi.timer.timervivi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;


public class SettingsActivity extends ActionBarActivity {

    private Button okButton;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPrefs = getSharedPreferences("ViviTimer.prefs", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveSettings(View view){
        // passing selected settings
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        // Save this somwhere;
        // NOTE The month is from 0 to 11
        System.out.println("Date Picker  day "+datePicker.getDayOfMonth() + " Month "+ datePicker.getMonth() + " Year "+ datePicker.getYear());

        int h = timePicker.getCurrentHour(); //deprecated
        int m = timePicker.getCurrentMinute(); //deprecated

        int yyyy = datePicker.getYear();
        int mm = datePicker.getMonth();
        int dd = datePicker.getDayOfMonth();

        System.out.println("Time Picker  hour " + h + " min " + m);

        Intent intent = new Intent(this, MainActivity.class);
        int [] date_array =  new int[] {yyyy,mm,dd};
        int [] time_array = new int[] {h,m};
        intent.putExtra("date", date_array);
        intent.putExtra("time", time_array);


        //Save data on shared preferences
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("yyyy", yyyy);
        ed.putInt("mm", mm);
        ed.putInt("dd", dd);

        ed.putInt("h", h);
        ed.putInt("m", m);

        ed.apply();




        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(this, "Let the hunger games begin!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){
        // do something here and don't write super.onBackPressed()
        this.saveSettings(null);
    }
}
