package cn.maidaotech.smartapi.api.trade.controller;

import cn.maidaotech.smartapi.api.trade.entity.TradeQo;
import cn.maidaotech.smartapi.api.trade.entity.TradeWrapOption;
import cn.maidaotech.smartapi.api.trade.service.TradeService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mch/trade")
public class MchTradeController extends BaseController {
    @Autowired
    private TradeService tradeService;


    @RequestMapping("items")
    @MerchantAdminAuthentication
    public ModelAndView items(String tradeQo) throws Exception {
        return feedback(tradeService.items(parseModel(tradeQo, new TradeQo(), "qo"), TradeWrapOption.getMerchantListInstance()));
    }

    @RequestMapping("item")
    @MerchantAdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(tradeService.item(id));
    }

    @RequestMapping("changeStatus")
    @MerchantAdminAuthentication
    public ModelAndView changeStatus(Integer id, Byte type) throws Exception {
        tradeService.updateType(id, type);
        return feedback();
    }

    @RequestMapping("send")
    @MerchantAdminAuthentication
    public ModelAndView send(Integer id, Byte type) throws Exception {
        tradeService.send(id, type);
        return feedback();
    }
}
