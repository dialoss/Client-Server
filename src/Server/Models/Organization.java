package Server.Models;

public class Organization extends BaseModel implements Comparable<Organization>, OrderedItem {
    @ModelField(MIN = 2, UNIQUE = true, AUTO_GENERATE = true)
    public Integer id;

    @ModelField
    public OrganizationType type; //���� �� ����� ���� null
    @ModelField(MIN = 0)
    public Float annualTurnover; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0
    @ModelField(NOT_EMPTY = true, UNIQUE = true)
    public String name; //���� �� ����� ���� null, ������ �� ����� ���� ������
    @ModelField(AUTO_GENERATE = true)
    public MyDate creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    @ModelField(NULL = true)
    public Coordinates coordinates; //���� �� ����� ���� null
    @ModelField
    public Address postalAddress; //���� �� ����� ���� null

    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.name);
    }

    public Integer getId() {
        return this.id;
    }
}