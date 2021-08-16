package cn.maidaotech.smartapi.common.context;

import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminSessionWrapper;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.ErrorCode;

import java.util.Objects;

public class MerchantAdminContexts extends Contexts {

    public static Integer requestAdminId() throws ServiceException {
        return requestMerchantAdmin().getId();
    }

    public static Integer sessionAdminId() throws ServiceException {
        MerchantAdmin admin = sessionMerchantAdmin();
        if (admin == null) {
            return null;
        }
        return admin.getId();
    }

    public static MerchantAdmin requestMerchantAdmin() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ErrorCode.ERR_SESSION_EXPIRES);
        }
        MerchantAdmin admin = sessionMerchantAdmin();
        if (admin == null) {
            throw new ArgumentServiceException("need userId");
        }
        return admin;
    }

    public static MerchantAdmin sessionMerchantAdmin() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrapper wrapper = context.getSession();
        MerchantAdmin admin = null;

        if (wrapper instanceof MerchantAdminSessionWrapper) {
            admin = ((MerchantAdminSessionWrapper) wrapper).getMerchantAdmin();
        }
        return admin;
    }

    public static Merchant sessionMerchant() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrapper wrap = context.getSession();
        Merchant merchant = null;

        if (wrap instanceof MerchantAdminSessionWrapper) {
            merchant = ((MerchantAdminSessionWrapper) wrap).getMerchant();
        }
        return merchant;
    }

    public static Merchant requireMerchant() throws ServiceException {
        Merchant merchant = sessionMerchant();
        if (Objects.isNull(merchant)) {
            throw new ArgumentServiceException("store not found");
        }
        return merchant;
    }
}
