package Common.Exceptions;

public class ScriptRuntimeException extends RuntimeException {
    public ScriptRuntimeException() {
        super("Ошибка выполнения скрипта");
    }
}
