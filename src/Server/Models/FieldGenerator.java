package Server.Models;

import Common.Exceptions.InvalidModelException;
import Server.Serializer.Serializer;

import java.lang.reflect.Field;

public class FieldGenerator {
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
}
