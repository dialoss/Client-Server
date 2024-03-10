
package Server.Storage;


import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


interface IStorage {
    String read();
    void write();
}

interface IStorageInternal<T> {
    T open();
    void close();
}

public abstract class Storage implements IStorage {
    abstract void open();
    abstract void close();
}


class FileStorage extends Storage {
    Path source;

    FileStorage(String filename) {
        this.source = Paths.get("data/" + filename).toAbsolutePath();
    }

    @Override
    public String read() {
        return "";
    }

    @Override
    public void write() {

    }

    @Override
    void open() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.source.toString());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    void close() {

    }
}

class JSONStorage extends FileStorage {

    JSONStorage(String filename) {
        super(filename);
    }
}

