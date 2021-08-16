package cn.maidaotech.smartapi.api.merchantAdmin.entity;

import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminRole;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminSession;
import cn.maidaotech.smartapi.common.context.SessionWrapper;

public class MerchantAdminSessionWrapper implements SessionWrapper {

    private MerchantAdmin merchantAdmin;

    private MerchantAdminSession merchantAdminSession;

    private MerchantAdminRole merchantAdminRole;

    private Merchant merchant;

    public MerchantAdminSessionWrapper() {
    }

    public MerchantAdminSessionWrapper(MerchantAdmin merchantAdmin, MerchantAdminSession merchantAdminSession, MerchantAdminRole merchantAdminRole,Merchant merchant) {
        this.merchantAdmin = merchantAdmin;
        this.merchantAdminSession = merchantAdminSession;
        this.merchantAdminRole = merchantAdminRole;
        this.merchant=merchant;

    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public MerchantAdmin getMerchantAdmin() {
        return merchantAdmin;
    }

    public void setMerchantAdmin(MerchantAdmin merchantAdmin) {
        this.merchantAdmin = merchantAdmin;
    }

    public MerchantAdminSession getMerchantAdminSession() {
        return merchantAdminSession;
    }

    public void setMerchantAdminSession(MerchantAdminSession merchantAdminSession) {
        this.merchantAdminSession = merchantAdminSession;
    }

    public MerchantAdminRole getMerchantAdminRole() {
        return merchantAdminRole;
    }

    public void setMerchantAdminRole(MerchantAdminRole merchantAdminRole) {
        this.merchantAdminRole = merchantAdminRole;
    }
}
