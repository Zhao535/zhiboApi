package cn.maidaotech.smartapi.api.article.entity;

public class ArticleWrapOption {
    private boolean withContent = false;
    private boolean withAuthor = false;
    private boolean withCollect = false;
    private boolean withSupport = false;
    private boolean withComment = false;
    private boolean withTag = false;
    private boolean withPrevNext = false;

    private ArticleWrapOption() {
    }

    public boolean isWithContent() {
        return this.withContent;
    }

    public boolean isWithAuthor() {
        return this.withAuthor;
    }

    public boolean isWithTag() {
        return this.withTag;
    }

    public boolean isWithCollect() {
        return this.withCollect;
    }

    public boolean isWithSupport() {
        return this.withSupport;
    }

    public boolean isWithComment() {
        return this.withComment;
    }

    /**
     * set方法需要私有化
     *
     * @param withContent
     * @return
     */
    private ArticleWrapOption setWithContent(boolean withContent) {
        this.withContent = withContent;
        return this;
    }

    private ArticleWrapOption setWithAuthor(boolean withAuthor) {
        this.withAuthor = withAuthor;
        return this;
    }

    public ArticleWrapOption setWithCollect(boolean withCollect) {
        this.withCollect = withCollect;
        return this;
    }

    public ArticleWrapOption setWithSupport(boolean withSupport) {
        this.withSupport = withSupport;
        return this;
    }

    public ArticleWrapOption setWithComment(boolean withComment) {
        this.withComment = withComment;
        return this;
    }

    public ArticleWrapOption setWithTag(boolean withTag) {
        this.withTag = withTag;
        return this;
    }

    public boolean isWithPrevNext() {
        return withPrevNext;
    }

    public ArticleWrapOption setWithPrevNext(boolean withPrevNext) {
        this.withPrevNext = withPrevNext;
        return this;
    }

    private static final ArticleWrapOption DEFAULT = new ArticleWrapOption();
    private static final ArticleWrapOption ADM_LIST = getDefaultInstance();
    private static final ArticleWrapOption ADM_DETAIL = getDefaultInstance().setWithContent(true);
    private static final ArticleWrapOption USR_LIST = getDefaultInstance().setWithTag(true);
    private static final ArticleWrapOption USER_DETAIL = getDefaultInstance().setWithContent(true).setWithCollect(true).setWithSupport(true).setWithComment(true).setWithAuthor(true).setWithPrevNext(true).setWithTag(true);

    public static ArticleWrapOption getDefaultInstance() {
        return DEFAULT;
    }

    public static ArticleWrapOption getAdmListInstance() {
        return ADM_LIST;
    }

    public static ArticleWrapOption getAdmDetailInstance() {
        return ADM_DETAIL;
    }

    public static ArticleWrapOption getUsrListInstance() {
        return USR_LIST;
    }

    public static ArticleWrapOption getUserDetailInstance() {
        return USER_DETAIL;
    }


}
