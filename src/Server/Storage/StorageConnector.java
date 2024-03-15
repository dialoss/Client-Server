package Server.Storage;

import Common.Tools;
import Server.Models.MObject;
import Server.Models.Organization;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.Database.DBManager;
import Common.Files.JSONStorage;

public class StorageConnector {
    public static JSONStorage fileStorage;
    public static DBManager dbManager;
    public static CollectionManager manager;

    public static void init() {
        dbManager = new DBManager();
        fileStorage = new JSONStorage("src/Common/data/");
        manager = new CollectionManager();
    }

    public static void loadFile() {
        loadFile("data.json");
    }

    public static void loadFile(String filename) {
        fileStorage.changeSource(filename);
        loadCollection(fileStorage.read());
    }

    public static void loadDB() throws Exception {
        loadCollection(dbManager.load());
    }

    public static void loadCollection(MObject[] items) {
        for (MObject o : items) {
            Organization item = (Organization) new Organization().from(o);
            manager.insert(item);
        }
    }

    public static String saveFile(String filename) {
        return fileStorage.write(Tools.objectToJSON(manager.getAll()), filename);
    }

    public static void saveDB() throws Exception {
        dbManager.save(manager.getAll());
    }
}