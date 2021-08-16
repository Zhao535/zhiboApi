package cn.maidaotech.smartapi.common.exception;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public class SessionServiceException extends ServiceException {
    private static final long serialVersionUID = 7404151147635683478L;

    public SessionServiceException() {
        super(ErrorCode.ERR_SESSION_EXPIRES);
    }

}

