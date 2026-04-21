package com.example.sapper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.result);

        Intent intent = getIntent();

        boolean is_victory = intent.getBooleanExtra("is_victory",true);
        long count_rows = intent.getLongExtra("count_rows", 0);
        long count_columns = intent.getLongExtra("count_columns", 0);
        long count_mins = intent.getLongExtra("count_mins", 0);

        TextView result = findViewById(R.id.result);

        if (is_victory) {
            result.setText(R.string.victory);
        } else {
            result.setText(R.string.defeat);
        }

        TextView countRows = findViewById(R.id.count_rows);
        TextView countColumns = findViewById(R.id.count_columns);
        TextView countMins = findViewById(R.id.count_mins);

        countRows.setText(String.valueOf(count_rows));
        countColumns.setText(String.valueOf(count_columns));
        countMins.setText(String.valueOf(count_mins));
    }
}
