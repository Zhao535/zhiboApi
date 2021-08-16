package cn.maidaotech.smartapi.common.vcode.service;

import cn.maidaotech.smartapi.common.vcode.model.Code;
import cn.maidaotech.smartapi.common.vcode.model.CodePayload;

public interface CodeService {
    Code send(String username, CodePayload payload) throws Exception;

    Code getAndMatchCode(String subject, String username, String code) throws Exception;




}
