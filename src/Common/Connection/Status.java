package Common.Connection;

public enum Status {
    OK(200),
    FORBIDDEN(400),
    SERVER_ERROR(500);

    public final int code;

    Status(int code) {
        this.code = code;
    }
}