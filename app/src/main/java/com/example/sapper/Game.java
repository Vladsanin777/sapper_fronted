package com.example.sapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Game extends AppCompatActivity {
    public class MineFieldAdapter extends RecyclerView.Adapter<MineFieldAdapter.ViewHolder> {
        private int m_countRows;
        private int m_countColumns;
        private MinsField m_field;

        public MineFieldAdapter(MinsField field, int countRows, int countColumns) {
            m_field = field;
            m_countRows = countRows;
            m_countColumns = countColumns;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Cell cell = new Cell(parent.getContext(), m_field);
            cell.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,200));
            return new ViewHolder(cell);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.m_cell.setIndexRow(position / m_countColumns);
            holder.m_cell.setIndexColumn(position % m_countColumns);
            holder.m_cell.updateHide();
        }

        @Override
        public int getItemCount() {
            return m_countRows * m_countColumns;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private Cell m_cell;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                m_cell = (Cell) itemView;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mins_field);

        Intent date = getIntent();

        int countRows = date.getIntExtra("count_rows", 10);
        int countColumns = date.getIntExtra("count_columns", 10);
        int countMins = date.getIntExtra("count_mins",10);
        int showSeconds = date.getIntExtra("count_show_seconds", 10);

        MinsField field = new MinsField(countRows, countColumns, countMins);

        field.modeBasic();

        TextView rows = findViewById(R.id.count_rows);
        rows.setText(getString(R.string.count_rows) + countRows);

        TextView columns = findViewById(R.id.count_columns);
        columns.setText(getString(R.string.count_columns) + countColumns);

        TextView mins = findViewById(R.id.count_mins);
        mins.setText(getString(R.string.count_mins) + countMins);

        RecyclerView minsField = findViewById(R.id.mins_field);

        minsField.setLayoutManager(new GridLayoutManager(this, countColumns));

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);

        Drawable divider = ContextCompat.getDrawable(this, R.drawable.grid_divider);
        horizontalDecoration.setDrawable(divider);
        verticalDecoration.setDrawable(divider);

        minsField.addItemDecoration(horizontalDecoration);
        minsField.addItemDecoration(verticalDecoration);

        MineFieldAdapter adapter = new MineFieldAdapter(field, countRows, countColumns);
        minsField.setAdapter(adapter);
    }
}