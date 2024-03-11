package Server.Models;

import java.util.Date;

public class Organization extends BaseModel implements Comparable<Organization> {
    @ModelField
    public OrganizationType type; //���� �� ����� ���� null
    @ModelField(MIN = 0)
    public Float annualTurnover; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0
    @ModelField(NOT_EMPTY = true)
    public String name; //���� �� ����� ���� null, ������ �� ����� ���� ������
    @ModelField(AUTO_GENERATE = true)
    public Date creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    @ModelField(NULL = true)
    public Coordinates coordinates; //���� �� ����� ���� null
    @ModelField
    public Address postalAddress; //���� �� ����� ���� null


    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.name);
    }
}