package cn.maidaotech.smartapi.api.merchantAdmin.service;

import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminRoleQo;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminSessionQo;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminSessionWrapper;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminRole;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminSession;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MerchantAdminService {

    void saveMerchantAdmin(MerchantAdmin merchantAdmin) throws Exception;

    MerchantAdminSessionWrapper signin(String mobile, String password) throws Exception;

    MerchantAdminSessionWrapper authenticate(String token) throws Exception;

    void changeStatus(Integer Id) throws Exception;

    List<MerchantAdmin> findByMerchantId(Integer merchantId) throws Exception;

    MerchantAdmin getById(Integer id) throws Exception;

    List<MerchantAdmin> findMerchantAdmins (Integer merchantAdminId) throws Exception;

    List<MerchantAdmin> items() throws Exception;

    List<MerchantAdmin> itemsForMerchantAdmin() throws Exception;

    Page<MerchantAdminSession> singInLog(MerchantAdminSessionQo qo) throws Exception;

    void saveRole(MerchantAdminRole role) throws Exception;

    void resetPassword(String mobile,String newPassword)throws Exception;

    Page<MerchantAdminRole> roles(MerchantAdminRoleQo qo) throws Exception;

    MerchantAdminRole role(Integer id)throws Exception;

    void removeRole(Integer id)throws Exception;

    List<MerchantAdminRole> roleList() throws Exception;
}
