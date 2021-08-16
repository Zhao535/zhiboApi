package cn.maidaotech.smartapi.api.trade.entity;

public class TradePayload {
    private Integer id;//秒杀商品的id
    private Integer realPrice;//实际优惠后的价格
    private Byte type;

    public Integer getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Integer realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
