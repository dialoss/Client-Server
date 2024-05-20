package Server.Storage.Collection;

import Common.Models.BaseModel;
import Common.Models.Organization;
import Server.ConnectionManagers.Notification;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class BaseCollectionManager<T extends BaseModel> implements ICollectionManager<T> {
    LinkedHashMap<Integer, T> collection;
    ReentrantLock locker = new ReentrantLock();
    public Info info;

    @Override
    public synchronized void delete(T item) {
        locker.lock();
        this.collection.remove(item.getId());
        Notification.set();
        locker.unlock();
    }

    @Override
    public synchronized void delete(Integer id) {
        locker.lock();
        this.collection.remove(id);
        Notification.set();
        locker.unlock();

    }

    @Override
    public synchronized void update(T item) {
        locker.lock();
        this.collection.replace(item.getId(), item);
        Notification.set();
        locker.unlock();


    }

    @Override
    public synchronized void update(Integer id, T item) {
        locker.lock();
        this.collection.replace(id, item);
        Notification.set();
        locker.unlock();

    }

    @Override
    public synchronized void insert(T item) {
        locker.lock();
        this.collection.put(item.getId(), item);
        Notification.set();
        locker.unlock();

    }

    @Override
    public T get(Integer id) {
        return this.collection.get(id);
    }

    @Override
    public synchronized void clear() {
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