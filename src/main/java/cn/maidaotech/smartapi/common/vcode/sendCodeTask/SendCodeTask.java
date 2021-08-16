package cn.maidaotech.smartapi.common.vcode.sendCodeTask;

import cn.maidaotech.smartapi.common.vcode.model.Code;

public class SendCodeTask implements Runnable {
    private String subject;
    private String username;
    private Code code;

    public SendCodeTask(String subject, String username, Code code) {
        this.subject = subject;
        this.username = username;
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    @Override
    public void run() {
    }
}
