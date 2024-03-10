package Server.Models;


public class ObjectBuilder<K extends IObjectBuilder> {
    private final K field;

    public ObjectBuilder(K field) {
        this.field = field;
    }

    public ObjectBuilder<K> set(Object parameter, Object value) {
        this.field.addParameter(parameter, value);
        return this;
    }

    public K get() {
        return this.field;
    }
}
