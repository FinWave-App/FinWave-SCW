package su.knst.scw;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class ConfigNodeDeserializer implements JsonDeserializer<ConfigNode> {
    @Override
    public ConfigNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LinkedHashMap<String, String> rawParams = new LinkedHashMap<>();
        LinkedHashMap<String, ConfigNode> subNodes = new LinkedHashMap<>();

        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();

            for (Map.Entry<String, JsonElement> e : set) {
                String key = e.getKey();
                JsonElement value = e.getValue();

                if (value.isJsonPrimitive()) {
                    rawParams.put(key, value.getAsString());
                    continue;
                }

                try {
                    subNodes.put(key, context.deserialize(value, ConfigNode.class));
                }catch (Exception ignored) {}
            }
        }catch (Exception ignored) {}

        return new ConfigNode(rawParams, subNodes, json);
    }
}
