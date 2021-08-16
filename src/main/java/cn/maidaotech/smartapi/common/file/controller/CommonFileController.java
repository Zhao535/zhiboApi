package cn.maidaotech.smartapi.common.file.controller;

import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.utils.ImgUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/common/file")
public class CommonFileController extends BaseController {

    @RequestMapping(value = "/img_to_base64")
    public ModelAndView imgToBase64(String url) throws Exception {
        return feedback(ImgUtils.base64FromURL(url));
    }

}
