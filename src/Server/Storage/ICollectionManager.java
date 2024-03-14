package Server.Storage;

public interface ICollectionManager<T> {
    void delete(T item);

    void delete(Integer id);

    void update(T item);

    void insert(T item);

    T get(Integer id);

    void clear();

    void update(Integer id, T item);
}