package network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import network.messages.*;
import network.messages.lobbyMessages.GetLobbyRequest;
import network.messages.lobbyMessages.LoginMultiPlayerRequest;
import network.messages.lobbyMessages.StartSinglePlayerRequest;

import java.io.BufferedReader;

public class JsonParserNetwork {
    private final Gson gson;
    private String path;
    BufferedReader fileReader = null;

    public JsonParserNetwork(String path) {
        this.path = path;
        gson = new Gson();
    }

    public JsonParserNetwork() {
        this.path = "/src/main/resources/networkConfig.json";
        gson = new Gson();
    }

    public void setPath(String path){
        this.path = path;
    }


    public LoginMultiPlayerRequest getLoginMultiMessage(String message){
        System.out.println(message);
        LoginMultiPlayerRequest m = null;
        try{
             m = gson.fromJson(message, LoginMultiPlayerRequest.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }

    public StartSinglePlayerRequest getLoginSingleMessage(String message){
        System.out.println(message);
        StartSinglePlayerRequest m = null;
        try{
            m = gson.fromJson(message, StartSinglePlayerRequest.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }

    public GetLobbyRequest getLobbyRequest(String message){
        System.out.println(message);
        GetLobbyRequest m = null;
        try{
            m = gson.fromJson(message, GetLobbyRequest.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }

    public Message getMessage(String message){
        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);//JsonParser.parseString(message).getAsJsonObject();
        MessageType messageType = MessageType.valueOf(jsonMessage.get("messageType").getAsString().toString());
        switch (messageType){
            case JOIN_SINGLEPLAYER:
        }
        return gson.fromJson(message, Message.class);
    }

    public String toClearString(Message message){
        return gson.toJson(message);
    }

    public String createLobbyResponse(boolean status){
        JsonObject response = new JsonObject();
        response.addProperty("status", status);
        return response.toString();
    }

    public static void main(String[] args){
        JsonParserNetwork j = new JsonParserNetwork();
        Message m = j.getMessage("{\"messageType\": \"JOIN_SINGLEPLAYER\"}");
        System.out.println(m);
    }


    /*
    public int getPort(){
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, int.class);
    }

    public ArrayList<Marble> getMarbles(){
        Type arrayListType = new TypeToken<ArrayList<Marble>>(){}.getType();
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(fileReader, arrayListType);
    }


    public static void main(String[] args){
        JsonParser j = new JsonParser("src/main/resources/tokensList.json");

        j.getTokens().forEach(System.out::println);
    }*/

}

