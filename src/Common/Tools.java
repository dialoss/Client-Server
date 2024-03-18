package Common;

import Server.Data.CustomFields.MBoolean;
import Server.Data.CustomFields.JSONModelFieldAdapter;
import Server.Data.CustomFields.MDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tools {
    static Gson json = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MDate.class, new JSONModelFieldAdapter<MDate>())
            .registerTypeAdapter(MBoolean.class, new JSONModelFieldAdapter<MBoolean>())
            .create();

    public static String stringify(Object object) {
        return json.toJson(object);
    }
    public static Object parse(String data, Class<?> cl) {
        return json.fromJson(data, cl);
    }
}