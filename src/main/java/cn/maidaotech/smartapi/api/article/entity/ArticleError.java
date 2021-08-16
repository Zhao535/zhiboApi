package cn.maidaotech.smartapi.api.article.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface ArticleError extends ErrorCode {
    public static final int ERR_ARTICLE_TITLE_LENGTH = 1700;
    public static final int ERR_ARTICLE_PICTURE_NOT_NULL = 1701;
    public static final int ERR_ARTICLE_CONTENT_NOT_NULL =1702;
    public static final int ERR_ARTICLE_STATUS_WRONG=1703;
    public static final int ERR_ARTICLE_PUTAT_INVALID=1704;
    public static final int ERR_TAG_NOT_FOUND=1705;
    public static final int ERR_ARTICLE_TAG_INVALID=1706;

}
