package cn.maidaotech.smartapi.api.trade.entity;

public enum TradeStatus {
    UNPAID(1), UNSHIPPED(2), UNRECEIPTED(3), FINISHED(4);

    private byte value;

    TradeStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }


    public static TradeStatus find(byte value) {
        for (TradeStatus status : TradeStatus.values()) {
            if (value == status.value) {
                return status;
            }
        }
        return null;
    }


}
