package cn.maidaotech.smartapi.api.account.service;

import cn.maidaotech.smartapi.api.account.model.Account;

import java.util.List;

public interface AccountService {

    List<Account> items() throws Exception;

    void save(Account account) throws Exception;

    void remove(Integer id) throws Exception;


}
