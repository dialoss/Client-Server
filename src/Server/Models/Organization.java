package Server.Models;

public class Organization extends BaseModel implements Comparable<Organization> {
    @ModelField(MIN = 100, MAX = 500)
    public Integer peopleCount;
    @ModelField
    public OrganizationType type; //Field cannot be null
    @ModelField(MIN = 0)
    public Float annualTurnover; //The field cannot be null, the field value must be greater than 0
    @ModelField(NOT_EMPTY = true, UNIQUE = true)
    public String name; //Field cannot be null, String cannot be empty
    @ModelField(AUTO_GENERATE = true)
    public MDate creationDate; //The field cannot be null, the value of this field must be generated automatically
    @ModelField(NULL = true)
    public Coordinates coordinates; //Field cannot be null
    @ModelField
    public Address postalAddress; //Field cannot be null
    @ModelField
    public MBoolean isPrivate;

    public static Class<?> user = UserAccount.class;
    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.name);
    }
}