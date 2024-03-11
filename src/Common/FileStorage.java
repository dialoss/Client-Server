package Common;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorage {
    Path source;

    public FileStorage(String path) {
        this.source = Paths.get(path).toAbsolutePath();
    }

    public String _read() {
        try {
            FileInputStream f = new FileInputStream(this.source.toString());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(f, 200);
            String data = "";
            int i;
            while((i = bufferedInputStream.read())!= -1){
                data += (char)i;
            }
            f.close();
            return data;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
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
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
