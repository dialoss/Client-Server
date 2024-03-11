package Server.Models;

public class Location extends BaseModel {
    @ModelField(NULL = true)
    public Double x;
    @ModelField(NULL = true)
    public Float y;
    @ModelField
    public Float z; //Поле не может быть null
}