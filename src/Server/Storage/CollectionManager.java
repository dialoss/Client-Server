package Server.Storage;

import Server.Models.Organization;

import java.util.HashMap;
import java.util.LinkedHashMap;

interface ICollectionManager<T> {
    void delete(T item);
    void update(T item);
    void insert(T item);
    T get(Integer id);
}

class BaseCollectionManager<T extends OrderedItem> implements ICollectionManager<T> {
    LinkedHashMap<Integer, T> collection;

    @Override
    public void delete(T item) {
        this.collection.remove(item.getId());
    }

    @Override
    public void update(T item) {
        this.collection.replace(item.getId(), item);
    }

    @Override
    public void insert(T item) {
        this.collection.put(item.getId(), item);
    }

    @Override
    public T get(Integer id) {
        return this.collection.get(id);
    }
}

public class CollectionManager extends BaseCollectionManager<Organization> {


}
