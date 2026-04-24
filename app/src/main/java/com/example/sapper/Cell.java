package com.example.sapper;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

public class Cell extends AppCompatButton {
    private int m_indexRow;
    private int m_indexColumn;
    private MinsField m_field;
    private View.OnClickListener m_openCell;
    private View.OnClickListener m_victory;
    private View.OnClickListener m_defeat;

    public Cell(Context context, MinsField field,
                View.OnClickListener openCell,
                View.OnClickListener victory,
                View.OnClickListener defeat) {
        super(context);
        m_field = field;
        m_openCell = openCell;
        m_victory = victory;
        m_defeat = defeat;
        setOnClickListener(this::onClickCell);
        setOnLongClickListener(this::onLongClickCell);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void setIndexRow(int indexRow) {
        m_indexRow = indexRow;
    }

    public void setIndexColumn(int indexColumn) {
        m_indexColumn = indexColumn;
    }

    public void open(View view) {
        boolean isMin = m_field.open(m_indexRow, m_indexColumn);
        Context context = getContext();

        updateHide();

        if (context instanceof Activity) {
            Activity activity = (Activity) context;

            if (isMin) {
                m_defeat.onClick(view);
            } else if (m_field.isVictory()) {
                m_victory.onClick(view);
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
            if (isMin()) {
                setText(R.string.min_open);
            } else {
                setText(String.valueOf(getCountMinsNear()));
            }
        } else if (isFlag()) {
            setText(R.string.flag);
        } else {
            setText(R.string.space);
        }
    }

    public void updateShow() {
        if (isOpen()) {
            if (isMin()) {
                setText(R.string.min_open);
            } else {
                setText(String.valueOf(getCountMinsNear()));
            }
        } else if (isFlag()) {
            if (isMin()) {
                setText(R.string.flag_finish);
            } else {
                setText(R.string.flag);
            }
        } else if (isMin()) {
            setText(R.string.min_close);
        } else {
            setText(R.string.space);
        }
    }

    public void onClickCell(View view) {
        open(view);
        m_openCell.onClick(view);
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