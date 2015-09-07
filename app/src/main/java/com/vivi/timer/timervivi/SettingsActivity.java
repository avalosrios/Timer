package com.vivi.timer.timervivi;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.json.JSONObject;


public class SettingsActivity extends ActionBarActivity {

    private Button okButton;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        System.out.println("Time Picker  hour " + h + " min "+m);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("date", new int[] {yyyy,mm,dd});
        intent.putExtra("time", new int[] {h,m});
        startActivity(intent);
    }
}
