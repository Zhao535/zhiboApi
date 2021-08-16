package cn.maidaotech.smartapi.api.merchant.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.merchant.entity.MerchantWrapOption;
import cn.maidaotech.smartapi.api.trade.entity.TradeQo;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchant.entity.MerchantQo;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "adm/merchant")
public class AdmMerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "/save")
    @AdminAuthentication({AdminPermission.MERCHANT_EDIT, AdminPermission.MERCHANT_LIST})
    public ModelAndView save(String merchant, String merchantAdmin) throws Exception {
        if (merchantAdmin.equals("{}")) {
            merchantService.save(parseModel(merchant, new Merchant(), "merchant"), new MerchantAdmin());
        }
        merchantService.save(parseModel(merchant, new Merchant(), "merchant"), parseModel(merchantAdmin, new MerchantAdmin(), "merchantAdmin"));
        return feedback();
    }


    @RequestMapping(value = "items")
    @AdminAuthentication({AdminPermission.MERCHANT_LIST})
    public ModelAndView items(String qo) throws Exception {
        return feedback(merchantService.items(parseModel(qo, new MerchantQo(), "qo"), MerchantWrapOption.getDefault()));
    }

    @RequestMapping(value = "item")
    @AdminAuthentication({AdminPermission.MERCHANT_LIST})
    public ModelAndView item(Integer id) throws Exception {
        return feedback(merchantService.item(id, MerchantWrapOption.getDefault()));
    }

    @RequestMapping(value = "changeStatus")
    @AdminAuthentication({AdminPermission.MERCHANT_EDIT})
    public ModelAndView changeStatus(Integer id) throws Exception {
        merchantService.updateStatus(id);
        return feedback();
    }

    @RequestMapping(value = "trades")
    @AdminAuthentication
    public ModelAndView trades(String qo) throws Exception {
        return feedback(merchantService.trades(parseModel(qo, new TradeQo(), "qo")));
    }

    @AdminAuthentication(value = AdminPermission.MERCHANT_EDIT)
    @RequestMapping("renewal")//商铺续费
    public ModelAndView renewal(Integer id, String duration) throws Exception {
        merchantService.renewal(id, duration);
        return feedback();//TODO
    }
}
