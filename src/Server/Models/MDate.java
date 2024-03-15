package Server.Models;

import Common.Exceptions.InvalidModelException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MDate implements CustomField {
    private final Date date;
    static final String dateFormat = "dd.MM.yyyy HH:mm:ss.SSS";
    static final String EXCEPTION_MESSAGE = "Invalid date format. Format: " + dateFormat;
    static final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    public MDate() {
        date = new Date();
    }

    public MDate(Date date) {
        this.date = date;
    }

    public static MDate valueOf(String s) {
        try {
            Date d = formatter.parse(s);
            return new MDate(d);
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

    public static Class<?> getType() {
        return String.class;
    }

    public Object getValue() {
        return this.toString();
    }
}