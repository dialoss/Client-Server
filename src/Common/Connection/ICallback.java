package Common.Connection;

import java.io.IOException;

@FunctionalInterface
public interface ICallback<T> {
    Response call(T values) throws IOException;
}