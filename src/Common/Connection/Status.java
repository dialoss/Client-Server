package Common.Connection;

public enum Status {
    OK(200),
    SERVER_ERROR(500);

    public final int code;

    Status(int code) {
        this.code = code;
    }
}