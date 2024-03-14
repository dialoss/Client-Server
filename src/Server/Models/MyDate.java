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

    public MyDate(Date date) {
        this.date = date;
    }

    public static MyDate valueOf(String s) {
        try {
            Date d = formatter.parse(s);
            return new MyDate(d);
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
