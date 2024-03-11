package Server.Storage;
import Server.Models.Organization;
import org.json.simple.JSONObject;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class StorageConnector {
    public static ServerStorage storage = new ServerStorage();
    public static CollectionManager manager = new CollectionManager();

    public static void init() {
        manager.init(Stream.of(storage.read())
                .map((JSONObject it) -> new Organization().from(it))
                .filter(Objects::nonNull)
                .toArray(Organization[]::new));
    }

    public static String save(String filename) {
        return storage.write((JSONObject[]) Stream.of(manager.getAll())
                .map((Organization it) -> it.json).toArray(), filename);
    }
}
