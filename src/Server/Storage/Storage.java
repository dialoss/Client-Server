
package Server.Storage;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


class FileStorage {
    Path source;

    FileStorage(String filename) {
        this.source = Paths.get("src/data/" + filename).toAbsolutePath();
    }

    String _read() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.source.toString());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
            String data = "";
            int i;
            while((i = bufferedInputStream.read())!= -1){
                data += (char)i;
            }
            return data;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return "";
    }

    void _write(String data) {
        try {
            FileOutputStream fos = new FileOutputStream(this.source.toFile());
            PrintStream printStream = new PrintStream(fos);
            printStream.print(data);
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class JSONStorage extends FileStorage {
    JSONStorage(String filename) {
        super(filename);
    }

    public JSONObject read() {
        String text = super._read();
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(text);
        } catch (ParseException e) {
            System.out.println(e);
//            throw new RuntimeException(e);
        }
        return new JSONObject();
    }

    public void write(JSONObject data) {
        super._write(data.toJSONString());
    }
}

