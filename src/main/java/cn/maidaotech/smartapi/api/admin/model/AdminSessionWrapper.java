package cn.maidaotech.smartapi.api.admin.model;

import cn.maidaotech.smartapi.common.context.SessionWrapper;

public class AdminSessionWrapper implements SessionWrapper {
    private Admin admin;
    private AdminSession adminSession;
    private AdminRole role;

    public AdminSessionWrapper() {
    }

    public AdminSessionWrapper(Admin admin, AdminSession adminSession, AdminRole role) {
        this.admin = admin;
        this.adminSession = adminSession;
        this.role = role;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public AdminSession getAdminSession() {
        return adminSession;
    }

    public void setAdminSession(AdminSession adminSession) {
        this.adminSession = adminSession;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }
}
