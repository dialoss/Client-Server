package Server.Storage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorage {
    protected Path source;
    private static String BASE_PATH = "src/Common/data/";

    public FileStorage() {
        this.changeSource("data.json");
    }

    public FileStorage changeSource(String path) {
        this.source = Paths.get(BASE_PATH + path).toAbsolutePath();
        return this;
    }

    public String _read() {
        try {
            FileInputStream f = new FileInputStream(this.source.toString());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(f, 200);
            String data = "";
            int i;
            while ((i = bufferedInputStream.read()) != -1) {
                data += (char) i;
            }
            f.close();
            return data;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public void _write(String data) {
        try {
            FileOutputStream f = new FileOutputStream(this.source.toFile());
            PrintStream printStream = new PrintStream(f);
            printStream.print(data);
            f.flush();
            f.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
