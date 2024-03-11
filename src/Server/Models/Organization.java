package Server.Models;

import java.util.Date;

public class Organization extends BaseModel implements Comparable<Organization> {
    @ModelField
    public OrganizationType type; //Поле не может быть null
    @ModelField(MIN = 0)
    public Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    @ModelField(NOT_EMPTY = true)
    public String name; //Поле не может быть null, Строка не может быть пустой
    @ModelField(AUTO_GENERATE = true)
    public Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @ModelField(NULL = true)
    public Coordinates coordinates; //Поле не может быть null
    @ModelField
    public Address postalAddress; //Поле не может быть null


    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.name);
    }
}