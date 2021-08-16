package cn.maidaotech.smartapi.common.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminAuthentication {

    AdminPermission[] value() default {AdminPermission.NONE};

}
