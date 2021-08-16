package cn.maidaotech.smartapi.api.coupon.entity;

public enum RuleType {
    //1.满减，2.每减
    FULL(1),
    EACH(2),
    LAPSE(3);


    private byte value;

    private RuleType(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return value;
    }

    public static RuleType findRuleType(Byte value) {
        if (value == null) {
            return null;
        }
        for (RuleType ruleType : RuleType.values()) {
            if (value.equals(ruleType.value)) {
                return ruleType;
            }
        }
        return null;
    }

}
