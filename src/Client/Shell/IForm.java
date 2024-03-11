package Client.Shell;

public abstract class IForm {
    public IOdevice device;

    public IForm(IOdevice device) {
        this.device = device;
    }

    protected abstract void validationError(String message);

    protected abstract String input();

    protected abstract void out(String data);
}
