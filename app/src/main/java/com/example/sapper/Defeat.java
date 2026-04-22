package com.example.sapper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

final public class Defeat extends Result {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        TextView result = findViewById(R.id.result);

        result.setBackgroundColor(0x00FF0000);
        result.setText(R.string.defeat);

        Intent intent = getIntent();

        byte percent_victory = intent.getByteExtra("percent_victory", (byte) 0);

        TextView percentVictory = findViewById(R.id.percent_victory);

        percentVictory.setText(String.valueOf(percent_victory) +
                getString(R.string.percent));
    }
}
