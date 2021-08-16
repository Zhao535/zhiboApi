package cn.maidaotech.smartapi.api.merchantAdmin.entity;

public enum MerchantAdminStatus {
    NORMAL(1), FORBID(2);

    private byte value;


    public byte value() {
        return this.value;
    }

    MerchantAdminStatus(int value) {
        this.value = (byte) value;
    }

    public static MerchantAdminStatus find(byte value) {
        for (MerchantAdminStatus status : MerchantAdminStatus.values()) {
            if (value == status.value) {
                return status;
            }
        }
        return null;
    }
}
