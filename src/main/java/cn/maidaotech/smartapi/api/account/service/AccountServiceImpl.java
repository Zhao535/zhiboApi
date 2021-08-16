package cn.maidaotech.smartapi.api.account.service;


import cn.maidaotech.smartapi.api.account.entity.AccountErrors;
import cn.maidaotech.smartapi.api.account.entity.AccountType;
import cn.maidaotech.smartapi.api.account.model.Account;
import cn.maidaotech.smartapi.api.account.repository.AccountRepository;
import cn.maidaotech.smartapi.api.bill.model.Bill;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.PermissionDeniedServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService, AccountErrors {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Account> accountCache;

    @PostConstruct
    public void init() {
        accountCache = kvCacheFactory.create(new CacheOptions("account", 1, 3600), new RepositoryProvider<Integer, Account>() {

            @Override
            public Account findByKey(Integer id) throws Exception {
                return accountRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, Account> findByKeys(Collection<Integer> ids) throws Exception {
                List<Account> accounts = accountRepository.findAllById(ids);
                return accounts.stream().collect(Collectors.toMap(Account::getId, it -> it));
            }
        }, new BeanModelConverter<>(Account.class));
    }

    @Override
    public List<Account> items() throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        List<Account> accountList = accountRepository.findAllByMerchantIdAndStatus(merchantId, Constants.STATUS_OK);
        return accountList;
    }

    private Account writeable(Integer id) throws Exception {
        Account account = getById(id);
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        if (account.getMerchantId() != merchantId) {
            throw new PermissionDeniedServiceException();
        }
        return account;
    }

    private Account findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        Account account = accountCache.findByKey(id);
        return account;
    }

    private Account getById(Integer id) throws Exception {
        Account account = findById(id);
        if (Objects.isNull(account)) {
            throw new DataNotFoundServiceException();
        }
        return account;
    }

    @Override
    public void save(Account account) throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        if (Objects.isNull(account.getId()) || account.getId() == 0) {
            if (account.getType() == AccountType.BANKCARD.value()) {
                if (Objects.isNull(account.getAccountName())) {
                    throw new ServiceException(ERR_ACCOUNT_NAME_NOT_NULL);
                }
                if (Objects.isNull(account.getAccountNo())) {
                    throw new ServiceException(ERR_ACCOUNT_NO_NOT_NULL);
                }
                if (Objects.isNull(account.getBankName())) {
                    throw new ServiceException(ERR_ACCOUNT_BANK_NAME_NOT_NULL);
                }
            }
            if (account.getType() == AccountType.WECHANT.value()) {
                if (Objects.isNull(account.getImg())) {
                    throw new ServiceException(ERR_ACCOUNT_QR_CODE_NOT_NULL);
                }

            }
            if (account.getType() == AccountType.ALIPAY.value()) {
                if (Objects.isNull(account.getImg())) {
                    throw new ServiceException(ERR_ACCOUNT_QR_CODE_NOT_NULL);
                }
            }
            account.setStatus(Constants.STATUS_OK);
            account.setMerchantId(merchantId);
            Account remove = accountRepository.findByTypeAndStatus(account.getType(), Constants.STATUS_OK);
            if (Objects.nonNull(remove)) {
                remove.setStatus(Constants.STATUS_HALT);
                accountRepository.save(remove);
                accountCache.removeSafely(remove.getId());
            }
            accountRepository.save(account);
        }
    }

    @Override
    public void remove(Integer id) throws Exception {
        Account account = writeable(id);
        account.setStatus(Constants.STATUS_HALT);
        accountRepository.save(account);
        accountCache.removeSafely(id);

    }
}
