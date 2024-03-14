package Server.Models;

import Common.Exceptions.InvalidModelException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    private final Date date;
    static final String dateFormat = "dd.MM.yyyy HH:mm:ss.SSS";
    static final String EXCEPTION_MESSAGE = "Неверный формат даты. Формат: " + dateFormat;
    static final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    public MyDate() {
        date = new Date();
    }

    public static Date valueOf(String s) {
        try {
            return formatter.parse(s);
        } catch (Exception e) {
            throw new InvalidModelException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public String toString() {
        try {
            return formatter.format(date);
        } catch (Exception e) {
            throw new InvalidModelException(EXCEPTION_MESSAGE);
        }
    }
}
