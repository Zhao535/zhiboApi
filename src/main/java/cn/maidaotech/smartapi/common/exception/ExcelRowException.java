package cn.maidaotech.smartapi.common.exception;

public class ExcelRowException extends RuntimeException {
    private static final long serialVersionUID = 1934398187432464216L;
    private int rowIndex;

    public ExcelRowException(String message, int rowIndex) {
        super(message);
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

}

