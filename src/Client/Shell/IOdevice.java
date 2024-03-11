package Client.Shell;

import java.util.Scanner;
import java.util.concurrent.Callable;

public abstract class IOdevice {
    protected Scanner scanner;

    protected IOdevice(Scanner scanner) {
        this.scanner = scanner;
    }

    abstract public void println(String data);

    abstract public void print(String data);

    public String inputLine() {
        return this.processInput(this.scanner::nextLine);
    }

    public String input() {
        this.print("> ");
        return this.processInput(this.scanner::next);
    }

    protected abstract void error(String message);

    private String processInput(Callable<?> method) {
        if (!this.scanner.hasNext()) return null;
        try {
            return (String) method.call();
        } catch (Exception e) {
        }
        return "";
    }
}
