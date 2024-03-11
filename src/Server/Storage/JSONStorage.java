
package Server.Storage;


import Common.FileStorage;
import Common.Tools;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONStorage extends FileStorage {
    public JSONStorage(String filename) {
        super("src/Common/data/" + filename);
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
        super._write(Tools.beautifyJSON(data.toJSONString()));
    }

    public String write(JSONObject[] data) {
        String out = "";
        for (JSONObject obj : data) {
            out = out.concat(Tools.beautifyJSON(obj.toJSONString()));
        }
        super._write(out);
        return this.fileAppend.path.toString();
    }
}

