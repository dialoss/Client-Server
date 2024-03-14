package Client.Shell;

public class ShellForm extends IForm {
    public ShellForm(IOdevice shell) {
        super(shell);
    }

    @Override
    public void validationError(String message) {
        this.device.error(message);
    }
    @Override
    public void out(String data) {
        this.device.println(data);
    }
}