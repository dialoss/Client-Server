package Server.Storage.Collection;

import Common.Models.Organization;

import java.util.LinkedHashMap;

public class CollectionManager extends BaseCollectionManager<Organization> {
    public Organization[] getAll() {
        Organization[] items = new Organization[this.collection.size()];
        int i = 0;
        for (Integer key : this.collection.keySet()) {
            items[i++] = this.collection.get(key);
        }
        return items;
    }

    public CollectionManager() {
        this.collection = new LinkedHashMap<>();
        this.info = new CollectionManager.Info();
    }
}