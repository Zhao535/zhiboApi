package cn.maidaotech.smartapi.common.auth;



import cn.maidaotech.smartapi.api.admin.model.AdminPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RequiredPermission {

    AdminPermission[] value() default {AdminPermission.NONE};

}
