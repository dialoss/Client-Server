package Server.Storage;

import Server.Models.BaseModel;
import Server.Models.Organization;

import java.util.Date;
import java.util.LinkedHashMap;

public class BaseCollectionManager<T extends BaseModel> implements ICollectionManager<T> {
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
            return String.format("Type: %s\nDate: %s\nSize: %s", this.type, this.initDate, this.getSize());
        }
    }
}