package model.cards;

public enum LeaderCardType {
    DISCOUNT(Discount.class),
    EXTRADEPOT(ExtraDepot.class),
    EXTRAPROD(ExtraProd.class),
    WHITECONVERTER(WhiteConverter.class);
    private Class<? extends LeaderCard> classType;

    private LeaderCardType(Class<? extends LeaderCard> classType){
        this.classType = classType;
    }

    public Class<? extends LeaderCard> getClassType() {
        return classType;
    }
}
