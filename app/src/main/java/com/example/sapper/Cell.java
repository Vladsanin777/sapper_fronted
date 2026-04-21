package com.example.sapper;

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
    }

    public void setIndexRow(int indexRow) {
        m_indexRow = indexRow;
    }

    public void setIndexColumn(int indexColumn) {
        m_indexColumn = indexColumn;
    }

    public void open() {
        boolean isMin = m_field.open(m_indexRow, m_indexColumn);
        updateShow();

        if (isMin) {
            Intent intent = new Intent(getContext(), Result.class);

            intent.putExtra("is_victory", false);
            Log.d("rows", String.valueOf(m_field.getCountRows()));
            intent.putExtra("count_rows", m_field.getCountRows());
            intent.putExtra("count_columns",m_field.getCountCols());
            intent.putExtra("count_mins",m_field.getCountMins());

            // startActivity(getContext(), intent, m_bundle);
            getContext().startActivity(intent);
        }
    }

    public boolean isOpen() {
        return m_field.isOpen(m_indexRow, m_indexColumn);
    }

    public void upFlag() {
        m_field.upFlag(m_indexRow, m_indexColumn);
    }

    public void downFlag() {
        m_field.downFlag(m_indexRow, m_indexColumn);
    }

    public boolean isFlag() {
        return m_field.isFlag(m_indexRow, m_indexColumn);
    }

    public boolean isMin() {
        return m_field.isMin(m_indexRow, m_indexColumn);
    }

    public void updateHide() {
        if (isOpen()) {
            setText("-");
        } else if (isFlag()) {
            setText("/");
        } else {
            setText(" ");
        }
    }

    public void updateShow() {
        if (isOpen()) {
            setText("-");
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
}