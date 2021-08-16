package cn.maidaotech.smartapi.common.file.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminPermission;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import cn.maidaotech.smartapi.common.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mch/file")
public class MchFileController extends BaseController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload_token")
    @MerchantAdminAuthentication(MerchantAdminPermission.NONE)
    public ModelAndView uploadToken(String namespace, String fileName, int fileSize) throws Exception {
        return feedback(fileService.uploadToken(namespace, fileName, fileSize, true));
    }

}
