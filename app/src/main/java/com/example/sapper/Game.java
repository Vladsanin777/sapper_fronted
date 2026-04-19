package com.example.sapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {
    MinsField m_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mins_field);

        Intent date = getIntent();

        int count_rows = date.getIntExtra("count_rows", 10);
        int count_columns = date.getIntExtra("count_columns", 10);
        int count_mins = date.getIntExtra("count_mins",10);
        int show_seconds = date.getIntExtra("count_show_seconds", 10);

        m_field = new MinsField(count_rows, count_columns, count_mins);

        TextView rows = findViewById(R.id.count_rows);
        rows.setText(getString(R.string.count_rows) + count_rows);

        TextView columns = findViewById(R.id.count_columns);
        columns.setText(getString(R.string.count_columns) + count_columns);

        TextView mins = findViewById(R.id.count_mins);
        mins.setText(getString(R.string.count_mins) + count_mins);
    }
}
