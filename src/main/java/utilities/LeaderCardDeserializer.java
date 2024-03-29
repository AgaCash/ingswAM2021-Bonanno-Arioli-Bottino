package utilities;

import com.google.gson.*;
import model.cards.LeaderCard;
import model.cards.LeaderCardType;

import java.lang.reflect.Type;

public class LeaderCardDeserializer implements JsonDeserializer<LeaderCard> {
    @Override
    public LeaderCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        LeaderCardType leaderCardType  = LeaderCardType.valueOf(jsonObject.get("type").getAsString());
        return jsonDeserializationContext.deserialize(jsonElement, leaderCardType.getClassType());
    }
}
