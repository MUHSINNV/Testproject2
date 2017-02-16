package com.example.muhsin.testproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by MUHSIN on 2/16/2017.
 */

public class Loginsucces extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsucces_activity);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Toast.makeText(Loginsucces.this, pref.getString("idvalue", null), Toast.LENGTH_LONG).show();
    }
}
