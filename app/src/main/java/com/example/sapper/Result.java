package com.example.sapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

abstract public class Result extends AppCompatActivity {
    private long m_countRows;
    private long m_countColumns;
    private long m_countMins;
    private long m_countShowSecond;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.result);

        Intent intent = getIntent();

        m_countRows = intent.getLongExtra("count_rows", 0);
        m_countColumns = intent.getLongExtra("count_columns", 0);
        m_countMins = intent.getLongExtra("count_mins", 0);
        m_countShowSecond = intent.getLongExtra("count_show_second", 0);

        TextView countRows = findViewById(R.id.count_rows);
        TextView countColumns = findViewById(R.id.count_columns);
        TextView countMins = findViewById(R.id.count_mins);

        countRows.setText(String.valueOf(m_countRows));
        countColumns.setText(String.valueOf(m_countColumns));
        countMins.setText(String.valueOf(m_countMins));

        TextView result = findViewById(R.id.result);

        result.setTextColor(getColor(R.color.white));

        Button menu = findViewById(R.id.menu_button);
        menu.setOnClickListener(this::onClickMenuButton);

        Button again = findViewById(R.id.again_button);
        again.setOnClickListener(this::onClickAgainButton);
    }

    public void onClickMenuButton(View view) {
        finish();
    }

    public void onClickAgainButton(View view) {
        Intent intent = new Intent(getApplicationContext(), Game.class);

        intent.putExtra("count_rows", m_countRows);
        intent.putExtra("count_columns", m_countColumns);
        intent.putExtra("count_mins", m_countMins);
        intent.putExtra("count_show_seconds", m_countShowSecond);

        startActivity(intent);

        finish();
    }
}