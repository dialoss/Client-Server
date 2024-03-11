package Server.Internal;

import Client.Shell.IOdevice;

import java.util.Scanner;

public class DevNull extends IOdevice {
    public DevNull(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void println(String data) {
    }

    @Override
    public void print(String data) {
    }

    @Override
    public void error(String message) {

    }
}
