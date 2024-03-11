package Client.Shell;

import Common.FileStorage;
import Server.Commands.ICallback;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class Shell {
    public ShellMode mode = ShellMode.INTERACTIVE;
    Scanner scanner;
    FileStorage storage;

    public Shell() {
        this.scanner = new Scanner(System.in);
    }

    public void setScript(String filename) {
        this.mode = ShellMode.SCRIPT;
        this.storage = new FileStorage("src/Client/scripts/" + filename);
        this.scanner = new Scanner(this.storage._read());
    }

    public void stopScript() {
        this.success("Скрипт исполнен!");
        this.storage._close();
        this.scanner = new Scanner(System.in);
        this.mode = ShellMode.INTERACTIVE;
    }

    public void start(ICallback<String> callback) {
        while (true) {
            String input = this.input();
            if (input == null) {
                this.stopScript();
                input = "";
            }
            String[] tokens = input.split(" ");
            callback.call(tokens[0]);
        }
    }

    public void print(String data) {
        if (this.mode != ShellMode.SCRIPT) System.out.println(data);
        else this.storage._add(data.replaceAll("\u001B\\[[\\d;]*[^\\d;]",""));
    }

    private String shellInput(Callable<?> method) {
        if (!this.scanner.hasNext()) return null;
        try {
            String value = (String) method.call();
            if (this.mode == ShellMode.SCRIPT) this.print("-> " + value);
            return value;
        } catch (Exception e) {}
        return "";
    }

    public String inputLine() {
        return this.shellInput(this.scanner::nextLine);
    }

    public String input() {
        return this.shellInput(this.scanner::next);
    }

    public void error(String data) {
        System.out.println(ShellColors.RED + data + ShellColors.RESET);
    }
    public void success(String data) {
        System.out.println(ShellColors.GREEN + data + ShellColors.RESET);
    }
}
