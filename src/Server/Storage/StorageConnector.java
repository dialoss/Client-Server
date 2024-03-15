package Server.Storage;

import Common.Tools;
import Server.Models.MObject;
import Server.Models.Organization;

public class StorageConnector {
    public static JSONStorage fileStorage = new JSONStorage();
    public static DBManager dbManager = new DBManager();
    public static CollectionManager manager = new CollectionManager();

    public static void loadFile() {
        loadFile("data.json");
    }

    public static void loadFile(String filename) {
        fileStorage.changeSource(filename);
        for (MObject o : fileStorage.read()) {
            Organization item = (Organization) new Organization().from(o);
            manager.insert(item);
        }
    }

    public static String saveFile(String filename) {
        return fileStorage.write(Tools.objectToJSON(manager.getAll()), filename);
    }
}