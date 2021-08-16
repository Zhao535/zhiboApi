package cn.maidaotech.smartapi.api.category.entity;

public enum CategoryStatus {
    NORMAL(1), FORBID(2);
    private byte value;

    CategoryStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }

    public  static  CategoryStatus find(int value) {
        for (CategoryStatus categoryStatus : CategoryStatus.values()) {
            if (categoryStatus.value == value) {
                return categoryStatus;
            }
        }
        return null;
    }


}
