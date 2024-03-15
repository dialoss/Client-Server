package Server.Storage.Files;


import Server.Models.MObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONStorage extends FileStorage {
    public JSONStorage() {
        super();
    }

    public MObject[] read() {
        String text = super._read();
        JSONParser parser = new JSONParser();
        try {
            Object parsed = parser.parse(text);
            if (parsed instanceof JSONArray) {
                MObject[] list = new MObject[((JSONArray) parsed).size()];
                int i = 0;
                for (Object o : (JSONArray) parsed) {
                    list[i++] = (MObject) o;
                }
                return list;
            }
            else if (parsed instanceof MObject)
                return new MObject[]{(MObject) parsed};
        } catch (ParseException e) {
            System.out.println(e);
        }
        return new MObject[]{new MObject()};
    }

    public String write(String data, String filename) {
        this.changeSource(filename);
        super._write(data);
        return this.source.toString();
    }
}