
package Server.Storage;


import Common.FileStorage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class JSONStorage extends FileStorage {
    JSONStorage(String filename) {
        super("src/data/" + filename);
    }

    public JSONObject read() {
        String text = super._read();
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(text);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return new JSONObject();
    }

    public void write(JSONObject data) {
        super._write(data.toJSONString());
    }
}

