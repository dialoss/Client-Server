package Server.Models;

import Common.Exceptions.InvalidModelException;
import Server.Serializer.Serializer;
import me.xdrop.jrand.Generator;
import me.xdrop.jrand.JRand;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

public class FieldGenerator {
    private static final Map<Class<?>, Generator<?>> generators = Map.of(
            String.class, JRand.string().range(40, 49),
            Double.class, JRand.dbl().range(1, 100),
            Float.class, JRand.flt().range(1, 1000),
            Boolean.class, JRand.bool(),
            MyDate.class, new Generator<MyDate>() {
                @Override
                public MyDate gen() {
                    return new MyDate();
                }
            },
            OrganizationType.class, new Generator<Enum<OrganizationType>>() {
                @Override
                public Enum<OrganizationType> gen() {
                    OrganizationType[] v = OrganizationType.values();
                    return v[(new Random(1)).nextInt(v.length)];
                }
            }
    );

    public static Object getValue(Field f) {
        Class<?> type = f.getType();
        try {
            if (Integer.class.isAssignableFrom(type)) {
                return (int) (Math.random() * 1e9);
            } else if (Number.class.isAssignableFrom(f.getType())) {
                return Serializer.castValue(type, String.valueOf(Math.random() * 1e9));
            } else {
                return type.getConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new InvalidModelException(e.toString());
        }
    }

    public static Object random(Class<?> type) {
        return generators.get(type).gen();
    }
}
