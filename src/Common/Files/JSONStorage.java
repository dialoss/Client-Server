package Common.Files;


import Common.Exceptions.InvalidFileException;
import Common.Models.MObject;
import Common.Tools;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

public class JSONStorage extends FileStorage {
    public JSONStorage(String path) {
        super(path);
    }

    public static MObject convertObject(Map<String, Object> obj) {
        MObject result = new MObject();
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            Object v = entry.getValue();
            String k = entry.getKey();
            if (v.getClass().isAssignableFrom(LinkedTreeMap.class))
                result.put(k, convertObject((Map<String, Object>) v));
            else result.put(k, v);
        }
        return result;
    }


    public MObject[] read() {
        String text = super._read();

        try {
            MObject[] objects = (MObject[]) Tools.parse(text, MObject[].class);
            for (int i = 0; i < objects.length; i++) {
                MObject converted = convertObject(objects[i]);
                objects[i] = converted;
            }
            return objects;
        } catch (Exception e) {
            throw new InvalidFileException("Invalid collection JSON file");
        }
    }

    public String write(String data, String filename) {
        this.changeSource(filename);
        super._write(data);
        return this.source.toString();
    }
}