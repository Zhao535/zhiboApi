package cn.maidaotech.smartapi.common.exception;


import cn.maidaotech.smartapi.common.model.ErrorCode;


public class DetailedServiceException extends ServiceException {
    private static final long serialVersionUID = -7140764920335762702L;
    private String errorMsg;

    public DetailedServiceException(String errorMsg) {
        super(ErrorCode.ERR_DETAILED_MESSAGE, errorMsg);
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

}



