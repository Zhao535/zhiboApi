package cn.maidaotech.smartapi.common.controller;

import cn.maidaotech.smartapi.api.user.model.UserSessionWrapper;
import cn.maidaotech.smartapi.api.user.service.UserService;
import cn.maidaotech.smartapi.common.context.Context;
import cn.maidaotech.smartapi.common.context.UserContexts;
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

import static cn.maidaotech.smartapi.common.controller.WebappKeys.KEY_USER_TOKEN;


@ControllerAdvice
public class UsrApiInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!(handler instanceof HandlerMethod)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        UserAuthentication requireAuth = handlerMethod.getMethodAnnotation(UserAuthentication.class);
        if (requireAuth == null) {
            requireAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(UserAuthentication.class);
            if (requireAuth == null) {
                return true;
            }
        }
        String token = WebUtils.getHeader(request, KEY_USER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new SessionServiceException();
        }
        UserSessionWrapper wrapper = userService.authenticate(token);
        if (wrapper == null) {
            throw new SessionServiceException();
        }
        Context context = new Context();
        context.setSession(wrapper);
        UserContexts.set(context);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
