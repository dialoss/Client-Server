package Server.Storage;

import Common.Tools;
import Server.Models.Organization;
import org.json.simple.JSONObject;

public class StorageConnector {
    public static ServerStorage storage = new ServerStorage();
    public static CollectionManager manager = new CollectionManager();

    public static void load() {
        load("data.json");
    }

    public static void load(String filename) {
        storage.changeSource(filename);
        for (JSONObject o : storage.read()) {
            Organization item = (Organization) new Organization().from(o);
            manager.insert(item);
        }
    }

    public static String save(String filename) {
        return storage.write(Tools.objectToJSON(manager.getAll()), filename);
    }
}