package Common;

import java.util.function.Function;

public class Stringify {
    private boolean numerate = true;
    private boolean splitLines;
    private final Object[] items;
    private Function<Object, String> modificator = Object::toString;

    public Stringify(Object[] items) {
        this.items = items;
    }

    public Stringify withNotNumerate() {
        this.numerate = false;
        return this;
    }

    public Stringify withSplitLines() {
        this.splitLines = true;
        return this;
    }

    public Stringify withModificator(Function<Object, String> modificator) {
        this.modificator = modificator;
        return this;
    }

    public String get() {
        String result = "";
        int i = 1;
        for (Object it : items) {
            result = result.concat((numerate ? i++ + " " : "") + modificator.apply(it) + "\n");
            if (splitLines) result += "\n";
        }
        if (result.equals("")) return "No elements";
        return result;
    }
}