import Client.Client;

public class Main {
    public static void main(String[] args) {
// Runnable serverTask = new Runnable() {
// @Override
// public void run() {
// Server server = new Server();
// }
// };
// Thread serverThread = new Thread(serverTask);
// serverThread.start();
// try {
// Thread.sleep(200);
// } catch (InterruptedException e) {
// throw new RuntimeException(e);
// }
        Client client = new Client();
        client.run();
    }
}