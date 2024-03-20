package Client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
