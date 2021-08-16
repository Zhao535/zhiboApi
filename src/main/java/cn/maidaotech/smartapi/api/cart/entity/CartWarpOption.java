package cn.maidaotech.smartapi.api.cart.entity;

public class CartWarpOption {
    private boolean withSecKill = false;

    public CartWarpOption() {
    }

    public boolean isWithSecKill() {
        return withSecKill;
    }

    private CartWarpOption setWithSecKill(boolean withSecKill) {
        this.withSecKill = withSecKill;
        return this;
    }

    public static final CartWarpOption DEFAULT = new CartWarpOption().setWithSecKill(true);

    public static CartWarpOption getInstance() {
        return DEFAULT;
    }
}
