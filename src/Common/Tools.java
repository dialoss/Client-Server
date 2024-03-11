package Common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Tools {
    static Gson json = new GsonBuilder().setPrettyPrinting().create();

    public static String beautifyJSON(String data) {
        JsonElement je = JsonParser.parseString(data);
        return json.toJson(je);
    }
}
