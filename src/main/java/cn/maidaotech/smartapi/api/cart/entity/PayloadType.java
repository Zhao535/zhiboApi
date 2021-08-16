package cn.maidaotech.smartapi.api.cart.entity;

public enum PayloadType {
    NORMAL(1), SECKILL(2);

    private byte value;

    PayloadType(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return value;
    }

}
