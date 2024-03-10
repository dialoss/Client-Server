package Server.Models;

public class Organization extends BaseModel {
    @ModelField(MIN = 0, UNIQUE = true, AUTO_GENERATE = true)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @ModelField(NOT_EMPTY = true)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @ModelField(NULL = true)
    private Coordinates coordinates; //Поле не может быть null
    @ModelField(AUTO_GENERATE = true)
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @ModelField(MIN = 0)
    private Integer annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private String fullName; //Поле не может быть null
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null
}