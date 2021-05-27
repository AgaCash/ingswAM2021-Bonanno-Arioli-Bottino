package model.utilities;

import clientModel.player.LightPlayer;
import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ModelToLightParserTest {
    private ModelToLightParser parser = new ModelToLightParser();
    //TODO D-:<

    @Test
    void parseTable() {
    }

    @Test
    void parseMarket() {
    }

    @Test
    void parseDevBoard() {
    }

    @Test
    void parseDeck() {
    }

    @Test
    void parseDevelopmentCard() {
    }

    @Test
    void parsePlayer() {
        Player player = new Player("cisco");
        //player.setNickname("cisco");
        player.setStartingTurn(1);
        ArrayList<LeaderCard> couple = new ArrayList<>();
        couple.add(new Discount(1, false, null, null));
        couple.add(new ExtraProd(2, false, null, null));
        player.getPlayerBoard().addLeaderCards(couple);

        LightPlayer newPlayer = parser.parsePlayer(player);
        System.out.println(newPlayer.getNickname()+" "+
                newPlayer.getPlayerBoard().getLeaderSlot());

    }

    @Test
    void parsePlayerBoard() {
    }

    @Test
    void parseCardSlots() {
    }

    @Test
    void parseWarehouse() {
    }

    @Test
    void parseStrongbox() {
    }

    @Test
    void parseFaithTrack() {
    }

    @Test
    void parseLeaderSlot() {
    }

    @Test
    void parseLeaderCard() {
    }

    @Test
    void parseLorenzo() {
    }
}