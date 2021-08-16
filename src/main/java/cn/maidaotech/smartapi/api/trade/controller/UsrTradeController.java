package cn.maidaotech.smartapi.api.trade.controller;

import cn.maidaotech.smartapi.api.trade.entity.TradeQo;
import cn.maidaotech.smartapi.api.trade.entity.TradeWrapOption;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import cn.maidaotech.smartapi.api.trade.service.TradeService;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("usr/trade")
public class UsrTradeController extends BaseController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/save")
    @UserAuthentication
    public ModelAndView save(String trade, Integer userCouponId) throws Exception {
        return feedback(tradeService.save(parseModel(trade, new Trade()), userCouponId));
    }

    @RequestMapping("/trades")
    @UserAuthentication
    public ModelAndView trades(String tradeQo) throws Exception {
        TradeQo qo = parseModel(tradeQo, new TradeQo());
        qo.setUserId(UserContexts.requestUserId());
        return feedback(tradeService.trades(qo, TradeWrapOption.getUserListInstance()));
    }

    @RequestMapping("/trade")
    @UserAuthentication
    public ModelAndView trade(Integer id) throws Exception {
        return feedback(tradeService.item(id));
    }

    @RequestMapping("/pay")
    @UserAuthentication
    public ModelAndView pay(Integer id, Byte type) throws Exception {
        tradeService.updateType(id, type);
        return feedback();
    }

    @RequestMapping("/update_type")
    @UserAuthentication
    public ModelAndView updateType(Integer id, Byte type) throws Exception {
        tradeService.updateType(id, type);
        return feedback();
    }


}
