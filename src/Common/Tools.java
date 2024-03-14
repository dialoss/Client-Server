package Common;

import Server.Models.DateAdapter;
import Server.Models.MyDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.function.Function;

public class Tools {
    static Gson json = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MyDate.class, new DateAdapter())
            .create();

    public static String beautifyJSON(String data) {
        JsonElement je = JsonParser.parseString(data);
        return json.toJson(je);
    }

    public static String objectToJSON(Object object) {
        return json.toJson(object);
    }

    public static String stringify(Object[] items) {
        return stringify(items, Object::toString, true);
    }

    public static String stringify(Object[] items, boolean numerate) {
        return stringify(items, Object::toString, numerate);
    }

    public static String stringify(Object[] items, Function<Object, String> modificator, boolean numerate) {
        String result = "";
        int i = 1;
        for (Object it : items) {
            result = result.concat((numerate ? i++ + " " : "") + modificator.apply(it) + "\n");
        }
        if (result.equals("")) return "Нет элементов";
        return result;
    }
}
