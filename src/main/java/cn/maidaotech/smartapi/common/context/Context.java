package cn.maidaotech.smartapi.common.context;

public class Context {
    private String locale;
    private SessionWrapper session;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public SessionWrapper getSession() {
        return session;
    }

    public void setSession(SessionWrapper session) {
        this.session = session;
    }
}
