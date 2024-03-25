package Common.Files;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorage {
    protected Path source;
    private String BASE_PATH = "";

    public FileStorage(String BASE_PATH) {
        this.BASE_PATH = BASE_PATH;
    }

    public FileStorage changeSource(String path) {
        this.source = Paths.get(BASE_PATH + path).toAbsolutePath();
        return this;
    }

    public void setSource(Path path) {
        this.source = path;
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

    public boolean checkExistance(String file) {
        this.changeSource(file);
        File f = new File(this.source.toString());
        return f.exists() && !f.isDirectory();
    }

    public boolean remove(String file) {
        this.changeSource(file);
        File f = new File(this.source.toString());
        if (f.exists() && !f.isDirectory()) {
            return f.delete();
        }
        return false;
    }
}