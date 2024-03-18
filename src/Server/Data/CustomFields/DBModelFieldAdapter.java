package Server.Data.CustomFields;

public class DBModelFieldAdapter<T> {
    public Object serialize(T src) {
        return src.toString();
    }
}
