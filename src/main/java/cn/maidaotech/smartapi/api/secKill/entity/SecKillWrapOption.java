package cn.maidaotech.smartapi.api.secKill.entity;

import cn.maidaotech.smartapi.api.ui.model.UI;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class SecKillWrapOption {
    private boolean forAdm = false;
    private boolean forMch = false;
    private boolean forUsr = false;
    private boolean withProduct = false;

    public boolean isForAdm() {
        return forAdm;
    }

    private SecKillWrapOption setForAdm(boolean forAdm) {
        this.forAdm = forAdm;
        return this;
    }

    public boolean isForMch() {
        return forMch;
    }

    private SecKillWrapOption setForMch(boolean forMch) {
        this.forMch = forMch;
        return this;
    }

    public boolean isForUsr() {
        return forUsr;
    }

    private SecKillWrapOption setForUsr(boolean forUsr) {
        this.forUsr = forUsr;
        return this;
    }

    public boolean isWithProduct() {
        return withProduct;
    }

    private SecKillWrapOption setWithProduct(boolean withProduct) {
        this.withProduct = withProduct;
        return this;
    }

    public static final SecKillWrapOption ADM_LIST = getDefaultInstance().setForAdm(true).setWithProduct(true);
    public static final SecKillWrapOption USR_LIST = getDefaultInstance().setForUsr(true).setWithProduct(true);
    public static final SecKillWrapOption MCH_LIST = getDefaultInstance().setForMch(true).setWithProduct(true);

    public static SecKillWrapOption getDefaultInstance() {
        return new SecKillWrapOption();
    }

    public static SecKillWrapOption getAdmList() {
        return ADM_LIST;
    }

    public static SecKillWrapOption getUsrList() {
        return USR_LIST;
    }

    public static SecKillWrapOption getMchList() {
        return MCH_LIST;
    }

}
