package Client;

import Server.Commands.List.CommandArgument;
import Server.Models.BaseModel;
import Server.Serializer.Serializer;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class Form {
    Shell shell;
    CommandArgument argument;

    Form(CommandArgument argument, Shell shell) {
        this.shell = shell;
        this.argument = argument;
    }

    private JSONObject processInput(Class<?> cl) {
        Field[] fields = cl.getDeclaredFields();
        JSONObject result = new JSONObject();
        shell.print(String.format("Заполните объект %s", ShellColors.BLUE + cl.getName() + ShellColors.RESET));

        for (Field f : fields) {
            result.put(f.getName(), this.awaitInput(f));
        }
        return result;
    }

    private Object awaitInput(Field f) {
        if (BaseModel.class.isAssignableFrom(f.getType())) {
            return this.processInput(f.getType());
        }
        shell.print(String.format("Введите поле %s %s", ShellColors.BLUE + f.getName() + ShellColors.RESET, f.getGenericType()));
        Serializer s = new Serializer(null);
        while (true) {
            String val = this.shell.input();
            try {
                s.validateField(f, val);
                return val;
            } catch (Exception e) {
                this.shell.error(e.toString());
            }
        }
    }

    public JSONObject get() {
        return this.processInput(this.argument.type);
    }
}
