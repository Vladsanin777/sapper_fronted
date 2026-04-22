package com.example.sapper;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

public class Cell extends AppCompatButton {
    private int m_indexRow;
    private int m_indexColumn;
    private MinsField m_field;

    public Cell(Context context, MinsField field) {
        super(context);
        m_field = field;
        setOnClickListener(this::onClickCell);
        setOnLongClickListener(this::onLongClickCell);
    }

    public void setIndexRow(int indexRow) {
        m_indexRow = indexRow;
    }

    public void setIndexColumn(int indexColumn) {
        m_indexColumn = indexColumn;
    }

    public void open() {
        boolean isMin = m_field.open(m_indexRow, m_indexColumn);
        Context context = getContext();

        updateShow();

        if (context instanceof Activity) {
            Activity activity = (Activity) context;

            if (isMin) {
                Intent intent = new Intent(getContext(), Defeat.class);

                intent.putExtra("percent_victory", m_field.getPercentVictory());
                intent.putExtra("count_rows", m_field.getCountRows());
                intent.putExtra("count_columns", m_field.getCountCols());
                intent.putExtra("count_mins", m_field.getCountMins());

                context.startActivity(intent);
                activity.finish();
            } else if (m_field.isVictory()) {
                Intent intent = new Intent(getContext(), Victory.class);

                intent.putExtra("count_rows", m_field.getCountRows());
                intent.putExtra("count_columns", m_field.getCountCols());
                intent.putExtra("count_mins", m_field.getCountMins());

                context.startActivity(intent);
                activity.finish();
            }
        }
    }

    public boolean isOpen() {
        return m_field.isOpen(m_indexRow, m_indexColumn);
    }

    public void upFlag() {
        m_field.upFlag(m_indexRow, m_indexColumn);
        updateHide();
    }

    public void downFlag() {
        m_field.downFlag(m_indexRow, m_indexColumn);
        updateHide();
    }

    public boolean isFlag() {
        return m_field.isFlag(m_indexRow, m_indexColumn);
    }

    public boolean isMin() {
        return m_field.isMin(m_indexRow, m_indexColumn);
    }

    public byte getCountMinsNear() {
        return m_field.getCountMinsNear(m_indexRow, m_indexColumn);
    }

    public void updateHide() {
        if (isOpen()) {
            setText(String.valueOf(getCountMinsNear()));
        } else if (isFlag()) {
            setText("/");
        } else {
            setText(" ");
        }
    }

    public void updateShow() {
        if (isOpen()) {
            setText(String.valueOf(getCountMinsNear()));
        } else if (isFlag()) {
            if (isMin()) {
                setText("+");
            } else {
                setText("/");
            }
        } else if (isMin()) {
            setText("*");
        } else {
            setText(" ");
        }
    }

    public void onClickCell(View view) {
        switch (m_field.getMode()) {
            case BASIC:
                open();
                break;
            case FLAG:
                if (isFlag()) {
                    downFlag();
                } else {
                    upFlag();
                }
                break;
        }
    }

    public boolean onLongClickCell(View view) {
        if (isFlag()) {
            downFlag();
        } else {
            upFlag();
        }
        return true;
    }
}