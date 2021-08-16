package cn.maidaotech.smartapi.api.merchantAdmin.model;

import javax.persistence.*;
import java.util.Iterator;

@Entity
@Table
public class MerchantAdminSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer merchantAdminId;

    @Column
    private String token;

    @Column
    private Long signinAt;

    @Column
    private Long expiredAt;

    public MerchantAdminSession() {
    }


    public MerchantAdminSession(Integer merchantAdminId, String token, Long signinAt, Long expiredAt) {
        this.merchantAdminId = merchantAdminId;
        this.token = token;
        this.expiredAt = expiredAt;
        this.signinAt = signinAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMerchantAdminId() {
        return merchantAdminId;
    }

    public void setMerchantAdminId(Integer merchantAdminId) {
        this.merchantAdminId = merchantAdminId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSigninAt() {
        return signinAt;
    }

    public void setSigninAt(Long signinAt) {
        this.signinAt = signinAt;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
