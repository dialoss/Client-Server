package Server.Models;

@FunctionalInterface
interface IValidated {
    boolean validate(Object value, Object limit);
}

public enum FieldParameters {
    NULL((value, limit) -> !(value == null)),
    NOT_EMPTY((value, limit) -> ((String) value).length() > 0),
    MIN((value, limit) -> ((Number) value).doubleValue() > ((Number) limit).doubleValue()),
    MAX((value, limit) -> ((Number) value).doubleValue() < ((Number) limit).doubleValue()),
    UNIQUE((value, limit) -> ((Number) value).doubleValue() > ((Number) limit).doubleValue()),
    AUTO_GENERATE((value, limit) -> ((Number) value).doubleValue() > ((Number) limit).doubleValue());
//    MIN_LENGTH,
//    MAX_LENGTH;

    final IValidated validator;

    FieldParameters(IValidated validator) {
        this.validator = validator;
    }
}
