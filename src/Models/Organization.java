package Models;

import java.util.HashMap;
import java.util.Map;

import static Models.FieldParameters.*;

public class Organization extends BaseModel {
    private static final ModelField<Long> id = new ModelField<Long>(Map.of(
            NOT_NULL, true,
            MIN, 1,
            UNIQUE, true,
            AUTO_GENERATE, true
    ));
    private static final ModelField<String> name = new ModelField<String>(Map.of(
            NOT_NULL, true,
            NOT_EMPTY, true
    ));
    private static final ModelField<Coordinates> coordinates = new ModelField<Coordinates>(Map.of(
            NOT_NULL, true
    ));
    private static final ModelField<java.time.ZonedDateTime> creationDate = new ModelField<java.time.ZonedDateTime>(Map.of(
            NOT_NULL, true,
            AUTO_GENERATE, true
    ));
    private float annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null
}
