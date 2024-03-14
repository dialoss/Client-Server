package Server.Models;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ModelFieldAdapter<T> implements JsonSerializer<T> {
    @Override
    public JsonElement serialize(T src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}