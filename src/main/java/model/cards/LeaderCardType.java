package model.cards;

/**
 * Enum that allow to link right classes to right leaderCard Type
 */
public enum LeaderCardType {
    DISCOUNT(Discount.class),
    EXTRADEPOT(ExtraDepot.class),
    EXTRAPROD(ExtraProd.class),
    WHITECONVERTER(WhiteConverter.class);
    /**
     * Class type of the card
     */
    private Class<? extends LeaderCard> classType;

    /**
     * Private constructor used by this enum to link the right class type
     * @param classType the right class of the card
     */
    private LeaderCardType(Class<? extends LeaderCard> classType){
        this.classType = classType;
    }

    /**
     * Getter of the classType
     * @return the class of the card
     */
    public Class<? extends LeaderCard> getClassType() {
        return classType;
    }
}
