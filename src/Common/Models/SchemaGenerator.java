package Common.Models;

import Common.Serializer.Serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class SchemaGenerator {
    public static MObject generate() {
        ArrayList<Field> fieldArrayList = new ArrayList<>();
        for (Field f : Organization.class.getFields()) {
            try {
                ModelField params = Serializer.getParameters(f);
                if (params == null) continue;
                fieldArrayList.add(f);
            } catch (Exception e) {
            }
        }
        MObject data = new MObject();
        Map<String, String> typeMapper = Map.of(
                "float", "number",
                "mboolean", "boolean",
                "integer", "number",
                "mdate", "date",
                "string", "text"
        );
        Field[] fields = fieldArrayList.toArray(Field[]::new);
        for (Field f : fields) {
            try {
                if (f.getName().equals("id")) continue;
                ModelField params = Serializer.getParameters(f);
                if (params == null) continue;
                data.put(f.getName(), new MObject(Map.of(
                        "type", typeMapper.get(f.getType().getSimpleName().toLowerCase())
                )));
            } catch (Exception e) {
            }
        }
        return data;
    }
}
