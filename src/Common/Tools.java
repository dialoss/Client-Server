package Common;

import Server.Models.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.function.Function;

public class Tools {
    static Gson json = new GsonBuilder().setPrettyPrinting().create();

    public static JSONObject objectToJSON(Object obj) {
        JSONParser parser = new JSONParser();
        try {
            String s =json.toJson(obj);
            return (JSONObject) parser.parse(s);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public static String beautifyJSON(String data) {
        JsonElement je = JsonParser.parseString(data);
        return json.toJson(je);
    }

    public static String itemsToString(Object[] items) {
        return Tools.itemsToString(items, Object::toString);
    }

    public static String itemsToString(Object[] items, Function<Object, String> modificator) {
        String result = "";
        for (Object it : items) {
            result = result.concat(modificator.apply(it) + "\n");
        }
        if (result.equals("")) return "Нет элементов";
        return result;
    }

    public static String flatJSON(JSONObject object) {
        String result = "";
        for (Object value : object.values()) {
            String v = "";
            if (value instanceof JSONObject) {
                v = Tools.flatJSON((JSONObject) value);
            } else {
                v = value.toString();
            }
            if (!result.endsWith("\n") && !v.endsWith("\n")) v += "\n";
            result = result.concat(v);
        }
        return result;
    }

    public static Date serializeDate(Object value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse((String) value, formatter);
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
