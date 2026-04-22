package com.example.sapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

abstract public class Result extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.result);

        Intent intent = getIntent();

        long count_rows = intent.getLongExtra("count_rows", 0);
        long count_columns = intent.getLongExtra("count_columns", 0);
        long count_mins = intent.getLongExtra("count_mins", 0);

        TextView countRows = findViewById(R.id.count_rows);
        TextView countColumns = findViewById(R.id.count_columns);
        TextView countMins = findViewById(R.id.count_mins);

        countRows.setText(String.valueOf(count_rows));
        countColumns.setText(String.valueOf(count_columns));
        countMins.setText(String.valueOf(count_mins));
    }
}