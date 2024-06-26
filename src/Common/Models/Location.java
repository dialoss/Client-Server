package Common.Models;

public class Location extends BaseModel {
    @ModelField(NULL = true)
    public Double x;
    @ModelField(NULL = true)
    public Float y;
    @ModelField
    public Float z; //Field cannot be null
    @ForeignKey
    public Address address;
}