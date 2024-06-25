package app.finwave.scw.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import app.finwave.scw.ConfigNode;
import app.finwave.scw.RootConfig;
import app.finwave.scw.ConfigNodeDeserializer;
import app.finwave.scw.ConfigNodeSerializer;

public class G {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ConfigNode.class, new ConfigNodeSerializer())
            .registerTypeAdapter(ConfigNode.class, new ConfigNodeDeserializer())
            .registerTypeAdapter(RootConfig.class, new ConfigNodeSerializer())
            .registerTypeAdapter(RootConfig.class, new ConfigNodeDeserializer())
            .create();
}
