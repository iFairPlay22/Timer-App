package com.example.tp1.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
// import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.tp1.R;
import com.example.tp1.util.Clepsydra;
import com.example.tp1.util.ArrayDateFormatter;
import com.example.tp1.util.RecentDurations;

import static android.media.RingtoneManager.getActualDefaultRingtoneUri;
import static android.media.RingtoneManager.getRingtone;

// import android.widget.Toast;

public class CountdownActivity extends AppCompatActivity {

    private boolean refreshView = true;
    private boolean active;
    private long countdownSave = 0;
    private long countdown = 0;
    private long startTime = 0;

    private TextView countdownTextView;
    private Button countdownButton;
    private Button startButton;
    private Button refreshButton;
    private Button stopButton;
    private Button historyButton;

    private Clepsydra clepsydra;

    private Handler handler;

    private Ringtone ringtone;

    private Runnable refreshRunnable = () -> {

        if (this.active) {

            this.countdown -= (System.currentTimeMillis() - this.startTime);
            this.startTime = System.currentTimeMillis();

            if (this.countdown < 0) {

                this.countdown = 0;

                // TODO : Alarme quand fin de temps --> ok ?

                AlarmManager am = (AlarmManager) this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                PendingIntent alarmIntent = PendingIntent.getActivity(this, 1, new Intent(this, CountendActivity.class), 0);
                am.setExact(AlarmManager.ELAPSED_REALTIME, 0, alarmIntent);

                ringtone = getRingtone(CountdownActivity.this, getActualDefaultRingtoneUri(CountdownActivity.this, RingtoneManager.TYPE_ALARM));

                // TODO : Faire sonner quand actif --> ok ?

                onResume();
                // ou
                // ringtone.play();

                this.update(false);

                return ;
            }

            this.actualizeCountdown();

            handler.postDelayed(this.getRefreshRunnable(), 50);
        }

    };

    private Runnable getRefreshRunnable () {
        return refreshRunnable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        this.countdownTextView = (TextView) findViewById(R.id.countdownTextView);
        this.countdownButton = (Button) findViewById(R.id.countdownButton);
        this.startButton = (Button) findViewById(R.id.startButton);
        this.refreshButton = (Button) findViewById(R.id.refreshButton);
        this.stopButton = (Button) findViewById(R.id.stopButton);
        this.historyButton = (Button) findViewById(R.id.historyButton);
        this.clepsydra = (Clepsydra) findViewById(R.id.clepsydra);

        handler = new Handler();

        countdownButton.setOnClickListener(button -> {
            Intent intent = new Intent(this, DurationActivity.class);
            intent.putExtra("initialDuration", 3600);
            startActivityForResult(intent, 1);
        });

        historyButton.setOnClickListener(button -> {Intent intent = new Intent(this, DurationActivity.class);
            Intent intent2 = new Intent(this, RecentDurationsActivity.class);
            startActivityForResult(intent2, 1);
        });

        startButton.setOnClickListener(button -> {
            this.startTime = System.currentTimeMillis();
            this.update(true);
        });

        refreshButton.setOnClickListener(button -> {
            this.countdown = this.countdownSave;
            this.update(false);
        });

        stopButton.setOnClickListener(button -> {
            this.update(false);
        });

        this.update(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            this.countdown = data.getLongExtra("duration", 0);
            this.countdownSave = this.countdown;
            this.refreshView = true;

            RecentDurations.getInstance().addDuration(this, this.countdown);

            this.update(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.refreshView = false;

        if (ringtone != null) {
            ringtone.stop();
            ringtone = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.refreshView = true;

        if (ringtone != null) {
            ringtone.play();
        }
    }

    private void update(boolean active) {

        this.active = active;

        if (this.active) {

            this.countdownButton.setEnabled(false);
            this.startButton.setEnabled(false);
            this.refreshButton.setEnabled(false);
            this.stopButton.setEnabled(true);
            this.historyButton.setEnabled(false);

            this.handler.post(refreshRunnable);

        } else {

            this.countdownButton.setEnabled(true);
            this.startButton.setEnabled(true);
            this.refreshButton.setEnabled(true);
            this.stopButton.setEnabled(false);
            this.historyButton.setEnabled(true);

        }

        this.actualizeCountdown();
    }

    private void actualizeCountdown() {
        if (this.refreshView) {
            this.countdownTextView.setText(ArrayDateFormatter.format(this.countdown));
        }
        this.clepsydra.setFillRatio(this.countdownSave == 0 ? 0 : (double) (((double) this.countdown) / ((double) this.countdownSave)));
    }
}
