package cn.maidaotech.smartapi.common.context;

import cn.maidaotech.smartapi.api.admin.model.Admin;
import cn.maidaotech.smartapi.api.admin.model.AdminSessionWrapper;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.ErrorCode;

public class AdminContexts extends Contexts {

    public static Integer requestAdminId() throws ServiceException {
        return requestAdmin().getId();
    }

    public static Integer sessionAdminId() throws ServiceException {
        Admin admin = sessionAdmin();
        if (admin == null) {
            return null;
        }
        return admin.getId();
    }

    public static Admin requestAdmin() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ErrorCode.ERR_SESSION_EXPIRES);
        }
        Admin admin = sessionAdmin();
        if (admin == null) {
            throw new ArgumentServiceException("need userId");
        }
        return admin;
    }

    public static Admin sessionAdmin() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrapper wrapper = context.getSession();
        Admin admin = null;

        if (wrapper instanceof AdminSessionWrapper) {
            admin = ((AdminSessionWrapper) wrapper).getAdmin();
        }
        return admin;
    }


}
