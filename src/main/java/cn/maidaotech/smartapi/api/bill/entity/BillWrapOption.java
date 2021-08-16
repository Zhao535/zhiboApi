package cn.maidaotech.smartapi.api.bill.entity;

public class BillWrapOption {

    private boolean withTrade = false;
    private boolean withProduct = false;
    private boolean withMerchant = false;

    public BillWrapOption() {
    }

    public boolean isWithTrade() {
        return withTrade;
    }

    private BillWrapOption setWithTrade(boolean withTrade) {
        this.withTrade = withTrade;
        return this;
    }

    public boolean isWithProduct() {
        return withProduct;
    }

    private BillWrapOption setWithProduct(boolean withProduct) {
        this.withProduct = withProduct;
        return this;
    }

    public boolean isWithMerchant() {
        return withMerchant;
    }

    private BillWrapOption setWithMerchant(boolean withMerchant) {
        this.withMerchant = withMerchant;
        return this;
    }

    public static final BillWrapOption DEFAULT = new BillWrapOption().setWithMerchant(true).setWithTrade(true);
}
