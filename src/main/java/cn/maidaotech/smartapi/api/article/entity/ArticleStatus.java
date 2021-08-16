package cn.maidaotech.smartapi.api.article.entity;

public enum ArticleStatus {
    ON(1,"已发布"), OFF(2,"未发布"), TIMING(3,"发布中");

    private byte value;
    private String name;

    public byte value() {
        return this.value;
    }

    ArticleStatus(int value ,String name) {
        this.value = (byte) value;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public static ArticleStatus find(Byte v) {
        if (v == null) {
            return null;
        }
        for (ArticleStatus status : ArticleStatus.values()) {
            if (status.value == v) {
                return status;
            }
        }
        return null;
    }


}


