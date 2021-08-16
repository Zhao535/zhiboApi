package cn.maidaotech.smartapi.api.admin.repository;

import cn.maidaotech.smartapi.api.admin.model.Admin;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;


public interface AdminRepository extends BaseRepository<Admin, Integer> {

    Admin findByMobile(String mobile);

    Admin findByEmail(String email);




}
