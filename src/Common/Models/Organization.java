package Common.Models;

import Server.Data.CustomFields.MBoolean;
import Server.Data.CustomFields.MDate;

public class Organization extends BaseModel implements Comparable<Organization> {
    @ModelField(MIN = 100, MAX = 500)
    public Integer peopleCount;
    @ModelField
    public OrganizationType type; //Field cannot be null
    @ModelField(MIN = 0)
    public Float annualTurnover; //The field cannot be null, the field value must be greater than 0
    @ModelField()
    public String name; //Field cannot be null, String cannot be empty
    @ModelField(AUTO_GENERATE = true)
    public MDate creationDate; //The field cannot be null, the value of this field must be generated automatically
    @ModelField(NULL = true)
    public Coordinates coordinates; //Field cannot be null
    @ModelField
    public Address postalAddress; //Field cannot be null
    @ModelField
    public MBoolean isPrivate;

    @ForeignKey
    public Integer useraccount_id;
    @Override
    public int compareTo(Organization o) {
        return this.id.compareTo(o.id);
    }
}