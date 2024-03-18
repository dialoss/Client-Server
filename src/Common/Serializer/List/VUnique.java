package Common.Serializer.List;

import Common.Exceptions.InvalidModelException;
import Server.Commands.List.Query;
import Server.Storage.StorageConnector;

import java.lang.reflect.Field;

public class VUnique extends Validator {
    public VUnique() {
        super("UNIQUE");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if ((Boolean) declaredValue && new Query(StorageConnector.manager.getAll())
                .filter(f.getName(), (Object v) -> v.equals(value))
                .get().length > 0)
            throw new InvalidModelException("The value of the field %s must be unique - %s".formatted(f.getName(), value));
    }
}