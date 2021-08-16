package cn.maidaotech.smartapi.common.exception;

import cn.maidaotech.smartapi.common.model.ErrorCode;

import java.util.Collections;
import java.util.Map;

public class PermissionDeniedServiceException extends ServiceException {
    private static final long serialVersionUID = 686137446373873065L;

    public PermissionDeniedServiceException() {
        super(ErrorCode.ERR_PERMISSION_DENIED);
    }

    public PermissionDeniedServiceException(String msg) {
        this();
        Map<String, Object> errorData = Collections.singletonMap("detail", (Object) msg);
        setErrorData(errorData);
    }


}
