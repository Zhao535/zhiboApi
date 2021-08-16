package cn.maidaotech.smartapi.common.vcode.model;

import cn.maidaotech.smartapi.common.utils.StringUtils;

public class MailCodeGenerator implements CodeGenerator {
    private static final MailCodeGenerator INSTANCE =new MailCodeGenerator();

    public static MailCodeGenerator getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public String generatorCode() {
        return StringUtils.randomAlphanumeric(8);
    }
}
