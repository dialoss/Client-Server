package Server.Commands.List;

import Common.EventBus.Callback;

import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Query {
    Object[] items;
    Class<?> cl;

    public Query(Object[] items, Class<?> cl) {
        this.items = items;
        this.cl = cl;
    }

    public Query filter(String f, Predicate<? super Object> p) {
        this.items = Stream.of(this.items).filter((Object it) -> {
            Field[] fields = it.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(f)) {
                    try {
                        return p.test(field.get(it));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return false;
        }).toArray();
        return this;
    }

    public Query sort(String f, Callback<Object> p) {
        return this;
    }

    public Object[] get() {
        return this.items;
    }
}
