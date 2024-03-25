package Common.Models;

import Client.Shell.ShellColors;
import Common.Exceptions.InvalidModelException;
import Common.Serializer.Serializer;
import Server.Data.FieldGenerator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class BaseModel implements Serializable {
    static final long serialVersionUID = 1L;

    @ModelField(UNIQUE = true, AUTO_GENERATE = true)
    public Integer id;

    private Field[] getFields() {
        ArrayList<Field> fieldArrayList = new ArrayList<>();
        for (Field f : this.getClass().getFields()) {
            try {
                ModelField params = Serializer.getParameters(f);
                if (params == null) continue;
                fieldArrayList.add(f);
            } catch (Exception e) {
            }
        }
        return fieldArrayList.toArray(Field[]::new);
    }

    public BaseModel from(MObject object, boolean random) {
        Field[] fields = this.getFields();
        for (Field f : fields) {
            try {
                if (f.getType().isAssignableFrom(BaseModel.class) && f.getName().equals("id")) continue;
                ModelField params = Serializer.getParameters(f);
                if (params == null) continue;
                f.setAccessible(true);

                Object value = random ? null : object.get(f.getName());
                if (BaseModel.class.isAssignableFrom(f.getType())) {
                    BaseModel m = (BaseModel) f.getType().getDeclaredConstructor().newInstance();
                    f.set(this, m.from((MObject) value, random));
                    continue;
                }

                if (value == "") value = null;

                if (value == null && params.AUTO_GENERATE()) {
                    value = FieldGenerator.random(f.getType());
                } else {
                    if (random) value = FieldGenerator.random(f.getType());
                    value = Serializer.serializeField(f, String.valueOf(value));
                }
                f.set(this, value);
            } catch (Exception e) {
                throw new InvalidModelException(e.toString());
            }
        }
        return this;
    }

    public BaseModel from(MObject object) {
        return this.from(object, false);
    }

    public BaseModel random() {
        return this.from(null, true);
    }

    public static boolean isModel(Class<?> type) {
        return BaseModel.class.isAssignableFrom(type);
    }

    @Override
    public String toString() {
        String result = ShellColors.format(ShellColors.BLUE, this.getClass().getSimpleName()) + "\n";
        for (Field f : this.getFields()) {
            f.setAccessible(true);
            if (f.getName().equals("id") && !this.getClass().getSimpleName().equals("Organization")) continue;
            try {
                result = result.concat(f.getName() + ": " + f.get(this).toString() + "\n");
            } catch (Exception e) {
            }
        }
        return "\n" + result.strip();
    }


    public Integer getId() {
        return this.id;
    }
}