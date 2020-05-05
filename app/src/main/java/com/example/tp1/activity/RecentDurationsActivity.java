package com.example.tp1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tp1.R;
import com.example.tp1.util.ArrayDateFormatter;
import com.example.tp1.util.RecentDurations;

import java.util.List;

import static com.example.tp1.util.RecentDurations.*;

public class RecentDurationsActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_durations);

        List<Long> recentDurations = getInstance().getRecentDurations(this);
        ArrayAdapter<Long> adapter = new ArrayDateFormatter(this, android.R.layout.simple_list_item_1, recentDurations);

        this.listView = (ListView) findViewById(R.id.listview);

        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            long duration = recentDurations.get(position);
            setResult(RESULT_OK, new Intent().putExtra("duration", duration));
            RecentDurations.getInstance().addDuration(RecentDurationsActivity.this, duration);
            finish();
        });

        this.listView.setAdapter(adapter);
    }
}
