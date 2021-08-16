package cn.maidaotech.smartapi.common.vcode.model;

import java.util.Map;

public class CodePayload {
    private String subject;
    private Map<String, Object> context;
    private CodeDeliveryType deliveryType;
    private String extra;
    private CodeGenerator generator;


    public String getSubject() {
        return subject;
    }

    public CodePayload setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public CodePayload setContext(Map<String, Object> context) {
        this.context = context;
        return this;
    }

    public CodeDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public CodePayload setDeliveryType(CodeDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public CodePayload setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public CodeGenerator getGenerator() {
        return generator;
    }

    public CodePayload setGenerator(CodeGenerator generator) {
        this.generator = generator;
        return this;
    }
}
