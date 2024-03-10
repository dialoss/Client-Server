package Server.Storage;

public class ServerStorage extends JSONStorage {
    CollectionManager manager;
    public ServerStorage() {
        super("data/data.json");
        this.manager = new CollectionManager();
    }
}
