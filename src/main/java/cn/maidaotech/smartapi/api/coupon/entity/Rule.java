package cn.maidaotech.smartapi.api.coupon.entity;

import java.util.List;

public class Rule {

    private Byte type;//(1.每减，2.满减，3.直减)
    private List<Integer> values;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

}
