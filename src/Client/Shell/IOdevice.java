package Client.Shell;

import Server.Commands.ICallback;

import java.util.Scanner;
import java.util.concurrent.Callable;

public abstract class IOdevice {
    protected Scanner scanner;

    protected IOdevice(Scanner scanner) {
        this.scanner = scanner;
    }

    public void updateScanner() {

    }

    abstract public void println(String data);

    abstract public void print(String data);

    public String inputLine() {
        return this.processInput(this.scanner::nextLine);
    }

    public String input() {
        this.print("> ");
        return this.processInput(this.scanner::nextLine);
    }

    public abstract void error(String message);

    private String processInput(Callable<?> method) {
        if (!this.scanner.hasNext()) return null;
        try {
            return (String) method.call();
        } catch (Exception e) {
        }
        return "";
    }

    public void start(ICallback<String[]> callback) {
        while (true) {
            String input = this.input();
            if (input == null) {
                break;
            }
            String[] tokens = input.split(" ");
            callback.call(tokens);
        }
    }
}
