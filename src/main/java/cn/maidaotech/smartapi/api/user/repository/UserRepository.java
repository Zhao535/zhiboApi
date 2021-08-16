package cn.maidaotech.smartapi.api.user.repository;

import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface UserRepository extends BaseRepository<User, Integer> {
    User findByMobile(String mobile);


}
