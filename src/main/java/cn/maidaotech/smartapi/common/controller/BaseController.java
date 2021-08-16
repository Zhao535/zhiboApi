package cn.maidaotech.smartapi.common.controller;

import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import com.sunnysuperman.commons.bean.Bean;
import com.sunnysuperman.commons.bean.ParseBeanOptions;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private final static int PAGE_SIZE = 20;

    protected static ModelAndView feedback() {
        return feedback(null);
    }

    protected static ModelAndView feedback(Object ret) {
        Object result = ret != null ? ret : "success";
        Map<String, Object> data = new HashMap<>();
        data.put("errcode", 0);
        data.put("result", result);
        return new ModelAndView(new JSONView(data));
    }

    public static <T> T parseModel(String modelJSON, T model) throws ServiceException {
        return parseModel(modelJSON, model, null, null);
    }

    protected static <T> T parseModel(String modelJSON, T model, String key) throws ServiceException {
        return parseModel(modelJSON, model, key, null);
    }

    protected static <T> T parseModel(String modelJSON, T model, String key, ParseBeanOptions options)
            throws ServiceException {
        if (modelJSON == null || modelJSON.isEmpty()) {
            throw new ArgumentServiceException(key != null ? key : "model");
        }
        try {
            return Bean.fromJson(modelJSON, model, options);
        } catch (Exception e) {
            throw new ArgumentServiceException(key != null ? key : "model");
        }
    }

    protected static int parsePageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 1) {
            return 0;
        }
        return pageNum - 1;
    }

    protected static int parsePageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            return PAGE_SIZE;
        }
        return pageSize;
    }

    protected static int parseOffset(Integer offset) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        return offset;
    }


    protected static PageRequest parsePageRequest(Integer pagenum, Integer pagesize) {
        PageRequest pageRequest = PageRequest.of(pagenum, pagesize);
        return pageRequest;
    }


}
