package app.finwave.scw;

import app.finwave.scw.utils.BinaryObject;
import app.finwave.scw.utils.gson.G;
import com.google.gson.JsonElement;
import app.finwave.scw.utils.ParamsContainer;

import java.nio.ByteBuffer;
import java.util.*;

public class ConfigNode extends ParamsContainer {
    protected Map<String, ConfigNode> subNodes;
    protected JsonElement sourceElement;
    protected ConfigNode parent;

    public ConfigNode() {
        this(new LinkedHashMap<>(), new LinkedHashMap<>(), null);
    }

    protected ConfigNode(Map<String, String> rawParams, Map<String, ConfigNode> subNodes, JsonElement sourceElement) {
        super(rawParams);

        this.subNodes = subNodes;
        this.sourceElement = sourceElement;
    }

    public boolean autoSaveEnabled() {
        return parent.autoSaveEnabled();
    }

    public ConfigNode save() {
        parent.save();

        return this;
    }

    protected ConfigNode init(ConfigNode parent) {
        this.parent = parent;

        return this;
    }

    public ConfigNode subNode(String name) {
        if (!subNodes.containsKey(name)) {
            ConfigNode newNode = new ConfigNode();
            newNode.init(this);

            subNodes.put(name, newNode);
        }

        return subNodes.get(name);
    }

    public ConfigNode setValue(String name, Object value) {
        this.rawParams.put(name, String.valueOf(value));

        if (autoSaveEnabled())
            save();

        return this;
    }

    public ConfigNode setAs(Object object) {
        sourceElement = G.GSON.toJsonTree(object);
        clear();

        if (autoSaveEnabled())
            save();

        return this;
    }

    public ConfigNode clear() {
        rawParams.clear();
        subNodes.values().forEach(ConfigNode::clear);

        return this;
    }

    public ConfigNode parent() {
        return parent;
    }

    public ConfigNode setBinaryObject(String name, BinaryObject object) {
        ByteBuffer byteBuffer = object.save();
        String base64 = Base64.getEncoder().encodeToString(byteBuffer.array());
        setValue(name, base64);

        return this;
    }

    public <T extends BinaryObject> Optional<T> getBinaryObject(String name, Class<T> tClass) {
        return getString(name).map(s -> {
            ByteBuffer byteBuffer;

            try {
                byteBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(s));
            }catch (Exception ignored){
                return null;
            }

            T object;
            try {
                object = tClass.newInstance();
                object.load(byteBuffer);
            }catch (Exception e){
                object = null;
                e.printStackTrace();
            }

            return object;
        });
    }

    public <T> Optional<T> getAs(Class<T> tClass) {
        Optional<T> result = Optional.empty();

        if (sourceElement == null)
            return result;

        try {
            result = Optional.ofNullable(G.GSON.fromJson(sourceElement, tClass));
        }catch (Exception ignored) {
        }

        return result;
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
