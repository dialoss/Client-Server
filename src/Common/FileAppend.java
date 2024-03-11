package Common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileAppend {
    PrintWriter out;
    public Path path;

    FileAppend(Path source) {
        Path outPath = Paths.get(source.getParent().toString(), "out");
        this.path = outPath;
        try {
            FileWriter f = new FileWriter(outPath.toString());
            BufferedWriter b = new BufferedWriter(f);
            this.out = new PrintWriter(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
