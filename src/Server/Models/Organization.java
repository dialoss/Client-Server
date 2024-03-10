package Server.Models;

import Server.Storage.OrderedItem;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static Server.Models.FieldParameters.*;

public class Organization extends BaseModel {
    public Organization() {
        super();
        this.fields = Map.of(
                "name", new ModelField<>("", Map.of(
                        NOT_NULL, true,
                        NOT_EMPTY, true
                )),
                "coordinates", new ModelField<>(null, Map.of(
                        NOT_NULL, true
                )),
                "creationDate", new ModelField<>(null, Map.of(
                        NOT_NULL, true,
                        AUTO_GENERATE, true
                )),
                "annualTurnover", new ModelField<>(0F, Map.of(
                        MIN, 1
                )),
                "postalAddress", new ModelField<Address>(null, Map.of(
                        NOT_NULL, true
                ))
        );
//    private final ModelField<OrganizationType> type = new ModelField<>(null, Map.of(
//            NOT_NULL, true
//    ));
    }
}