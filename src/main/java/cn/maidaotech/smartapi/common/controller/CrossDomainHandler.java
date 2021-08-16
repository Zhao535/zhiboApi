package cn.maidaotech.smartapi.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainHandler {
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String MATCH_ALL = "*";

    public static final boolean handle(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, MATCH_ALL);
        return false;
    }

}
