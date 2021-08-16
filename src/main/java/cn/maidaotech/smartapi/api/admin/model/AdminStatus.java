package cn.maidaotech.smartapi.api.admin.model;

public enum AdminStatus {
    NORMAL(1), FORBID(2);

    private byte value;

    private AdminStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }

    public static AdminStatus find(Byte value) {
        if (value == null) {
            return null;
        }
        for (AdminStatus adminStatus : AdminStatus.values()) {
            if (adminStatus.value() == value.byteValue()) {
                return adminStatus;
            }
        }
        return null;
    }
}
