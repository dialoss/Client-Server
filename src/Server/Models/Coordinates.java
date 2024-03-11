package Server.Models;

public class Coordinates extends BaseModel {
    @ModelField
    public Double x; //Поле не может быть null
    @ModelField
    public Double y; //Поле не может быть null
}