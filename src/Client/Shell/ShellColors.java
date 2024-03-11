package Client.Shell;

public enum ShellColors {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    BLUE("\033[0;34m"),
    GREEN("\u001B[32m");

    final String code;

    ShellColors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

    static public String format(ShellColors color, String data) {
        return color + data + ShellColors.RESET;
    }
}
