package Common;

import Server.Models.DateAdapter;
import Server.Models.MyDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tools {
    static Gson json = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MyDate.class, new DateAdapter())
            .create();

    public static String objectToJSON(Object object) {
        return json.toJson(object);
    }
}

