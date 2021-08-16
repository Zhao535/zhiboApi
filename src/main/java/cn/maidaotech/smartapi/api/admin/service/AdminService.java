package cn.maidaotech.smartapi.api.admin.service;

import cn.maidaotech.smartapi.api.admin.model.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminService {
    void save(Admin admin) throws Exception; //创建管理员

    AdminSessionWrapper signIn(String userName, String password) throws Exception;//登录

    AdminSessionWrapper authenticate(String token) throws Exception;//验证token

    Page<Admin> findAll(AdminQo qo) throws Exception;// 查找管理员

    void changeAdminStatus(Integer id) throws Exception;

    Admin findById(Integer id) throws Exception;

    Admin getById(Integer id) throws Exception;

    Page<AdminSession> adminSessions(AdminSessionQo qo) throws Exception;//登录记录

    void saveRole(AdminRole role) throws Exception;//创建权限

    void removeRole(Integer id) throws Exception;// 移除权限

    AdminRole role(Integer id) throws Exception;//查找一个权限

    Admin changeRole(Integer adminId, Integer roleId) throws Exception;//修改管理员的权限组

    void changePassword(String oldPassword, String repeatPassword) throws Exception;

    Map<Integer, Admin> findByIdIn(Set<Integer> ids);

    List<AdminRole> roles(boolean init) throws Exception;

}
