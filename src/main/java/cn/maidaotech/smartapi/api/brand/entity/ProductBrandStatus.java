package cn.maidaotech.smartapi.api.brand.entity;

public enum ProductBrandStatus {
    NORMAL(1), FORBID(2);

    private byte value;

    ProductBrandStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }


    public static ProductBrandStatus find(int v) {
        for (ProductBrandStatus status : ProductBrandStatus.values()) {
            if (v == status.value) {
                return status;
            }
        }


        return null;
    }


}
