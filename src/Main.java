import Server.Models.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

class DB {
    public static Connection connection;

    public static Map<String, String> TYPES = Map.of(
            "STRING", "TEXT",
            "FLOAT", "REAL",
            "DOUBLE", "DOUBLE PRECISION"
    );

    public static ArrayList<Class<?>> types = new ArrayList<>();

    public static void registerModel(Class<?> t) {
        registerModel(t, false);
    }

    public static void registerModel(Class<?> t, boolean isType) {
        types.add(t);
    }

    static {
//        registerModel(OrganizationType.class, true);
        registerModel(Organization.class);
        registerModel(Address.class);
        registerModel(Location.class);
        registerModel(Coordinates.class);
    }

    DB() throws Exception {
        connection = getNewConnection();

        for (Class<?> t : types) {
            String query = this.createTable(t);
            connection.prepareStatement(query).execute();
        }

        PreparedStatement st = connection.prepareStatement("SELECT * FROM organization");
        st.execute();


        ResultSet r = st.getResultSet();
        while (r.next()) {
            System.out.println(r.getObject("name"));
        }
    }

    public String createType(Class<?> t) throws Exception {
        String query = "CREATE TYPE %s AS ENUM (%s);";
        String result = "";
        if (Enum.class.isAssignableFrom(t)) {
            Method method = t.getDeclaredMethod("values");
            Object[] obj = (Object[]) method.invoke(t);
            for (Object enumField : obj) {
                result += enumField.toString().toLowerCase() + ",";
            }
        }
        return query.formatted(t.getSimpleName().toUpperCase(), result);
    }

    public String createTable(Class<?> t) throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS %s (%s);";
        String tableName = t.getSimpleName().toLowerCase();
        Field[] fields = t.getDeclaredFields();
        String queryFields = "";
        for (Field f : fields) {
            String type = f.getType().getSimpleName();
            String dbType = TYPES.get(type);
            if (type.equals("CLASS")) {
                Class<?> x = (Class<?>) f.get(null);
                String parent = x.getSimpleName().toLowerCase();
                queryFields += "%s INTEGER REFERENCES %s, ".formatted(parent + "_id", parent);
                continue;
            }
            else if (CustomField.class.isAssignableFrom(f.getType())) {
                Method method = f.getType().getDeclaredMethod("getType");
                Class<?> cl = (Class<?>) method.invoke(null);
                dbType = cl.getSimpleName();
            }
            else if (dbType == null) {
                if (BaseModel.isModel(f.getType())) {
                    continue;
                }
                dbType = type;
            }
            queryFields += "%s %s, ".formatted(f.getName(), dbType.toUpperCase());
        }
        return query.formatted(tableName, queryFields);
    }

    public PreparedStatement insert(Class<?> t, Map<String, Object> values) {
        return null;
    }

    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql:1";
        String user = "1";
        String passwd = "1";
        return DriverManager.getConnection(url, user, passwd);
    }
}

public class Main {
    public static void main(String[] args) {
//        Runnable serverTask = new Runnable() {
//            @Override
//            public void run() {
//                Server server = new Server();
//            }
//        };
//        Thread serverThread = new Thread(serverTask);
//        serverThread.start();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Client client = new Client();
//        client.run();


        try {
            DB db = new DB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}