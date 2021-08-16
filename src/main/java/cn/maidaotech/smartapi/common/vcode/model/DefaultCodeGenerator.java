package cn.maidaotech.smartapi.common.vcode.model;

import cn.maidaotech.smartapi.common.utils.StringUtils;

public class DefaultCodeGenerator implements CodeGenerator {
    private static final DefaultCodeGenerator INSTANCE = new DefaultCodeGenerator();

    public static DefaultCodeGenerator getInstance() {
        return INSTANCE;
    }

    private DefaultCodeGenerator() {

    }

    @Override
    public String generatorCode() {
        return StringUtils.randomNumeric(6);
    }
}
