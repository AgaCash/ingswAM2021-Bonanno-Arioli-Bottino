package clientModel.cards;

/**
 * Enum that link Light Leaders' type with their correct classes so when clients or server receive the cards they know
 * in which type need to be parsed to be created
 */
public enum LightLeaderCardType {
        DISCOUNT(LightDiscount.class),
        EXTRADEPOT(LightExtraDepot.class),
        EXTRAPROD(LightExtraProd.class),
        WHITECONVERTER(LightWhiteConverter.class);
        /**
         * The type of the class linked to the correct light leader card type
         */
        private Class<? extends LightLeaderCard> classType;

        /**
         * Private Constructor used by enum values
         * @param classType the class of the correct
         */
        private LightLeaderCardType(Class<? extends LightLeaderCard> classType){
            this.classType = classType;
        }

        /**Method that get the type of the class of the enum
         * @return the class type of the enum class
         */
        public Class<? extends LightLeaderCard> getClassType() {
            return classType;
        }

}
