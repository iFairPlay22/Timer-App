package com.example.tp1.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;

import com.example.tp1.R;
import com.example.tp1.util.RecentDurations;

import static android.media.RingtoneManager.getActualDefaultRingtoneUri;
import static android.media.RingtoneManager.getRingtone;

public class CountendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countend);
    }

}
