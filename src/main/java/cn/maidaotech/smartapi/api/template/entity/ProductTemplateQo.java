package cn.maidaotech.smartapi.api.template.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import jodd.util.CollectionUtil;

import java.util.List;

public class ProductTemplateQo extends DataQueryObjectPage {


    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;

    @QueryField(type = QueryType.FULL_LIKE, name = "title")
    private String title;

    @QueryField(type = QueryType.IN, name = "sequence")
    private List<String> sequence;


    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status == 0 ? null : status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSequence() {
        return sequence;
    }

    public void setSequence(List<String> sequence) {
        this.sequence = CollectionUtils.isEmpty(sequence) ? null : sequence;
    }
}
