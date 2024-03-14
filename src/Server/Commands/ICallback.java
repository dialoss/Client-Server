package Server.Commands;

@FunctionalInterface
public interface ICallback<T> {
    void call(T values);
}