package Common.Connection;

public class Response extends ConnectionPackage {
    private Object body = "";
    private String message = "";
    public  Status code = Status.OK;

    public Response() {}

    public Response withMessage(String message) {
        this.message = message;
        return this;
    }

    public Response(String message, Status code) {
        this.message = message;
        this.code = code;
    }

    public Response(Object body, Status code) {
        this.body = body;
        this.code = code;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public Object getBody() {
        return this.body;
    }
}