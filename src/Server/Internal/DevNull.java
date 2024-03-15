package Server.Internal;

import Client.Shell.IOdevice;
import Client.Shell.ShellColors;

import java.util.Scanner;

public class DevNull extends IOdevice {
    public static Integer deviceCounter = 0;

    public DevNull(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void println(String data) {
        System.out.println(ShellColors.format(ShellColors.BLUE, data));
    }

    @Override
    public void print(String data) {
        System.out.print(ShellColors.format(ShellColors.BLUE, data));
    }

    @Override
    public void error(String message) {

    }
}