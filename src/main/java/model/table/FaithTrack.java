package model.table;

import clientModel.table.LightFaithBox;
import clientModel.table.LightFaithTrack;
import utilities.JsonParser;

import java.util.ArrayList;

public class FaithTrack {
    private ArrayList<FaithBox> box = new ArrayList<>(25);

    /**
     * loads the faith boxes from a json file
     */
    public FaithTrack() {
        box = new JsonParser("src/main/resources/faithBox.json").getFaithBoxes();
    }

    /**
     * method that advances player's position in the faith track
     *
     * @param playerBox   current player position in the track
     * @return new player position in the track
     */
    /*public FaithBox faithAdvance (FaithBox playerBox, FaithTrack playerTrack, int advance){
        FaithBox nextBox;
        int pos;
        pos = playerBox.getPosition();
        nextBox = playerTrack.box.get(pos+advance);
        return nextBox;
    }
*/
    public FaithBox faithAdvance(FaithBox playerBox) {
        FaithBox nextBox;
        int pos;
        pos = playerBox.getPosition();
        nextBox = this.box.get(pos + 1);
        return nextBox;
    }

    public LightFaithTrack convert(){
        ArrayList<LightFaithBox> newTrack = new ArrayList<>();
        box.forEach(element -> newTrack.add(element.convert()));

        LightFaithTrack newFTrack = new LightFaithTrack();
        newFTrack.setTrack(newTrack);
        return newFTrack;
    }


}

    /*
    public void firstPopeCheck(String nickname){
        //lo deve fare il game. Metodo che riceve il nickname del chiamante e gli aggiunge i punti,
        //dopodichè per ogni player.nickname != chiamante.nickname verifica se la FaithBox ha una position>x.
    }
    public void secondPopeCheck(String nickname){
        //lo deve fare il game...
    }
    public void thirdPopeCheck(String nickname){
        //lo deve fare il game...
    }
}
*/
