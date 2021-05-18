package clientModel.table;

import clientModel.marbles.LightMarble;

import java.util.ArrayList;

public class LightMarketBoard {

    private ArrayList<ArrayList<LightMarble>> marbleGrid = new ArrayList<>(3);
    private LightMarble freeMarble;

    public void setMarketBoard (ArrayList<LightMarble> marbles){
        for (int n = 0; n < 3; n++)
            marbleGrid.add(new ArrayList(4));

        int k =0;
        for(int i=0; i<3; i++)
            for(int j =0; j<4; j++){
                marbleGrid.get(i).add(j, marbles.get(k));
                k++;
            }
        freeMarble = marbles.get(k);
    }
/*
    public void addMarketLine (int line, LeaderCard card){
        int i;

        freeMarble = marbleGrid.get(line).get(3);
        for(i =3 ; i>0; i--)
            marbleGrid.get(line).set(i, marbleGrid.get(line).get(i-1));
        marbleGrid.get(line).set(i, freeMarble);
    }

    public void addMarketColumn (int col, LeaderCard card){
        freeMarble = marbleGrid.get(2).get(col);
        marbleGrid.get(1).set(col, marbleGrid.get(0).get(col));
        marbleGrid.get(0).set(col, freeMarble);
    }

 */

    public LightMarble getMarble (int whichOneLine, int whichOneCol){
        LightMarble mar;
        mar = marbleGrid.get(whichOneLine).get(whichOneCol);
        return mar;
    }

}
