package Server.Models;

public class Address {
    @ModelField(NULL = true, MAX_LENGTH = 16)
    private String zipCode; //����� ������ �� ������ ���� ������ 15, ���� ����� ���� null
    private Location town; //���� �� ����� ���� null
}
