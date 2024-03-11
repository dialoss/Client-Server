package Client.Shell;

import Common.FileStorage;
import Server.Commands.ICallback;

import java.util.Scanner;

enum ShellMode {
    INTERACTIVE,
    SCRIPT,
}

public class Shell {
    ShellMode mode = ShellMode.INTERACTIVE;
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
    }

    public void start(ICallback<String> callback) {
        while (true) {
            String input = this.input();
            if (input == null) {
                this.stopScript();
                return;
            }
            String[] tokens = input.split(" ");
            callback.call(tokens[0]);
        }
    }

    public void print(String data) {
        if (this.mode != ShellMode.SCRIPT) System.out.println(data);
        else this.storage._add(data.replaceAll("\u001B\\[[\\d;]*[^\\d;]",""));
    }

    public String input() {
        if (!this.scanner.hasNext()) return null;
        String value = this.scanner.nextLine();
        if (this.mode == ShellMode.SCRIPT) this.print("-> " + value);
        return value;
    }

    public void error(String data) {
        System.out.println(ShellColors.RED + data + ShellColors.RESET);
    }
    public void success(String data) {
        System.out.println(ShellColors.GREEN + data + ShellColors.RESET);
    }
}
