package cn.maidaotech.smartapi.api.template.entity;

public enum TemplateStatus {
    NORMAL(1), FORBID(2);

    private byte value;

    TemplateStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }


    public static TemplateStatus find(int value) {
        for (TemplateStatus status : TemplateStatus.values()) {
            if (value == status.value) {
                return status;
            }
        }


        return null;
    }
}
