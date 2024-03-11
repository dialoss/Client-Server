package Server.Storage;

import Server.Models.Organization;

public class ServerStorage extends JSONStorage {
    CollectionManager manager;
    public ServerStorage() {
        super("Common.data.json");
        this.manager = new CollectionManager();
    }

    public void initialize() {
        Organization[] items = new Organization[1];
        items[0] = (Organization) new Organization().from(this.read(), true);
        this.manager.init(items);
    }
}
