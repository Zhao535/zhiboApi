package cn.maidaotech.smartapi.api.account.entity;

public enum AccountType {

    BANKCARD(1), WECHANT(2), ALIPAY(3);

    private byte value;

    AccountType(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return this.value;
    }

    public static AccountType find(byte value) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.value == value) {
                return accountType;
            }
        }
        return null;
    }

}
