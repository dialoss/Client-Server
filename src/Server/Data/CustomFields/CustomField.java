package Server.Data.CustomFields;

import java.io.Serializable;

public abstract class CustomField implements Serializable {
    abstract Object getValue();
}
