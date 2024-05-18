package Server.Storage;

import Common.Files.JSONStorage;
import Common.Models.MObject;
import Common.Models.Organization;
import Common.Tools;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.Database.DBManager;

import java.util.Scanner;

public class StorageConnector {
    public static JSONStorage fileStorage;
    public static DBManager dbManager;
    public static CollectionManager manager;

    public static void init() {
        dbManager = new DBManager();
        fileStorage = new JSONStorage("src/Common/data/");
        manager = new CollectionManager();

        String file = "session_data.json";
        if (fileStorage.checkExistance(file)) {
            System.out.println("Do you want to restore collection?");
            String ans = null;
            Scanner scanner = new Scanner(System.in);
            while (ans == null) {
                ans = scanner.next();
                if (ans.equals("yes")) {
                    fileStorage.changeSource(file);
                    loadCollection(fileStorage.read());
                    break;
                }
                if (ans.equals("no")) break;
                ans = null;
            }
            fileStorage.remove(file);
        }
    }

    public static void loadFile() {
        loadFile("data.json");
    }

    public static void loadFile(String filename) {
        fileStorage.changeSource(filename);
        loadCollection(fileStorage.read());
    }

    public static void loadDB() throws Exception {
        loadCollection(dbManager.load());
    }

    public static void loadCollection(MObject[] items) {
        int counter = 0;
        for (MObject o : items) {
            System.out.println("Start");
            long m = System.currentTimeMillis();
            Organization item = (Organization) new Organization().from(o);
            System.out.println("End " + (System.currentTimeMillis() - m));
            manager.insert(item);
            System.out.println(counter++);
        }
    }

    public static String saveFile(String filename) {
        return fileStorage.write(Tools.stringify(manager.getAll()), filename);
    }

    public static void saveDB() throws Exception {
        dbManager.save(manager.getAll());
    }
}