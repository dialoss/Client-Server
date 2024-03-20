package Common.Connection;

@FunctionalInterface
public interface ICallback<T> {
    Response call(T values) throws Exception;
}