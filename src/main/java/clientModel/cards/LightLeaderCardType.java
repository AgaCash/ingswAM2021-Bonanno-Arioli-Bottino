package clientModel.cards;

public enum LightLeaderCardType {
        DISCOUNT(LightDiscount.class),
        EXTRADEPOT(LightExtraDepot.class),
        EXTRAPROD(LightExtraProd.class),
        WHITECONVERTER(LightWhiteConverter.class);
        private Class<? extends LightLeaderCard> classType;

        private LightLeaderCardType(Class<? extends LightLeaderCard> classType){
            this.classType = classType;
        }

        public Class<? extends LightLeaderCard> getClassType() {
            return classType;
        }

}
