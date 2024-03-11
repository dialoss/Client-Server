package Server.Storage;

import Server.Models.Organization;
import java.util.*;

interface ICollectionManager<T> {
    void delete(T item);

    void delete(Integer id);

    void update(T item);

    void insert(T item);

    T get(Integer id);

    void clear();
    void update(Integer id, T item);
}

class BaseCollectionManager<T extends OrderedItem> implements ICollectionManager<T> {
    LinkedHashMap<Integer, T> collection;
    public Info info;

    @Override
    public void delete(T item) {
        this.collection.remove(item.getId());
    }

    @Override
    public void delete(Integer id) {
        this.collection.remove(id);
    }

    @Override
    public void update(T item) {
        this.collection.replace(item.getId(), item);
    }

    @Override
    public void update(Integer id, T item) {
        this.collection.replace(id, item);
    }

    @Override
    public void insert(T item) {
        this.collection.put(item.getId(), item);
    }

    @Override
    public T get(Integer id) {
        return this.collection.get(id);
    }

    @Override
    public void clear() {
        this.collection.clear();
    }

    public class Info {
        private final Date initDate;
        private final String type;

        Info() {
            this.initDate = new Date();
            this.type = BaseCollectionManager.this.collection.getClass().getName() + Organization.class;
        }

        public Integer getSize() {
            return BaseCollectionManager.this.collection.size();
        }

        public String getInfo() {
            return String.format("Тип: %s\nДата %s:\nРазмер: %s", this.type, this.initDate, this.getSize());
        }
    }
}

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

    public void init(Organization[] items) {
        for (Organization it : items) this.insert(it);
    }
}
