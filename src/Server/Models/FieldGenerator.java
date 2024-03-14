package Server.Models;

import me.xdrop.jrand.Generator;
import me.xdrop.jrand.JRand;

import java.util.Map;
import java.util.Random;

public class FieldGenerator {
    private static final Map<Class<?>, Generator<?>> generators = Map.of(
            String.class, JRand.string().range(40, 49),
            Double.class, JRand.dbl().range(1, 1000),
            Float.class, JRand.flt().range(1, 1000),
            Boolean.class, JRand.bool(),
            Integer.class, new Generator<Integer>() {
                @Override
                public Integer gen() {
                    return (int) (Math.random() * 1e9);
                }
            },
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

    public static Object random(Class<?> type) {
        return generators.get(type).gen();
    }
}
