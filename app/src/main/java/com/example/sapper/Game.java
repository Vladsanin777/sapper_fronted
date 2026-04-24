package com.example.sapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;


public class Game extends AppCompatActivity {
    private long m_countRows;
    private long m_countColumns;
    private long m_countMins;
    private long m_showSeconds;
    private MinsField m_field;

    public class MineFieldAdapter extends RecyclerView.Adapter<MineFieldAdapter.ViewHolder> {
        private long m_countRows;
        private long m_countColumns;
        private View.OnClickListener m_openCell;
        private View.OnClickListener m_victory;
        private View.OnClickListener m_defeat;

        public MineFieldAdapter(View.OnClickListener openCell,
                                View.OnClickListener victory,
                                View.OnClickListener defeat,
                                long countRows, long countColumns) {
            m_openCell = openCell;
            m_victory = victory;
            m_defeat = defeat;
            m_countRows = countRows;
            m_countColumns = countColumns;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Cell cell = new Cell(parent.getContext(), m_field, m_openCell, m_victory, m_defeat);
            cell.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,200));
            return new ViewHolder(cell);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.m_cell.setIndexRow(position / (int) m_countColumns);
            holder.m_cell.setIndexColumn(position % (int) m_countColumns);
            holder.m_cell.updateHide();
        }

        @Override
        public int getItemCount() {
            return (int) m_countRows * (int) m_countColumns;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private Cell m_cell;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                m_cell = (Cell) itemView;
            }
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private long m_spanCount;
        private int m_spacing;
        private boolean m_includeEdge;
        private final Paint m_paint = new Paint();

        public GridSpacingItemDecoration(long spanCount, int spacing, boolean includeEdge, @ColorInt int color) {
            m_spanCount = spanCount;
            m_spacing = spacing;
            m_includeEdge = includeEdge;

            m_paint.setColor(color);
            m_paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % (int) m_spanCount;

            if (m_includeEdge) {
                outRect.left = m_spacing - column * m_spacing / (int) m_spanCount;
                outRect.right = (column + 1) * m_spacing / (int) m_spanCount;

                if (position < m_spanCount) {
                    outRect.top = m_spacing;
                }
                outRect.bottom = m_spacing;
            } else {
                outRect.left = column * m_spacing / (int) m_spanCount;
                outRect.right = m_spacing - (column + 1) * m_spacing / (int) m_spanCount;
                if (position >= m_spanCount) {
                    outRect.top = m_spacing;
                }
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                int rightLeft = child.getRight();
                int rightRight = rightLeft + m_spacing;
                int rightTop = child.getTop();
                int rightBottom = child.getBottom();
                c.drawRect(rightLeft, rightTop, rightRight, rightBottom, m_paint);

                int bottomLeft = child.getLeft();
                int bottomRight = child.getRight() + m_spacing;
                int bottomTop = child.getBottom();
                int bottomBottom = bottomTop + m_spacing;
                c.drawRect(bottomLeft, bottomTop, bottomRight, bottomBottom, m_paint);

                if (m_includeEdge) {
                    int position = parent.getChildAdapterPosition(child);
                    int column = position % (int) m_spanCount;

                    if (column == 0) {
                        c.drawRect(child.getLeft() - m_spacing, child.getTop(), child.getLeft(), child.getBottom(), m_paint);
                    }
                    if (position < m_spanCount) {
                        c.drawRect(child.getLeft() - m_spacing, child.getTop() - m_spacing, child.getRight() + m_spacing, child.getTop(), m_paint);
                    }
                }
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mins_field);

        Intent date = getIntent();

        m_countRows = date.getLongExtra("count_rows", 10);
        m_countColumns = date.getLongExtra("count_columns", 10);
        m_countMins = date.getLongExtra("count_mins",10);
        m_showSeconds = date.getLongExtra("count_show_seconds", 10);

        m_field = new MinsField(m_countRows, m_countColumns, m_countMins);

        TextView rows = findViewById(R.id.count_rows);
        rows.setText(getString(R.string.count_rows) + m_countRows);

        TextView columns = findViewById(R.id.count_columns);
        columns.setText(getString(R.string.count_columns) + m_countColumns);

        TextView mins = findViewById(R.id.count_mins);
        mins.setText(getString(R.string.count_mins) + m_countMins);

        RecyclerView minsField = findViewById(R.id.mins_field);

        minsField.setLayoutManager(new GridLayoutManager(this, (int) m_countColumns));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        int gridColor = ContextCompat.getColor(this, R.color.grid_spacing);

        minsField.addItemDecoration(new GridSpacingItemDecoration(m_countColumns, spacingInPixels, true, gridColor));


        MineFieldAdapter adapter = new MineFieldAdapter(this::openCell, this::victory, this::defeat, m_countRows, m_countColumns);
        minsField.setAdapter(adapter);

        openCell();
    }
    public void openCell(View view) {
        openCell();
    }
    public void openCell() {
        TextView textView = findViewById(R.id.free_cell);
        textView.setText(getString(R.string.free_cell) + String.valueOf(m_field.getCountEmptyClose()));
    }

    public void victory(View view) {
        Intent intent = new Intent(getApplicationContext(), Victory.class);

        intent.putExtra("count_rows", m_countRows);
        intent.putExtra("count_columns", m_countColumns);
        intent.putExtra("count_mins", m_countMins);
        intent.putExtra("count_show_second", m_showSeconds);

        startActivity(intent);
        finish();
    }

    public void defeat(View view) {
        Intent intent = new Intent(getApplicationContext(), Defeat.class);

        intent.putExtra("percent_victory", m_field.getPercentVictory());
        intent.putExtra("count_rows", m_countRows);
        intent.putExtra("count_columns", m_countColumns);
        intent.putExtra("count_mins", m_countMins);
        intent.putExtra("count_show_second", m_showSeconds);

        startActivity(intent);
        finish();
    }
}