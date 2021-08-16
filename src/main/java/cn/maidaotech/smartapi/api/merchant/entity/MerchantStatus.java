package cn.maidaotech.smartapi.api.merchant.entity;

public enum MerchantStatus {
    NORMAL(1), FORBID(2);

    private byte value;


    public byte value() {
        return this.value;
    }

    MerchantStatus(int value) {
        this.value = (byte) value;
    }

    public static MerchantStatus find(byte value) {
        for (MerchantStatus status : MerchantStatus.values()) {
            if (value == status.value) {
                return status;
            }
        }
        return null;
    }

}
