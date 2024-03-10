package Server.Models;

public class Location {
    @ModelField(NULL = true)
    private double x;
    @ModelField(NULL = true)
    private float y;
    private Float z; //Поле не может быть null
}