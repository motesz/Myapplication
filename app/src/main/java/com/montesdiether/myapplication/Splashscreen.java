package com.montesdiether.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.montesdiether.myapplication.data.DatabaseHelper;

import java.util.Timer;
import java.util.TimerTask;

public class Splashscreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DatabaseHelper db = new DatabaseHelper(Splashscreen.this);

        try{
            db.createDataBase();
        }catch(Exception e){
            Log.e("Splashscreen", "" + e.getLocalizedMessage());
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Splashscreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                finish();
                startActivity(new Intent(Splashscreen.this, MainActivity.class));
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}