package com.example.karcsony;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView szamlalo;
    private Timer timer;
    private Date xmas = new Date(2021, 12, 24);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        szamlalo = findViewById(R.id.counter);
        Date now = Calendar.getInstance().getTime();
        int year = now.getYear();
        if (now.getMonth() == 11 && now.getDate() > 24) {
            year++;
        }
        xmas = new Date(year, 11, 24);
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date now = Calendar.getInstance().getTime();
                long timeDiff = xmas.getTime() - now.getTime();

                long sec = 1000;
                long min = sec*60;
                long hour = min*60;
                long day = hour*24;

                long nap = timeDiff/day;
                timeDiff %= day;
                long ora = timeDiff/hour;
                timeDiff %= hour;
                long perc = timeDiff/min;
                timeDiff %= min;
                long msdperc = timeDiff / sec;

                String ido = getString(R.string.counterFormat, nap, ora, perc, msdperc);
                runOnUiThread(() -> szamlalo.setText(ido));
            }
        };

        timer.schedule(task, 0, 500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}