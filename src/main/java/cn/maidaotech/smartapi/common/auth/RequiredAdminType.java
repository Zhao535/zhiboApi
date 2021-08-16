package cn.maidaotech.smartapi.common.auth;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredAdminType {

    AdminType[] value() default {AdminType.NONE};

}
