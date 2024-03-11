
package Server.Storage;


import Common.Tools;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONStorage extends FileStorage {
    public JSONStorage() {
        super();
    }

    public JSONObject[] read() {
        String text = super._read();
        JSONParser parser = new JSONParser();
        try {
            Object parsed = parser.parse(text);
            if (parsed instanceof JSONArray)
                return (JSONObject[]) ((JSONArray) parsed).toArray();
            else if (parsed instanceof JSONObject)
                return new JSONObject[]{(JSONObject) parsed};
        } catch (ParseException e) {
            System.out.println(e);
        }
        return new JSONObject[]{new JSONObject()};
    }

    public String write(JSONObject[] data, String filename) {
        this.changeSource(filename);
        return this.write(data);
    }

    public String write(JSONObject[] data) {
        super._write(Tools.itemsToString(data,
                (Object t) -> Tools.beautifyJSON(((JSONObject) t).toJSONString())));
        return this.fileAppend.path.toString();
    }
}

