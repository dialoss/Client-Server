package Common;

import Client.Shell.Shell;
import Client.Shell.ShellColors;
import Server.Commands.List.CommandArgument;
import Server.Models.BaseModel;
import Server.Models.ModelField;
import Server.Serializer.Serializer;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class Form {
    Shell shell;
    CommandArgument argument;

    public Form(CommandArgument argument, Shell shell) {
        this.shell = shell;
        this.argument = argument;
    }

    private Object processInput(Class<?> cl) {
        if (BaseModel.isModel(cl)) return this.getModel(cl);
        else return this.getValue(cl);
    }

    private Object getValue(Class<?> type) {
        Serializer s = new Serializer();
        while (true) {
            String val = this.shell.input();
            if (val == null) break;
            try {
                return s.serializeValue(type, val);
            } catch (Exception e) {
                this.shell.error(e.toString());
            }
        }
        return null;
    }

    private Object getModel(Class<?> cl) {
        Field[] fields = cl.getDeclaredFields();
        JSONObject result = new JSONObject();
        shell.print(String.format("Заполните объект %s", ShellColors.BLUE + cl.getName() + ShellColors.RESET));

        for (Field f : fields) {
            result.put(f.getName(), this.awaitInput(f));
        }
        return result;
    }

    private Object awaitInput(Field f) {
        if (BaseModel.isModel(f.getType())) {
            return this.processInput(f.getType());
        }
        Serializer s = new Serializer();

        ModelField params = s.getParameters(f);
        if (params != null && params.AUTO_GENERATE()) {
            try {
                return f.getType().getConstructor().newInstance();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        shell.print(String.format("Введите поле %s тип %s", ShellColors.BLUE + f.getName() + ShellColors.RESET, f.getGenericType()));
        if (Enum.class.isAssignableFrom(f.getType())) {
            shell.print("Возможные значения:");
            for (Field enumField : f.getType().getDeclaredFields()) {
                shell.print(enumField.getName());
            }
        }
        while (true) {
            String val = this.shell.input();
            if (val == null) break;
            try {
                return s.serializeField(f, val);
            } catch (Exception e) {
                this.shell.error(e.toString());
            }
        }
        return null;
    }

    public Object get() {
        return this.processInput(this.argument.type);
    }
}
