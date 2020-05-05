package com.example.tp1.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArrayDateFormatter extends ArrayAdapter<Long> {

    private Context context;

    public ArrayDateFormatter(@NonNull Context context, int resource, @NonNull List<Long> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView textView = new TextView(context);
        textView.setText(simpleFormat(getItem(position)));

        return textView;
    }

    private static String simpleFormat(long countdown) {
        long milliseconds = countdown;
        long totalSecs = milliseconds / 1000;

        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;

        return hours + ":" + String.format("%02d", mins) + (secs == 0 ? "" : String.format(":%02d", secs));
    }

    public static String format(long countdown) {
        long milliseconds = countdown;
        long totalSecs = milliseconds / 1000;

        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        milliseconds -= (hours * (60 * 60  * 1000) + mins * 60 * 1000 + secs * 1000);

        return hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds);
    }
}
