package Client.Shell;

public class ShellForm extends IForm {
    public ShellForm(Shell shell) {
        super(shell);
    }

    @Override
    public void validationError(String message) {
        this.device.error(message);
    }

    @Override
    public String input() {
        return this.device.input();
    }

    @Override
    public void out(String data) {
        this.device.println(data);
    }
}
