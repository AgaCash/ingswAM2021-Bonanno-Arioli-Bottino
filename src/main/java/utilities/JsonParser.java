package utilities;

import model.cards.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.marbles.Marble;
import model.singleplayer.Token;
import model.table.FaithBox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonParser {
    private final Gson gson;
    private final String path;
    BufferedReader fileReader = null;

    public JsonParser(String path) {
        this.path = path;
        gson = new Gson();
    }
    /*
    public <cardsType> ArrayList<Card> getCards(){
        Type arrayListType = new TypeToken<ArrayList<cardsType>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }

    public <DevelopmentCard, Cards> ArrayList<DevelopmentCard> changeCast(ArrayList<Cards> toCast){
        ArrayList<DevelopmentCard> newList = new ArrayList<>();
        toCast.forEach((c)->{
            newList.add((DevelopmentCard) c);
        });
        return newList;
    }
    */
    public ArrayList<Marble> getMarbles(){
        Type arrayListType = new TypeToken<ArrayList<Marble>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards(){
        Type arrayListType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getDiscountCards(){
        Type arrayListType = new TypeToken<ArrayList<Discount>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getExtraDepotCards(){
        Type arrayListType = new TypeToken<ArrayList<ExtraDepot>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getExtraProdCards(){
        Type arrayListType = new TypeToken<ArrayList<ExtraProd>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }


    public ArrayList<LeaderCard> getWhiteConverterCard(){
        Type arrayListType = new TypeToken<ArrayList<WhiteConverter>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }

    public ArrayList<Token> getTokens(){
        Type arrayListType = new TypeToken<ArrayList<Token>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }

    public ArrayList<FaithBox> getFaithBoxes(){
        Type arrayListType = new TypeToken<ArrayList<FaithBox>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }

    /*
    public static void main(String[] args){
        JsonParser j = new JsonParser("src/main/model.resources/tokensList.json");

        j.getTokens().forEach(System.out::println);
    }*/

}