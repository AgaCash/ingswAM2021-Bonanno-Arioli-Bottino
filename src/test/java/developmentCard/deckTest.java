package developmentCard;

import cards.DevelopmentCard;
import colour.Colour;
import org.junit.jupiter.api.Test;
import resources.Resource;
import table.Deck;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class deckTest {
    @Test
    void createDeckTest(){
        ArrayList<DevelopmentCard> cards = new ArrayList<>();

        int id = 2;
        Colour colour = Colour.GREEN;
        int level = 1;
        int points = 2;
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(Resource.STONE);
        ArrayList<Resource> prodInput = new ArrayList<>();
        prodInput.add(Resource.COIN);
        ArrayList<Resource> prodOutput = new ArrayList<>();
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c1 = new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c1);

        id = -1;
        colour = null;
        level = -1;
        points = 256;
        cost.clear();
        cost.add(Resource.STONE);
        cost.add(Resource.COIN);
        cost.add(Resource.SERVANT);

        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.SERVANT);
        prodInput.add(Resource.SHIELD);

        prodOutput.clear();
        prodOutput.add(Resource.STONE);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c2 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c2);

        id = 31;
        colour = null;
        level = 2;
        points = 26;
        cost.clear();
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);

        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);

        prodOutput.clear();
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c3 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c3);

        id = 3;
        colour = Colour.PURPLE;
        level = 3;
        points = 29;
        cost.clear();
        cost.add(Resource.SHIELD);
        cost.add(Resource.STONE);

        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);

        prodOutput.clear();
        prodOutput.add(Resource.SERVANT);
        prodOutput.add(Resource.STONE);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c4 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c4);

        Deck d = new Deck(cards);
        Deck e = new Deck(cards);
        assertNotNull(d);
        assertNotNull(e);


    }

    @Test
    void popCardTest(){
        ArrayList<DevelopmentCard> cards = new ArrayList<>();

        int id = 2;
        Colour colour = Colour.GREEN;
        int level = 1;
        int points = 2;
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(Resource.STONE);
        ArrayList<Resource> prodInput = new ArrayList<>();
        prodInput.add(Resource.COIN);
        ArrayList<Resource> prodOutput = new ArrayList<>();
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c1 = new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c1);

        id = -1;
        colour = null;
        level = -1;
        points = 256;
        cost.clear();
        cost.add(Resource.STONE);
        cost.add(Resource.COIN);
        cost.add(Resource.SERVANT);

        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.SERVANT);
        prodInput.add(Resource.SHIELD);

        prodOutput.clear();
        prodOutput.add(Resource.STONE);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c2 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c2);

        id = 31;
        colour = null;
        level = 2;
        points = 26;
        cost.clear();
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);

        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);

        prodOutput.clear();
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c3 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c3);

        id = 3;
        colour = Colour.PURPLE;
        level = 3;
        points = 29;
        cost.clear();
        cost.add(Resource.SHIELD);
        cost.add(Resource.STONE);

        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);

        prodOutput.clear();
        prodOutput.add(Resource.SERVANT);
        prodOutput.add(Resource.STONE);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c4 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        cards.add(c4);

        Deck d = new Deck(cards);
        Deck e = new Deck(cards);

        assertNotNull(d.popCard());
        assertNotNull(d.popCard());
        assertNotNull(d.popCard());
        assertNotNull(d.popCard());
        assertNull(d.popCard());
    }
}
