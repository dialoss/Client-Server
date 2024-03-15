package Server.Storage;

import Server.Models.MObject;

public class DBStorage extends FileStorage {
    public MObject[] read() {
        return new MObject[]{new MObject()};
    }

    public String write(String data, String filename) {
        return this.source.toString();
    }
}
