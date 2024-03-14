package Server.Models;

public class Organization extends BaseModel implements Comparable<Organization>, OrderedItem {
    @ModelField(MIN = 2, UNIQUE = true, AUTO_GENERATE = true)
    public Integer id;

    @ModelField(MIN=100, MAX=500)
    public Integer peopleCount;
    @ModelField
    public OrganizationType type; //Поле не может быть null
    @ModelField(MIN = 0)
    public Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    @ModelField(NOT_EMPTY = true, UNIQUE = true)
    public String name; //Поле не может быть null, Строка не может быть пустой
    @ModelField(AUTO_GENERATE = true)
    public MyDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @ModelField(NULL = true)
    public Coordinates coordinates; //Поле не может быть null
    @ModelField
    public Address postalAddress; //Поле не может быть null
    @ModelField
    public Boolean isPrivate;

    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.name);
    }

    public Integer getId() {
        return this.id;
    }
}