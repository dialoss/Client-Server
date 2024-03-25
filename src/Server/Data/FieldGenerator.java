package Server.Data;

import Common.Models.OrganizationType;
import Server.Data.CustomFields.MBoolean;
import Server.Data.CustomFields.MDate;
import me.xdrop.jrand.Generator;
import me.xdrop.jrand.JRand;

import java.util.Map;

public class FieldGenerator {
    private static final Map<Class<?>, Generator<?>> generators = Map.of(
            String.class, JRand.string().range(40, 49),
            Double.class, JRand.dbl().range(-10000, 10000),
            Float.class, JRand.flt().range(-10000, 10000),
            Integer.class, new Generator<Integer>() {
                @Override
                public Integer gen() {
                    return (int) (Math.random() * 1e9);
                }
            },
            MDate.class, new Generator<MDate>() {
                @Override
                public MDate gen() {
                    return new MDate();
                }
            },
            OrganizationType.class, new Generator<Enum<OrganizationType>>() {
                @Override
                public Enum<OrganizationType> gen() {
                    OrganizationType[] v = OrganizationType.values();
                    return v[(int) Math.floor(Math.random() * v.length)];
                }
            },
            MBoolean.class, new Generator<MBoolean>() {
                @Override
                public MBoolean gen() {
                    return new MBoolean(JRand.bool().gen());
                }
            }
            );

    public static Object random(Class<?> type) {
        return generators.get(type).gen();
    }
}