package Server.Models;

import java.util.Date;

public class Organization extends BaseModel {
    @ModelField(MIN = 0)
    private Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    @ModelField(NOT_EMPTY = true)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @ModelField(AUTO_GENERATE = true)
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @ModelField(NULL = true)
    private Coordinates coordinates; //Поле не может быть null
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null
}