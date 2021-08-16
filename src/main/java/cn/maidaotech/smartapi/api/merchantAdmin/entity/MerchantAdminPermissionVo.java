package cn.maidaotech.smartapi.api.merchantAdmin.entity;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.common.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class MerchantAdminPermissionVo {

    private static List<Permission> list = null;

    public static List<Permission> initOmsPermissions() {
        list = new ArrayList<>();
        for (MerchantAdminPermission p : MerchantAdminPermission.values()) {
            list.add(new Permission(p.name(), p.getVal(), p.getLevel()));
        }
        return list;
    }

    public static List<Permission> initOmsPermissionsByPs(List<String> ps) {
        List<Permission> list = initOmsPermissions();
        List<Permission> result = new ArrayList<>();
        if (ps.size() > 0) {
            for (String s : ps) {
                for (Permission p : list) {
                    if (s.equals(p.getKey()))
                        result.add(p);
                }
            }
        }
        return result;
    }

}
