package cn.maidaotech.smartapi.api.bill.entity;

public enum BillStatus {
    PASSED(1), FAILED(2), WAITING(3);

    private byte value;

    BillStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }

    public static BillStatus find(byte value) {
        for (BillStatus status : BillStatus.values()) {
            if (value == status.value) {
                return status;
            }
        }
        return null;
    }

}
