package cn.maidaotech.smartapi.api.user.service;



import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.api.user.model.UserSessionWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {

    void sendCode(String mobile )throws Exception;

    void sendChangeMobileCode(String mobile )throws Exception;

    void sendRegisterCode(String mobile )throws Exception;

    void save(User user,String code ) throws Exception;

    UserSessionWrapper signIn(String userName, String passWrod, String coed) throws Exception;

    void updatePassword(String username, String passwrod,String newPassword) throws Exception;

    UserSessionWrapper authenticate(String token) throws Exception;

    User item(Integer id) throws Exception;

    Page<User> items(PageRequest pageRequest) throws Exception;

    List<User> findAllById (Collection<Integer> userIds) throws Exception;

    void changeMobile(String mobile,String newMobile,String code) throws Exception;



}
