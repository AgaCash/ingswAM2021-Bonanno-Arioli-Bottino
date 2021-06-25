package model.cards;

/**
 * The abstract class inherited by all different Cards classes
 */
public abstract class Card {
    protected int id;

    /**Return the unique ID of the Card
     * @return a number
     */
    public int getId(){return id;}
}
