package cn.maidaotech.smartapi.common.controller;


import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminPermission;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminSessionWrapper;
import cn.maidaotech.smartapi.api.merchantAdmin.service.MerchantAdminService;
import cn.maidaotech.smartapi.common.context.Context;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.exception.PermissionDeniedServiceException;
import cn.maidaotech.smartapi.common.exception.SessionServiceException;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import cn.maidaotech.smartapi.common.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static cn.maidaotech.smartapi.common.controller.WebappKeys.*;

@ControllerAdvice
public class MerchantAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private MerchantAdminService merchantAdminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        MerchantAdminAuthentication requireAuth = handlerMethod.getMethodAnnotation(MerchantAdminAuthentication.class);
        if (requireAuth == null) {
            requireAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(MerchantAdminAuthentication.class);
            if (requireAuth == null) {
                return true;
            }
        }
        String token = WebUtils.getHeader(request, KEY_MERCHANT_ADMIN_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new SessionServiceException();
        }
        MerchantAdminSessionWrapper wrapper = merchantAdminService.authenticate(token);
        if (wrapper == null) {
            throw new SessionServiceException();
        }
        MerchantAdminPermission[] adminPermissions = requireAuth.value();
        boolean allow = false;
        if (adminPermissions.length == 0 || Arrays.asList(adminPermissions).contains(MerchantAdminPermission.NONE)) {
            allow = true;
        }
        for (MerchantAdminPermission adminPermission : adminPermissions) {
            if (wrapper.getMerchantAdminRole().getPermissions().contains(adminPermission.name())) {
                allow = true;
            }
        }
        if (!allow) {
            throw new PermissionDeniedServiceException();
        }

        Context context = new Context();
        context.setSession(wrapper);
        MerchantAdminContexts.set(context);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
