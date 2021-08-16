package cn.maidaotech.smartapi.common.mail;


public interface MailService {

    boolean send(MailHelper.MailInfo mail, boolean async);

}