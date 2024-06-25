package app.finwave.scw.utils;

import java.nio.ByteBuffer;

public interface BinaryObject {
    ByteBuffer save();
    void load(ByteBuffer savedData);
    int size();
}
