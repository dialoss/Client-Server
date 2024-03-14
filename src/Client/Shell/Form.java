package Client.Shell;

import Common.Exceptions.EmptyInputException;
import Server.Commands.List.CommandArgument;
import Server.Models.BaseModel;
import Server.Models.ModelField;
import Server.Models.Organization;
import Server.Serializer.Serializer;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Form {
    IForm form;
    CommandArgument argument;

    public Form(CommandArgument argument, IForm form) {
        this.form = form;
        this.argument = argument;
    }

    private Object processInput(Class<?> cl) {
        if (BaseModel.isModel(cl)) return this.getModel(cl);
        else return this.getValue(cl);
    }

    private Object getValue(Class<?> type) {
        String val = this.form.input();
        if (val == null) throw new EmptyInputException();
        try {
            return Serializer.serializeValue(type, val);
        } catch (Exception e) {
            this.form.validationError(e.toString());
        }
        return null;
    }

    private JSONObject getModel(Class<?> cl) {
        Field[] fields = cl.getDeclaredFields();
        JSONObject result = new JSONObject();
        this.form.out("Fill in the %s object".formatted(ShellColors.format(ShellColors.BLUE, cl.getName())));

        for (Field f : fields) {
            result.put(f.getName(), this.awaitInput(f));
        }
        return result;
    }

    private Object awaitInput(Field f) {
        if (BaseModel.isModel(f.getType())) {
            return this.processInput(f.getType());
        }
        ModelField params = Serializer.getParameters(f);
        if (params != null && params.AUTO_GENERATE()) return null;

        this.form.out("Enter field %s type %s".formatted(ShellColors.format(ShellColors.BLUE, f.getName()), f.getGenericType()));
        if (Enum.class.isAssignableFrom(f.getType())) {
            this.form.out("Possible values:");
            try {
                Method method = f.getType().getDeclaredMethod("values");
                Object[] obj = (Object[]) method.invoke(f);
                for (Object enumField : obj) {
                    this.form.out(enumField.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error");
            }
        }
        while (true) {
            String val = this.form.input();
            if (val == null) break;
            try {
                return Serializer.serializeField(f, val);
            } catch (Exception e) {
                this.form.validationError(e.toString());
            }
        }
        return null;
    }

    public Object get() {
        Object value = this.processInput(this.argument.type);
        if (value == null) throw new RuntimeException("Value error");
        if (value instanceof JSONObject) {
            value = new Organization().from((JSONObject) value);
        }
        return value;
    }
}