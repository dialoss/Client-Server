package Client.Shell;

import java.util.ArrayDeque;

public abstract class IForm {
    public IOdevice device;
    protected ArrayDeque<String> simulatedInput;

    public IForm(IOdevice device) {
        this.device = device;
        this.simulatedInput = new ArrayDeque<>();
    }

    public void putInput(String data) {
        this.simulatedInput.add(data);
    }

    public void clearInput() {
        this.simulatedInput.clear();
    }

    protected abstract void validationError(String message);

    protected String input() {
        if (!this.simulatedInput.isEmpty()) return this.simulatedInput.pollFirst();
        return this.device.input();
    }

    public abstract void out(String data);
}