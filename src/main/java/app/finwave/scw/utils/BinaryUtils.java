package app.finwave.scw.utils;

import java.nio.ByteBuffer;

public class BinaryUtils {
    public static BinaryObject string(String string){
        return new BinaryObject() {
            @Override
            public ByteBuffer save() {
                return ByteBuffer.wrap(string.getBytes());
            }

            @Override
            public int size() {
                return string.getBytes().length;
            }

            @Override
            public void load(ByteBuffer savedData) {

            }
        };
    }

    public static String string(BinaryObject binaryObject){
        return new String(binaryObject.save().array());
    }

    public static void putString(String string, ByteBuffer byteBuffer) {
        byteBuffer.putInt(string.length());
        byteBuffer.put(string.getBytes());
    }

    public static String getString(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[byteBuffer.getInt()];
        byteBuffer.get(bytes);

        return new String(bytes);
    }

    public static int getStringCapacity(String string) {
        return string.getBytes().length + 4;
    }
}
