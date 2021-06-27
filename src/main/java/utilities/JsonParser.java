package utilities;

import clientModel.cards.LightLeaderCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.cards.*;
import model.marbles.Marble;
import model.singleplayer.Token;
import model.table.FaithBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonParser {
    private final Gson gson;
    private String path;
    BufferedReader fileReader = null;

    public JsonParser(String path) {
        this.path = path;
        gson = new Gson();
    }

    public ArrayList<Marble> getMarbles(){
        Type arrayListType = new TypeToken<ArrayList<Marble>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards(){
        Type arrayListType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getDiscountCards(){
        Type arrayListType = new TypeToken<ArrayList<Discount>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getExtraDepotCards(){
        Type arrayListType = new TypeToken<ArrayList<ExtraDepot>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getExtraProdCards(){
        Type arrayListType = new TypeToken<ArrayList<ExtraProd>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getWhiteConverterCard(){
        Type arrayListType = new TypeToken<ArrayList<WhiteConverter>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }

    public ArrayList<Token> getTokens(){
        Type arrayListType = new TypeToken<ArrayList<Token>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }

    public ArrayList<FaithBox> getFaithBoxes(){
        Type arrayListType = new TypeToken<ArrayList<FaithBox>>(){}.getType();
        fileReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path)));
        return gson.fromJson(fileReader, arrayListType);
    }

    public LightLeaderCard getLightLeaderCardFromId(int id){
        ArrayList<LeaderCard> cards = null;
        int tmpId = id;
        if(id >= 1 && id <= 4){
            path += "discount.json";
            cards = this.getDiscountCards();
        }else if(id >=5 && id <= 8){
            path += "extraDepot.json";
            cards = this.getExtraDepotCards();
        }else if(id >= 9 && id <= 12){
            path += "whiteConverter.json";
            cards = this.getWhiteConverterCard();
        }else if(id >= 13 && id <= 16){
            path += "extraProd.json";
            cards = this.getExtraProdCards();
        }
        assert cards != null;
        while(tmpId>4){
            tmpId-=4;
        }
        return cards.get(tmpId-1).convert();
    }

}
