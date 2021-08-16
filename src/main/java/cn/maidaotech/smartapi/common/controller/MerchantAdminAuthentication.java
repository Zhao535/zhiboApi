package cn.maidaotech.smartapi.common.controller;


import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminPermission;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MerchantAdminAuthentication {
    MerchantAdminPermission[] value() default {MerchantAdminPermission.NONE};
}
