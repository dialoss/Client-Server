package Client.Shell;

import Server.Commands.ICallback;
import java.util.Scanner;

public class Shell extends IOdevice {
    public Shell() {
        super(new Scanner(System.in));
    }

    public void start(ICallback<String> callback) {
        while (true) {
            String input = this.input();
            String[] tokens = input.split(" ");
            callback.call(tokens[0]);
        }
    }

    public void print(String data) {
        System.out.print(data);
    }

    public void println(String data) {
        this.println(data, ShellColors.RESET);
    }

    public void println(String data, ShellColors color) {
        System.out.println(ShellColors.format(color, data));
    }

    public void error(String data) {
        this.println(data, ShellColors.RED);
    }
    public void success(String data) {
        this.println(data, ShellColors.GREEN);
    }
}
