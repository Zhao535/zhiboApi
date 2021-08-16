package cn.maidaotech.smartapi.api.usrCollect.entity;

public enum CollectType {
    PRODUCT(1), ARTICLE(2), SCENE(3), MERCHANT(4);

    private byte value;

    CollectType(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }



}
