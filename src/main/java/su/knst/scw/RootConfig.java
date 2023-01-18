package su.knst.scw;

import su.knst.scw.utils.SimpleFileWorker;
import su.knst.scw.utils.gson.G;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RootConfig extends ConfigNode {

    protected SimpleFileWorker fileWorker;

    public RootConfig(File file) throws IOException {
        this.fileWorker = new SimpleFileWorker(file);
    }

    public void load() throws IOException {
        ConfigNode loaded = G.GSON.fromJson(fileWorker.readFromFile(), ConfigNode.class);

        this.subNodes = new LinkedHashMap<>(loaded.subNodesToMap());
        this.rawParams = new LinkedHashMap<>(loaded.valuesToMap());
        this.sourceElement = loaded.getSourceElement();
    }

    public void save() throws IOException {
        fileWorker.writeToFile(G.GSON.toJson(this));
    }
}
