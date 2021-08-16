package cn.maidaotech.smartapi.common.controller;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.context.Contexts;
import cn.maidaotech.smartapi.common.exception.RuntimeServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.resources.LocaleBundles;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionInterceptor {
    private static final RuntimeServiceException DEFAULT_EXCEPTION = new RuntimeServiceException();

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleError(HttpServletRequest request, HandlerMethod handlerMethod, Throwable ex) {
        if (!(ex instanceof ServiceException)) {
            ApiLog.log(request, null);
        }
        ServiceException se;
        if (ex instanceof ServiceException) {
            se = (ServiceException) ex;
        } else {
            L.error(ex);
            se = DEFAULT_EXCEPTION;
        }
        int errorCode = se.getErrorCode();
        String errorMsg = LocaleBundles.getWithArrayParams(Contexts.ensureLocale(), "err." + errorCode,
                se.getErrorParams());
        Map<String, Object> result = new HashMap<>();
        result.put("errcode", errorCode);
        result.put("errmsg", errorMsg);
        result.put("errdata", se.getErrorData());
        return new ModelAndView(new JSONView(result));
    }

}
