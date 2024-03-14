package Server.Models;

public class Address extends BaseModel {
    @ModelField(NULL = true, MAX_LENGTH = 50)
    public String zipCode; //����� ������ �� ������ ���� ������ 15, ���� ����� ���� null
    @ModelField
    public Location town; //���� �� ����� ���� null
}
