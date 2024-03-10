package Server.Models;

public class Organization extends BaseModel {
    @ModelField(MIN = 0, UNIQUE = true, AUTO_GENERATE = true)
    private Integer id; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0, �������� ����� ���� ������ ���� ����������, �������� ����� ���� ������ �������������� �������������
    @ModelField(NOT_EMPTY = true)
    private String name; //���� �� ����� ���� null, ������ �� ����� ���� ������
    @ModelField(NULL = true)
    private Coordinates coordinates; //���� �� ����� ���� null
    @ModelField(AUTO_GENERATE = true)
    private java.time.LocalDateTime creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    @ModelField(MIN = 0)
    private Integer annualTurnover; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0
    private String fullName; //���� �� ����� ���� null
    private OrganizationType type; //���� �� ����� ���� null
    private Address postalAddress; //���� �� ����� ���� null
}