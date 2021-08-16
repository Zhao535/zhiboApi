package cn.maidaotech.smartapi.api.merchant.entity;

public class MerchantWrapOption {

    private boolean WithMerchantAdmin;
    private boolean WithMerchantExpiredAt;
    private boolean WithMerchantProductNum;

    private MerchantWrapOption() {

    }


    public boolean isWithMerchantAdmin() {
        return WithMerchantAdmin;
    }

    public boolean isWithMerchantExpiredAt() {
        return WithMerchantAdmin;
    }

    public boolean isWithMerchantProductNum() {
        return WithMerchantProductNum;
    }


    public MerchantWrapOption setWithMerchantAdmin(boolean withMerchantAdmin) {
        WithMerchantAdmin = withMerchantAdmin;
        return this;
    }

    public MerchantWrapOption setWithMerchantExpiredAt(boolean WithMerchantExpiredAt) {
        this.WithMerchantExpiredAt = WithMerchantExpiredAt;
        return this;
    }

    public MerchantWrapOption setWithMerchantProductNum(boolean withMerchantProductNum) {
        this.WithMerchantProductNum = withMerchantProductNum;
        return this;
    }


    private static final MerchantWrapOption DEFAULT = new MerchantWrapOption().setWithMerchantAdmin(true).setWithMerchantExpiredAt(true).setWithMerchantProductNum(true);

    public static MerchantWrapOption getDefault() {
        return DEFAULT;
    }
}
