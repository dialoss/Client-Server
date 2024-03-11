package Client;

import Server.Commands.ICallback;

import java.util.Scanner;

enum ShellColors {
    ANSI_RESET("\u001B[0m"),
    ANSI_RED("\u001B[31m");

    final String code;

    ShellColors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}

public class Shell {
    public void start(ICallback<String> callback) {
        while (true) {
            String input = this.input();
            String[] tokens = input.split(" ");
            callback.call(tokens[0]);
        }
    }

    public void print(String data) {
        System.out.println(data);
    }

    public String input() {
        return new Scanner(System.in).nextLine();
    }

    public void error(String data) {
        System.out.println(ShellColors.ANSI_RED + data + ShellColors.ANSI_RESET);
    }
}
