package su.knst.scw.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import su.knst.scw.ConfigNode;
import su.knst.scw.RootConfig;
import su.knst.scw.ConfigNodeDeserializer;
import su.knst.scw.ConfigNodeSerializer;

public class G {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ConfigNode.class, new ConfigNodeSerializer())
            .registerTypeAdapter(ConfigNode.class, new ConfigNodeDeserializer())
            .registerTypeAdapter(RootConfig.class, new ConfigNodeSerializer())
            .registerTypeAdapter(RootConfig.class, new ConfigNodeDeserializer())
            .create();
}
