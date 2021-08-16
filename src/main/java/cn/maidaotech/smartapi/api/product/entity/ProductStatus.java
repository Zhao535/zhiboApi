package cn.maidaotech.smartapi.api.product.entity;

public enum  ProductStatus {
    NORMAL(1), FORBID(2);

    private byte value;

    ProductStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }


    public static ProductStatus find(int value) {
        for (ProductStatus status : ProductStatus.values()) {
            if (value == status.value) {
                return status;
            }
        }


        return null;
    }






}
