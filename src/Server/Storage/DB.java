package Server.Storage;

import Server.Models.*;
import org.postgresql.util.PSQLException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//SELECT .. WHERE ..

class B {
    String from = "";
    MObject where = null;
    ArrayList<String> join = new ArrayList<>();

    public B from(String from) {
        this.from = from;
        return this;
    }

    public B where(MObject values) {
        this.where = values;
        return this;
    }

    public B join(String[] tables) {
        this.join = (ArrayList<String>) Arrays.asList(tables);
        return this;
    }

    public String get() throws Exception {
        if (from.length() == 0) throw new Exception("Invalid from");
        String query = "SELECT * FROM %s ".formatted(from);
        if (join.size() != 0) {
            String result = "";
            for (String t : join) {
                result += "JOIN %s USING(id) ".formatted(t);
            }
            query += result;
        }
        if (where != null) {
            ArrayList<String> queryFields = new ArrayList<>();
            for (Map.Entry<String, Object> entry : where.entrySet()) {
                queryFields.add("%s = %s".formatted(entry.getKey().toLowerCase(), entry.getValue()));
            }
            query += "WHERE " + DB.formatQuery(queryFields.toArray());
        }
        return query;
    }
}

public class DB {
    public static Connection connection;

    public static Map<String, String> TYPES = Map.of(
            "STRING", "TEXT",
            "FLOAT", "REAL",
            "DOUBLE", "DOUBLE PRECISION",
            "BOOLEAN", "BOOLEAN",
            "INTEGER", "INTEGER"
    );

    private static Map<Class<?>, DBModelFieldAdapter<?>> serializers = new HashMap<>();

    public static void registerModel(Class<?> t) throws Exception {
        dropTable(t);
        createTable(t);
    }

    public static void registerType(Class<?> t) throws Exception {
        dropType(t);
        createType(t);
    }

    private static MObject[] queryToObject(Class<?> t, String query) throws SQLException {
        PreparedStatement st = perform(query);
        ResultSet r = st.getResultSet();
        ArrayList<MObject> data = new ArrayList<>();
        while (r.next()) {
            MObject object = new MObject();
            int id = r.getInt("id");
            for (Field f : t.getDeclaredFields()) {
                if (BaseModel.isModel(f.getType())) {
                    MObject[] foreign = get(f.getType(), name(t) + "_id=" + id);
                    if (foreign.length != 0) object.put(f.getName(), foreign[0]);
                    else object.put(f.getName(), null);
                } else {
                    try {
                        Object d = r.getObject(f.getName());
                        try {
                            Method[] x = f.getType().getMethods();
                            f.setAccessible(true);
                            Method m = f.getType().getMethod("fromString", String.class);
                            object.put(f.getName(), m.invoke(d, d));
                        } catch (Exception e) {
                            object.put(f.getName(), d);
                        }
                    } catch (PSQLException e) {}
                }
            }
            data.add(object);
        }
        return data.toArray(MObject[]::new);
    }

    static {
        try {

            connection = getNewConnection();

            registerType(OrganizationType.class);
            registerModel(Users.class);
            registerModel(Organization.class);
            registerModel(Address.class);
            registerModel(Location.class);
            registerModel(Coordinates.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DB() throws Exception {
        insert(Organization.class, new Organization().from(new MObject(Map.of(
                "name", "test",
                "peopleCount", 100,
                "creationDate", new MDate(),
                "postalAddress", new MObject(Map.of(
                        "zipCode","qqq",
                        "town",new MObject(Map.of(
                                "x",1,"y",2,"z",3
                        )))),
                "annualTurnover", 2,
                "isPrivate", false,
                "type", OrganizationType.PUBLIC,
                "coordinates", new MObject(Map.of("x", 11, "y", 22)),
                "user", new MObject(Map.of(
                        "name","alex",
                        "password","123"
                ))
        ))));

        MObject q = getAll(Organization.class)[0];
        Organization t = (Organization) new Organization().from(q);
        return;
    }

    public static Object serialize() {
        return null;
    }

    public static void dropType(Class<?> t) throws Exception {
        perform("DROP TYPE IF EXISTS %s CASCADE;".formatted(t.getSimpleName().toUpperCase()));
    }

    public static void dropTable(Class<?> t) throws Exception {
        perform("DROP TABLE IF EXISTS %s CASCADE;".formatted(t.getSimpleName().toUpperCase()));
    }

    public static void createType(Class<?> t) throws Exception {
        String query = "CREATE TYPE %s AS ENUM (%s);";
        ArrayList<String> queryFields = new ArrayList<>();

        if (Enum.class.isAssignableFrom(t)) {
            Method method = t.getDeclaredMethod("values");
            Object[] obj = (Object[]) method.invoke(t);
            for (Object enumField : obj) {
                queryFields.add("'%s'".formatted(enumField.toString().toLowerCase()));
            }
        }
        perform(query.formatted(name(t), formatQuery(queryFields.toArray())));
    }

    private static String name(Class<?> t, boolean toUpper) {
        if (toUpper) return t.getSimpleName().toUpperCase();
        return t.getSimpleName().toLowerCase();
    }

    private static String name(Class<?> t) {
        return name(t, false);
    }

    public static void createTable(Class<?> t) throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS %s (%s);";
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
        perform(query.formatted(name(t), formatQuery(queryFields.toArray())));
    }

    public static int insert(Class<?> t, BaseModel values) throws Exception {
        String query = "INSERT INTO %s (%s) VALUES (%s);";
        ArrayList<Pair<Integer, Class<?>>> updateForeign = new ArrayList<>();
        ArrayList<String> insertFields = new ArrayList<>();
        ArrayList<Object> insertValues = new ArrayList<>();
        for (Field f : values.getClass().getDeclaredFields()) {
            Object value = f.get(values);
            if (name(value.getClass(), true).equals("CLASS")) {
                continue;
            }
            if (BaseModel.class.isAssignableFrom(value.getClass())) {
                int id = insert(value.getClass(), (BaseModel) value);
                updateForeign.add(new Pair<>(id, value.getClass()));
                continue;
            }
            insertFields.add(f.getName());
            Object v;
            try {
                Method m = value.getClass().getDeclaredMethod("getValue");
                v = m.invoke(value);
            } catch (Exception exception) {
                v = value;
            }
            if (String.class.isAssignableFrom(v.getClass())) v = "'%s'".formatted(v);
            if (Enum.class.isAssignableFrom(v.getClass())) v = "'%s'".formatted(v).toLowerCase();
            insertValues.add(v);
        }

        query = query.formatted(name(t), formatQuery(insertFields.toArray()), formatQuery(insertValues.toArray()));
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                Integer key = generatedKeys.getInt(1);
                for (Pair<Integer, Class<?>> p : updateForeign) {
                    update(p.b, new MObject(Map.of(name(t) + "_id", key)), p.a);
                }
                return key;
            }
        }
        return -1;
    }

    private static String _join(Class<?> t) {
        String join = "";
        for (Field f : t.getDeclaredFields()) {
            if (BaseModel.isModel(f.getType())) {
                join += "JOIN %s USING(id) ".formatted(name(f.getType()));
            }
        }
        return join;
    }

    public static MObject[] getAll(Class<?> t) throws Exception {
        return queryToObject(t, "SELECT * FROM %s".formatted(name(t)));
    }

    private static PreparedStatement perform(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        return statement;
    }

    public static void update(Class<?> t, MObject values, Integer id) throws SQLException {
        String query = "UPDATE %s SET %s WHERE id = " + id;
        ArrayList<String> updateValues = new ArrayList<>();
        for (Map.Entry<String, Object> e : values.entrySet()) {
            updateValues.add(e.getKey() + "=" + e.getValue());
        }
        perform(query.formatted(name(t), formatQuery(updateValues.toArray())));
    }

    public static MObject[] get(Class<?> t, Integer id) throws SQLException {
        return queryToObject(t, "SELECT * FROM %s WHERE id = %s".formatted(name(t), id));
    }

    public static MObject[] get(Class<?> t, String where) throws SQLException {
        return queryToObject(t, "SELECT * FROM %s WHERE %s".formatted(name(t), where));
    }

    public static String formatQuery(Object[] values) {
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
