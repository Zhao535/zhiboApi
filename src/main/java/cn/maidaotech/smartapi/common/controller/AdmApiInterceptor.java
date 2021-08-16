package cn.maidaotech.smartapi.common.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.admin.model.AdminSessionWrapper;
import cn.maidaotech.smartapi.api.admin.service.AdminService;
import cn.maidaotech.smartapi.common.context.Context;
import cn.maidaotech.smartapi.common.context.Contexts;
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

import static cn.maidaotech.smartapi.common.controller.WebappKeys.KEY_ADMIN_TOKEN;


@ControllerAdvice
public class AdmApiInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AdminAuthentication requireAuth = handlerMethod.getMethodAnnotation(AdminAuthentication.class);
        if (requireAuth == null) {
            requireAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AdminAuthentication.class);
            if (requireAuth == null) {
                return true;
            }
        }
        String token = WebUtils.getHeader(request, KEY_ADMIN_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new SessionServiceException();
        }
        AdminSessionWrapper wrapper = adminService.authenticate(token);
        if (wrapper == null) {
            throw new SessionServiceException();
        }
        AdminPermission[] adminPermissions = requireAuth.value();
        boolean allow = false;
        if (adminPermissions.length == 0 || Arrays.asList(adminPermissions).contains(AdminPermission.NONE)) {
            allow = true;
        }
        for (AdminPermission adminPermission : adminPermissions) {
            if (wrapper.getRole().getPermissions().contains(adminPermission.name())) {
                allow = true;
            }
        }
        if (!allow) {
            throw new PermissionDeniedServiceException();
        }

        Context context = new Context();
        context.setSession(wrapper);
        Contexts.set(context);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
