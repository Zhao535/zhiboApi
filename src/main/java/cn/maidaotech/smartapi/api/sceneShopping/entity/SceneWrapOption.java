package cn.maidaotech.smartapi.api.sceneShopping.entity;

public class SceneWrapOption {

    private boolean withArticle = true;
    private boolean withAuthor = true;
    private boolean withProducts = true;

    private SceneWrapOption() {
    }

    public boolean isWithArticle() {
        return this.withArticle;
    }

    public boolean isWithAuthor() {
        return this.withAuthor;
    }

    public boolean isWithProducts() {
        return this.withProducts;
    }

    /**
     * set方法需要私有化
     *
     * @param withArticle
     * @return
     */
    private SceneWrapOption serWithArticle(boolean withArticle) {
        this.withArticle = withArticle;
        return this;
    }

    private SceneWrapOption setWithAuthor(boolean withAuthor) {
        this.withAuthor = withAuthor;
        return this;
    }

    public SceneWrapOption setWithProducts(boolean withProducts) {
        this.withProducts = withProducts;
        return this;
    }



    private static final SceneWrapOption DEFAULT = new SceneWrapOption();

    public static SceneWrapOption getDefaultInstance() {
        return DEFAULT;
    }




}
