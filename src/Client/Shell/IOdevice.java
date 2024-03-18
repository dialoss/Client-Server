package Client.Shell;

import Common.Callback;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Callable;

public abstract class IOdevice {
    protected Scanner scanner;

    protected IOdevice(Scanner scanner) {
        this.scanner = scanner;
    }

    abstract public void println(String data);

    abstract public void print(String data);

    public String input() {
        String data = this.processInput(this.scanner::nextLine);
        if (Objects.equals(data, "")) return "";
        return data;
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

    public void start(Callback<String[]> callback) {
        this.print("> ");
        while (true) {
            String input = this.input();
            if (input == null) {
                break;
            }
            if (input.equals("")) continue;
            String[] tokens = input.split(" ");
            callback.call(tokens);
            this.print("> ");
        }
    }
}