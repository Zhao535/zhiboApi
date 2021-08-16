package cn.maidaotech.smartapi.common.context;


import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.api.user.model.UserSessionWrapper;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.ErrorCode;

public class UserContexts extends Contexts {

    public static Integer requestUserId() throws ServiceException {
        return requestUser().getId();
    }

    public static Integer  sessionUserId() throws ServiceException {
        User user = sessionUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public static User requestUser() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ErrorCode.ERR_SESSION_EXPIRES);
        }
        User user = sessionUser();
        if (user == null) {
            throw new ArgumentServiceException("need userId");
        }
        return user;
    }

    public static User sessionUser() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrapper wrapper = context.getSession();
        User user = null;
        if (wrapper instanceof UserSessionWrapper) {
            user = ((UserSessionWrapper) wrapper).getUser();
        }
        return user;
    }

}
