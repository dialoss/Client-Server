package Server.Serializer;

import Server.Serializer.List.*;

import java.util.HashMap;

public class Validators {
    static HashMap<String, Validator> validators = new HashMap<>();

    static {
        Validators.add(new VNull());
        Validators.add(new VMin());
        Validators.add(new VMaxLength());
        Validators.add(new VMinLength());
        Validators.add(new VNotEmpty());
    }

    static Validator get(String name) {
        return Validators.validators.get(name);
    }

    static void add(Validator v) {
        Validators.validators.put(v.name, v);
    }

    static String[] getParams() {
        return Validators.validators.keySet().toArray(String[]::new);
    }
}
