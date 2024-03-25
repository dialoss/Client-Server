package Common.Connection;

import java.io.*;

/**
 * Class for serializing and deserializing objects
 */
public class ObjectIO {
    public static ByteArrayOutputStream writeObject(Object data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(data);
        out.close();
        return bos;
    }

    public static Object readObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        return in.readObject();
    }
}