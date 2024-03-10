package Server.Storage;

import Server.Models.Organization;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;


interface ICollectionManager<T> {
    void delete(T item);

    void update(T item);

    void insert(T item);

    T get(Integer id);
}

class BaseCollectionManager<T extends OrderedItem> implements ICollectionManager<T> {
    LinkedHashMap<Integer, T> collection;
    public Info info;

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

    public class Info {
        private final Date initializationDate;
        private final String type;

        Info() {
            this.initializationDate = new Date();
            this.type = String.format("LinkedHashMap<Integer, %s>", "org");
        }

        public Integer getSize() {
            return BaseCollectionManager.this.collection.size();
        }

        public String getInfo() {
            return String.format("%s %s %s", this.type, this.initializationDate, this.getSize());
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

    public Organization[] query(Predicate<Organization> predicate) {
        return (Organization[]) Stream.of(this.getAll()).filter(predicate).toArray();
    }

    public CollectionManager() {
        this.collection = new LinkedHashMap<>();
        this.info = new CollectionManager.Info();
    }

    public void fill() {
        for (int i = 0; i < 5; i++) {
            this.insert(new Organization());
        }
    }

    public void init(Organization[] items) {
        for (Organization it : items) this.insert(it);
    }
}
