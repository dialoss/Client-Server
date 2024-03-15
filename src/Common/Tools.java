package Common;

import Server.Models.MBoolean;
import Server.Models.JSONModelFieldAdapter;
import Server.Models.MDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tools {
    static Gson json = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MDate.class, new JSONModelFieldAdapter<MDate>())
            .registerTypeAdapter(MBoolean.class, new JSONModelFieldAdapter<MBoolean>())
            .create();

    public static String objectToJSON(Object object) {
        return json.toJson(object);
    }
}