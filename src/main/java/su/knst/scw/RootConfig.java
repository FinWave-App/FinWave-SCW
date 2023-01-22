package su.knst.scw;

import su.knst.scw.utils.SimpleFileWorker;
import su.knst.scw.utils.gson.G;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RootConfig extends ConfigNode {
    protected SimpleFileWorker fileWorker;
    protected boolean autoSave;

    public RootConfig(File file) throws IOException {
        this(file, false);
    }

    public RootConfig(File file, boolean autoSave) throws IOException {
        this.fileWorker = new SimpleFileWorker(file);

        this.autoSave = autoSave;
    }

    public RootConfig load() throws IOException {
        ConfigNode loaded = G.GSON.fromJson(fileWorker.readFromFile(), ConfigNode.class);

        if (loaded == null)
            return this;

        this.subNodes = new LinkedHashMap<>(loaded.subNodesToMap());
        this.rawParams = new LinkedHashMap<>(loaded.valuesToMap());
        this.sourceElement = loaded.getSourceElement();

        subNodes.values().forEach((n) -> n.init(this));

        return this;
    }

    @Override
    public boolean autoSaveEnabled() {
        return autoSave;
    }

    @Override
    public ConfigNode parent() {
        return this;
    }

    @Override
    public ConfigNode save() {
        try {
            fileWorker.writeToFile(G.GSON.toJson(this));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this;
    }
}
