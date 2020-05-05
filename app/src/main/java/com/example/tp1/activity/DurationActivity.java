package com.example.tp1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
// import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tp1.R;
import com.example.tp1.util.DurationTextWatcher;

public class DurationActivity extends AppCompatActivity {
    private EditText hours;
    private EditText minutes;
    private EditText seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);

        hours = findViewById(R.id.input_hours);
        minutes = findViewById(R.id.input_minutes);
        seconds = findViewById(R.id.input_seconds);

        minutes.addTextChangedListener(new DurationTextWatcher(minutes));
        seconds.addTextChangedListener(new DurationTextWatcher(seconds));

        Button b = (Button) findViewById(R.id.button_validate);
        b.setOnClickListener(button -> {
            long duration = getDuration();

            Toast t = Toast.makeText(this, getResources().getString(R.string.toast_duration) +  duration, Toast.LENGTH_SHORT);
            t.show();

            Intent intent = new Intent();
            intent.putExtra("duration", duration);
            setResult(RESULT_OK, intent);

            // Log.i("test", "put Data : " + duration);

            finish();
        });
    }

    private long getDuration() {
        long duration = 0;

        try {
            duration += Long.parseLong(hours.getText().toString()) * 3600;
        } catch (Exception e) {}

        try {
            duration += Long.parseLong(minutes.getText().toString()) * 60;
        } catch (Exception e) {}

        try {
            duration += Long.parseLong(seconds.getText().toString());
        } catch (Exception e) {}

        return duration * 1000;
    }
}
