package cn.maidaotech.smartapi.api.coupon.task;

import cn.maidaotech.smartapi.api.coupon.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatusSchedulerTask {

    @Autowired
    private UserCouponService userCouponService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    private void doSyncTag() {
        userCouponService.syncStatus();
    }

}
