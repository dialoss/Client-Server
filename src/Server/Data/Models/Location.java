package Server.Data.Models;

import Server.Data.ModelField;

public class Location extends BaseModel {
    @ModelField(NULL = true)
    public Double x;
    @ModelField(NULL = true)
    public Float y;
    @ModelField
    public Float z; //Field cannot be null
    public static final Class<?> address = Address.class;
}