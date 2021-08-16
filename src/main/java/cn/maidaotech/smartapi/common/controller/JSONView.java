package cn.maidaotech.smartapi.common.controller;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JSONView extends AbstractView {
    private Object result;

    public JSONView(Object result) {
        super();
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String resultAsJSONString = JSONSerializerManager.serialize(result);
        response.getWriter().write(resultAsJSONString);
    }
}
