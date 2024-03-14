package Common;

import Server.Models.DateAdapter;
import Server.Models.MDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tools {
    static Gson json = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MDate.class, new DateAdapter())
            .create();

    public static String objectToJSON(Object object) {
        return json.toJson(object);
    }
}

