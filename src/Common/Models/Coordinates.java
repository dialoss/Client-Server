package Common.Models;

public class Coordinates extends BaseModel {
    @ModelField
    public Double x; //Field cannot be null
    @ModelField
    public Double y; //Field cannot be null
    @ForeignKey
    public Organization organization;
}