package com.example.tp1.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.tp1.R;

import java.util.Objects;

public class DurationTextWatcher implements TextWatcher {
    private final EditText editText;

    public DurationTextWatcher(EditText editText) {
        this.editText = Objects.requireNonNull(editText);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        int printValue = -1;
        try {
            printValue = Integer.parseInt(editable.toString());
        } catch (NumberFormatException error) {}
        if (printValue < 0 || 60 <= printValue) {
            editText.setError(editText.getContext().getResources().getString(R.string.number_format_exception));
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
