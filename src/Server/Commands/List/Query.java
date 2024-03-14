package Server.Commands.List;

import Common.EventBus.Callback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Query {
    Object[] items;

    public Query(Object[] items) {
        this.items = items;
    }

    public Query reduce(String f) {
        ArrayList<Object> reduced = new ArrayList<>();
        Stream.of(this.items).forEach((Object it) -> {
            Field[] fields = it.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(f)) {
                    try {
                        reduced.add(field.get(it));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        this.items = reduced.toArray();
        return this;
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

    public Stream<Object> stream() {
        return Stream.of(this.items);
    }

    public Query sort(String f, Callback<Object> p) {
        return this;
    }

    public Object[] get() {
        return this.items;
    }

    public Object[] sorted() {
        return this.sorted(1);
    }

    public Object[] sorted(int order) {
        if (order == 0) return Stream.of(this.items).sorted().toArray();
        else {
            List<Object> result = new ArrayList<>(Stream.of(this.items).sorted().toList());
            Collections.reverse(result);
            return result.toArray();
        }
    }
}