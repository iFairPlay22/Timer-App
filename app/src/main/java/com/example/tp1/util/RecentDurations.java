package com.example.tp1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RecentDurations {

    private RecentDurations() {}

    private static final RecentDurations instance = new RecentDurations();

    public static RecentDurations getInstance() {
        return instance;
    }

    public List<Long> getRecentDurations(Context context) {

        String[] durations = PreferenceManager.getDefaultSharedPreferences(context).getString("durations", "").split(",");

        List<Long> result = new ArrayList<>();

        for (String duration : durations) {
            if (duration.length() != 0) {
                result.add(Long.parseLong(duration));
            }
        }

        Log.i("test", "Get : " + result.toString());

        return result;
    }

    public void addDuration(Context context, long duration) {

        List<Long> durations = getRecentDurations(context);

        int length = durations.size();

        if (!(length < 10)) {
            // TODO
        }

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        durations.add(duration);

        StringBuilder result = new StringBuilder();
        result.append(durations.get(0));

        for (int i = 1; i < length + 1; i++) {
            result.append(",");
            result.append(durations.get(i));
        }

        editor.putString("durations", result.toString());

        editor.apply();
    }
}
