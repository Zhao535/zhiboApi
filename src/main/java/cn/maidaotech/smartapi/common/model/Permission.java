package cn.maidaotech.smartapi.common.model;

public class Permission {

    private String key;
    private String name;
    private String level;

    public Permission() {
        super();
    }

    public Permission(String key, String name, String level) {
        super();
        this.key = key;
        this.name = name;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
