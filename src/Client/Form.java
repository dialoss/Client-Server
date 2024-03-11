package Client;

import Server.Commands.List.CommandArgument;
import Server.Models.BaseModel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Form {
    Shell shell;
    CommandArgument argument;
    Object result;

    Form(CommandArgument argument, Shell shell) {
        this.shell = shell;
        this.argument = argument;
        this.createForm();
    }

    private Object createForm() {
        Class<?> cl = argument.type;
        Field[] fields = cl.getDeclaredFields();
        Map<String, Object> result = new HashMap<>();
        for (Field f : fields) {
            shell.print(String.format("Поле %s %s", f.getName(), f.getGenericType()));
            result.put(f.getName(), this.awaitInput(f));
        }
        return result;
    }

    private Object awaitInput(Field f) {
        if (f.getClass().isAssignableFrom(BaseModel.class)) {
            return this.createForm();
        }
        this.shell.start();
    }

    public Object get() {
        return this.result;
    }
}
