package Server.Models;

public class Address extends BaseModel {
    @ModelField(NULL = true, MAX_LENGTH = 50)
    public String zipCode; //Длина строки не должна быть больше 15, Поле может быть null
    @ModelField
    public Location town; //Поле не может быть null
}
