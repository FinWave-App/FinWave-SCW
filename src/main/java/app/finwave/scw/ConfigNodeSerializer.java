package app.finwave.scw;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map;

public class ConfigNodeSerializer implements JsonSerializer<ConfigNode> {
    @Override
    public JsonElement serialize(ConfigNode src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement jsonElement = src.getSourceElement();
        JsonObject result;

        if (jsonElement != null) {
            if (jsonElement.isJsonObject())
                result = jsonElement.getAsJsonObject();
            else
                return jsonElement;
        }else {
            result = new JsonObject();
        }

        Map<String, String> values = src.valuesToMap();
        Map<String, ConfigNode> subNodes = src.subNodesToMap();

        for (Map.Entry<String, String> e : values.entrySet())
            result.addProperty(e.getKey(), e.getValue());

        for (Map.Entry<String, ConfigNode> e : subNodes.entrySet())
            result.add(e.getKey(), context.serialize(e.getValue()));

        return result;
    }
}
