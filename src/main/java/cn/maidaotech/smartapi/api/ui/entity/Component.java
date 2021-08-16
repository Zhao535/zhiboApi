package cn.maidaotech.smartapi.api.ui.entity;

import java.util.List;

public class Component {
    private String key;
    private List list;
    private Byte listStyle;
    private String title;
    private String subHeading;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Byte getListStyle() {
        return listStyle;
    }

    public void setListStyle(Byte listStyle) {
        this.listStyle = listStyle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }
}
