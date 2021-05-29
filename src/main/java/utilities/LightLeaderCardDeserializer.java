package utilities;

import clientModel.cards.LightLeaderCard;
import clientModel.cards.LightLeaderCardType;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LightLeaderCardDeserializer implements JsonDeserializer<LightLeaderCard> {
    @Override
    public LightLeaderCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        LightLeaderCardType leaderCardType  = LightLeaderCardType.valueOf(jsonObject.get("type").getAsString());
        return jsonDeserializationContext.deserialize(jsonElement, leaderCardType.getClassType());
    }
}
