package Common.Exceptions;

public class InvalidValue extends InvalidModelException {

    public InvalidValue(Object value, String field) {
        super(String.format("�������� %s �� ����� ���� ��������� ���� %s", value, field));
    }
}
