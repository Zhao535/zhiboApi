package cn.maidaotech.smartapi.api.admin.model;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;

public class AdminQo extends DataQueryObjectPage {


    @QueryField(type = QueryType.FULL_LIKE, name = "name")
    private String name;
    @QueryField(type = QueryType.LESS_THAN, name = "email")
    private String email;

    @Override
    public String getSortPropertyName() {
        return super.getSortPropertyName();
    }

    @Override
    public void setSortPropertyName(String sortPropertyName) {
        super.setSortPropertyName(sortPropertyName);
    }

    @Override
    public boolean isSortAscending() {
        return super.isSortAscending();
    }

    @Override
    public void setSortAscending(boolean sortAscending) {
        super.setSortAscending(sortAscending);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
