package su.knst.scw.utils;

import su.knst.scw.utils.exceptions.CantWriteToTargetException;
import su.knst.scw.utils.exceptions.TargetNotFileException;

import java.io.*;

public class SimpleFileWorker {
    protected final File target;

    public SimpleFileWorker(File target) throws TargetNotFileException, IOException {
        if (!target.exists()) {
            target.getParentFile().mkdirs();
            target.createNewFile();
        }

        if (!target.isFile())
            throw new TargetNotFileException();

        this.target = target;
    }

    public void writeToFile(String text) throws IOException {
        if (!target.canWrite())
            throw new CantWriteToTargetException();

        BufferedWriter writer = new BufferedWriter(new FileWriter(target));
        writer.write(text);

        writer.flush();
        writer.close();
    }

    public String readFromFile() throws IOException {
        return readFromFile(128);
    }

    public String readFromFile(int bufferSize) throws IOException {
        if (!target.canRead())
            throw new CantWriteToTargetException();

        BufferedReader reader = new BufferedReader(new FileReader(target));
        StringBuilder builder = new StringBuilder();

        char[] buffer = new char[bufferSize];
        int readed;

        while ((readed = reader.read(buffer)) != -1)
            builder.append(buffer, 0, readed);

        reader.close();

        return builder.toString();
    }
}
