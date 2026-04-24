package com.example.sapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shawnlin.numberpicker.NumberPicker;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'sapper' library on application startup.
    static {
        System.loadLibrary("sapper");
    }

    NumberPicker m_rows;
    NumberPicker m_columns;
    NumberPicker m_mins;
    NumberPicker m_show_seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        m_rows = findViewById(R.id.count_rows);

        m_columns = findViewById(R.id.count_columns);
        m_mins = findViewById(R.id.count_mins);
        m_show_seconds = findViewById(R.id.show_seconds);

        Button startGame = findViewById(R.id.start_game_button);

        startGame.setOnClickListener(this::onClickStartGame);
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("count_rows", (long) m_rows.getValue());
        intent.putExtra("count_columns", (long) m_columns.getValue());
        intent.putExtra("count_mins", (long) m_mins.getValue());
        intent.putExtra("count_show_seconds", (long) m_show_seconds.getValue());

        startActivity(intent);
    }
}