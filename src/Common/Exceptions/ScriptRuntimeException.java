package Common.Exceptions;

public class ScriptRuntimeException extends RuntimeException {
    public ScriptRuntimeException(String e) {
        super("Ошибка выполнения скрипта " + e);
    }
}
