package cn.maidaotech.smartapi.common.exception;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public class FrequencyServiceException extends ServiceException {
    private static final long serialVersionUID = 7404151147635683478L;

    public FrequencyServiceException() {
        super(ErrorCode.ERR_OPERATION_TOO_FREQUENT);
    }

}

