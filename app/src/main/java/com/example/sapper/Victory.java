package com.example.sapper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

final public class Victory extends Result {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        TextView result = findViewById(R.id.result);

        result.setBackgroundResource(R.color.victory);
        result.setText(R.string.victory);
        TextView percentVictory = findViewById(R.id.percent_victory);
        percentVictory.setVisibility(View.GONE);
    }
}
