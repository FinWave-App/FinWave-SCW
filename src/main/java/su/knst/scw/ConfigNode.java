package su.knst.scw;

import com.google.gson.JsonElement;
import su.knst.scw.utils.ParamsContainer;
import su.knst.scw.utils.gson.G;

import java.util.*;

public class ConfigNode extends ParamsContainer {
    protected Map<String, ConfigNode> subNodes;
    protected JsonElement sourceElement;

    public ConfigNode() {
        this(new LinkedHashMap<>(), new LinkedHashMap<>(), null);
    }

    protected ConfigNode(Map<String, String> rawParams, Map<String, ConfigNode> subNodes, JsonElement sourceElement) {
        super(rawParams);

        this.subNodes = subNodes;
        this.sourceElement = sourceElement;
    }

    public ConfigNode subNode(String name) {
        if (!subNodes.containsKey(name))
            subNodes.put(name, new ConfigNode());

        return subNodes.get(name);
    }

    public void setValue(String name, Object value) {
        this.rawParams.put(name, String.valueOf(value));
    }

    public void setAs(Object object) {
        sourceElement = G.GSON.toJsonTree(object);
    }

    public <T> Optional<T> getAs(Class<T> tClass) {
        return Optional.ofNullable(sourceElement == null ? null : G.GSON.fromJson(sourceElement, tClass));
    }

    protected Map<String, String> valuesToMap() {
        return Collections.unmodifiableMap(rawParams);
    }

    protected Map<String, ConfigNode> subNodesToMap() {
        return Collections.unmodifiableMap(subNodes);
    }

    protected JsonElement getSourceElement() {
        return sourceElement;
    }
}
