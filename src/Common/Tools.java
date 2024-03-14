package Common;

import Server.Models.MBoolean;
import Server.Models.ModelFieldAdapter;
import Server.Models.MDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tools {
    static Gson json = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MDate.class, new ModelFieldAdapter<MDate>())
            .registerTypeAdapter(MBoolean.class, new ModelFieldAdapter<MBoolean>())
            .create();

    public static String objectToJSON(Object object) {
        return json.toJson(object);
    }
}