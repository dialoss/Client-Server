package Server.Models;

public class DBModelFieldAdapter<T> {
    public Object serialize(T src) {
        return src.toString();
    }
}
