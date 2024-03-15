import Server.Models.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

class DB {
    public static Connection connection;

    public static Map<String, String> TYPES = Map.of(
            "STRING", "TEXT",
            "FLOAT", "REAL",
            "DOUBLE", "DOUBLE PRECISION",
            "BOOLEAN", "BOOLEAN",
            "INTEGER", "INTEGER"
    );

    public static void registerModel(Class<?> t) throws Exception {
        connection.prepareStatement(dropTable(t)).execute();
        connection.prepareStatement(createTable(t)).execute();
    }

    public static void registerType(Class<?> t) throws Exception {
        connection.prepareStatement(dropType(t)).execute();
        connection.prepareStatement(createType(t)).execute();
    }

    static {
        try {
            connection = getNewConnection();

            registerType(OrganizationType.class);
            registerModel(Organization.class);
            registerModel(Address.class);
            registerModel(Location.class);
            registerModel(Coordinates.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    DB() throws Exception {
        connection.prepareStatement(insert(Organization.class, Map.of(
                "name","test",
                "peoplecount", 100,
                "creationdate", new MDate()
        ))).execute();

        PreparedStatement st = connection.prepareStatement("SELECT * FROM organization");
        st.execute();
        ResultSet r = st.getResultSet();
        while (r.next()) {
            System.out.println(r.getObject("name"));
        }
    }

    public static String dropType(Class<?> t) throws Exception {
        return "DROP TYPE IF EXISTS %s CASCADE;".formatted(t.getSimpleName().toUpperCase());
    }

    public static String dropTable(Class<?> t) throws Exception {
        return "DROP TABLE IF EXISTS %s CASCADE;".formatted(t.getSimpleName().toUpperCase());
    }

    public static String createType(Class<?> t) throws Exception {
        String query = "CREATE TYPE %s AS ENUM (%s);";
        ArrayList<String> queryFields = new ArrayList<>();

        if (Enum.class.isAssignableFrom(t)) {
            Method method = t.getDeclaredMethod("values");
            Object[] obj = (Object[]) method.invoke(t);
            for (Object enumField : obj) {
                queryFields.add("'%s'".formatted(enumField.toString().toLowerCase()));
            }
        }
        return query.formatted(name(t), formatQuery(queryFields.toArray()));
    }

    private static String name(Class<?> t, boolean toUpper) {
        if (toUpper) return t.getSimpleName().toUpperCase();
        return t.getSimpleName().toLowerCase();
    }

    private static String name(Class<?> t) {
        return name(t, false);
    }

    public static String createTable(Class<?> t) throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS %s (%s);";
        String tableName = name(t);
        Field[] fields = t.getDeclaredFields();
        ArrayList<String> queryFields = new ArrayList<>();
        queryFields.add("id SERIAL PRIMARY KEY");
        for (Field f : fields) {
            String type = name(f.getType(), true);
            String dbType = TYPES.get(type);
            if (type.equals("CLASS")) {
                Class<?> x = (Class<?>) f.get(null);
                String parent = name(x);
                queryFields.add("%s INTEGER REFERENCES %s".formatted(parent + "_id", parent));
                continue;
            } else if (CustomField.class.isAssignableFrom(f.getType())) {
                Method method = f.getType().getDeclaredMethod("getType");
                Class<?> cl = (Class<?>) method.invoke(null);
                dbType = TYPES.get(name(cl, true));
            } else if (dbType == null) {
                if (BaseModel.isModel(f.getType())) continue;
                dbType = type;
            }
            queryFields.add("%s %s".formatted(f.getName(), dbType.toUpperCase()));
        }
        query = query.formatted(tableName, formatQuery(queryFields.toArray()));
        System.out.println(query);
        return query;
    }

    public static String insert(Class<?> t, Map<String, Object> values) {
        String query = "INSERT INTO %s (%s) VALUES (%s);";
        Object[] insertFields = values.keySet().toArray();
        Object[] insertValues = Arrays.stream(values.values().toArray()).map(value -> {
                    Object v;
                    try {
                        Method m = value.getClass().getDeclaredMethod("getValue");
                        v = m.invoke(value);
                    } catch (Exception e) {
                        v = value;
                    }
                    if (String.class.isAssignableFrom(v.getClass())) v = "'%s'".formatted(v);
                    return v;
                }).toArray();
        query = query.formatted(name(t), formatQuery(insertFields), formatQuery(insertValues));
        System.out.println(query);
        return query;
    }

    public static Map<String, String> get(Class<?> t) {

    }

    private static String formatQuery(Object[] values) {
        String result = "";
        for (Object v : values) result += v.toString() + ",";
        return result.substring(0, result.length() - 1);
    }

    private static Connection getNewConnection() throws SQLException {
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