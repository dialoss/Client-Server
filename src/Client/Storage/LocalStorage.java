package Client.Storage;

import Common.Files.FileStorage;

import java.nio.file.Path;

public class LocalStorage {
    private static final FileStorage fileStorage = new FileStorage("src/Client/Storage/");

    public static void write(String filename, String data) {
        fileStorage.changeSource(filename);
        fileStorage._write(data);
    }

    public static String read(String filename) {
        fileStorage.changeSource(filename);
        return fileStorage._read();
    }

    public static String read(Path path) {
        fileStorage.setSource(path);
        return fileStorage._read();
    }
}
