package com.example.sapper;

public class MinsField {
    public enum Mode {
        BASIC, FLAG
    }
    private long m_nativePtr = 0;
    private Mode m_mode;

    public MinsField(long rows, long cols, long mins) {
        init(rows, cols, mins);
    }

    public void finalize() {
        destroy();
    }

    public void modeBasic() {
        m_mode = Mode.BASIC;
    }

    public void modeFlag() {
        m_mode = Mode.FLAG;
    }

    public Mode getMode() {
        return m_mode;
    }

    private native void init(long rows, long cols, long mins);

    public native byte getCountMins(long row, long col);

    public native boolean isMin(long row, long col);

    public native void upFlag(long row , long col);

    public native void downFlag(long row , long col);

    public native boolean isFlag(long row, long col);

    public native long getCountRows();

    public native long getCountCols();

    public native boolean isLive();

    public native long getCountFree();

    public native boolean isVictory();

    public native boolean open(long row, long col);

    public native boolean isOpen(long row, long col);

    private native void destroy();
}