package Server.Models;

public class Location extends BaseModel {
    @ModelField(NULL = true)
    private Double x;
    @ModelField(NULL = true)
    private Float y;
    private Float z; //Поле не может быть null
}