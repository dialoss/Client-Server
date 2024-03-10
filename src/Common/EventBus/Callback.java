package Common.EventBus;

@FunctionalInterface
public interface Callback {
    void call(Object data);
}
