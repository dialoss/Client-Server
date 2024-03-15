package Client;

import Common.Files.FileStorage;

public class LocalStorage {
    private static final FileStorage fileStorage = new FileStorage("src/Client/Files/");

    public static void write(String filename, String data) {
        fileStorage.changeSource(filename);
        fileStorage._write(data);
    }

    public static String read(String filename) {
        fileStorage.changeSource(filename);
        return fileStorage._read();
    }
}
