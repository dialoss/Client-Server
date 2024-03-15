package Server.Storage;

import Server.Models.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DB {
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

//            registerType(OrganizationType.class);
//            registerModel(Organization.class);
//            registerModel(Address.class);
//            registerModel(Location.class);
//            registerModel(Coordinates.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DB() throws Exception {
        insert(Organization.class, new MObject(Map.of(
                "name", "test",
                "peoplecount", 100,
                "creationdate", new MDate(),
                "postaladdress", new Coordinates().from(new MObject(Map.of("x", 11, "y", 22)))
        )));
        MObject t = get(Organization.class);
        return;
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

    public static int insert(Class<?> t, MObject values) throws Exception {
        String query = "INSERT INTO %s (%s) VALUES (%s);";
        Map<Integer, Class<?>> updateForeign = new HashMap<>();
        ArrayList<String> insertFields = new ArrayList<>();
        ArrayList<Object> insertValues = new ArrayList<>();
        for (Map.Entry<String, Object> e : values.entrySet()) {
            Object value = e.getValue();
            if (BaseModel.isModel(value.getClass())) {
                int key = insert(value.getClass(), (MObject) value);
                updateForeign.put(key, value.getClass());
                continue;
            }
            insertFields.add(e.getKey());
            Object v;
            try {
                Method m = value.getClass().getDeclaredMethod("getValue");
                v = m.invoke(value);
            } catch (Exception exception) {
                v = value;
            }
            if (String.class.isAssignableFrom(v.getClass())) v = "'%s'".formatted(v);
            insertValues.add(v);
        }

        query = query.formatted(name(t), formatQuery(insertFields.toArray()), formatQuery(insertValues.toArray()));
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                for (Map.Entry<Integer, Class<?>> e : updateForeign.entrySet()) {

                }
                return generatedKeys.getInt(1);
            }
        }
    }

    public static MObject get(Class<?> t) throws Exception {
        String join = "";
        for (Field f : t.getDeclaredFields()) {
            if (BaseModel.isModel(f.getType())) {
                join += "JOIN %s USING(id) ".formatted(name(f.getType()));
            }
        }
        PreparedStatement st = connection.prepareStatement("SELECT * FROM %s %s".formatted(name(t), join));
        st.execute();
        ResultSet r = st.getResultSet();
        MObject data = new MObject();
        while (r.next()) {
            for (Field f : t.getDeclaredFields()) {
                if (BaseModel.isModel(f.getType())) data.put(f.getName(), get(f.getType()));
                else {
                    try {
                        data.put(f.getName(), r.getObject(f.getName()));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return data;
    }

    public static String update(Class<?> t, MObject o) {
        return connection.prepareStatement("SELECT * FROM %s WHERE id = ".formatted(name(t), join))
    }

    public static String get(Class<?> t, Integer id) {
        return "SELECT * FROM %s WHERE id = %s".formatted(name(t), id);
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
