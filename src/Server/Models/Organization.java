package Server.Models;

import java.util.Date;

public class Organization extends BaseModel {
    @ModelField(MIN = 0)
    private Float annualTurnover; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0
    @ModelField(NOT_EMPTY = true)
    private String name; //���� �� ����� ���� null, ������ �� ����� ���� ������
    @ModelField(AUTO_GENERATE = true)
    private Date creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    @ModelField(NULL = true)
    private Coordinates coordinates; //���� �� ����� ���� null
    private OrganizationType type; //���� �� ����� ���� null
    private Address postalAddress; //���� �� ����� ���� null
}