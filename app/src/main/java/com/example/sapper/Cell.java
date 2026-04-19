package com.example.sapper;

import android.content.Context;
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

    public boolean open() {
        boolean isOpen = m_field.open(m_indexRow, m_indexColumn);
        updateShow();
        return isOpen;
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