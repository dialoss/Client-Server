package Common.EventBus;

@FunctionalInterface
public interface Callback<T> {
    void call(T data);
}
