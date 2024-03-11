package Common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
}
