package cn.maidaotech.smartapi.api.trade.entity;

public enum TradePayment {
    WECHAT(1), OFFLINE(2), ALIPAY(3);

    private byte value;

    TradePayment(int value) {
        this.value = (byte) value;
    }

    Byte value() {
        return this.value;
    }

    public static TradePayment find(byte value) {
        for (TradePayment tradePayment : TradePayment.values()) {
            if (tradePayment.value == value) {
                return tradePayment;
            }
        }
        return null;
    }
}
