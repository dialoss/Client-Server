package Server.Storage.Database;

import Common.Pair;
import Server.Data.CustomFields.CustomField;
import Common.Models.*;
import org.postgresql.util.PSQLException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class DBOperations {
    public static Map<String, String> TYPES = Map.of(
            "STRING", "TEXT",
            "FLOAT", "REAL",
            "DOUBLE", "DOUBLE PRECISION",
            "BOOLEAN", "BOOLEAN",
            "INTEGER", "INTEGER"
    );
    public Connection connection;

    public void registerModel(Class<?> t) throws Exception {
        dropTable(t);
        createTable(t);
    }

    public void registerType(Class<?> t) throws Exception {
        dropType(t);
        createType(t);
    }

    private MObject[] queryToObject(Class<?> t, String query) throws SQLException {
        PreparedStatement st = perform(query);
        System.out.println("Got rows from database");
        ResultSet r = st.getResultSet();
        ArrayList<MObject> data = new ArrayList<>();
        while (r.next()) {
            MObject object = new MObject();
            int id = r.getInt("id");
            object.put("id", id);
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
                    } catch (PSQLException e) {
                        System.out.println(e);
                    }
                }
            }
            data.add(object);
        }
        return data.toArray(MObject[]::new);
    }

    private void initModels() throws Exception {
        registerType(OrganizationType.class);
        registerModel(UserAccount.class);
        registerModel(Organization.class);
        registerModel(Address.class);
        registerModel(Location.class);
        registerModel(Coordinates.class);
    }

    DBOperations() {
        try {
            connection = getNewConnection();
//            initModels();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dropType(Class<?> t) throws Exception {
        perform("DROP TYPE IF EXISTS %s CASCADE;".formatted(t.getSimpleName().toUpperCase()));
    }

    public void dropTable(Class<?> t) throws Exception {
        perform("DROP TABLE IF EXISTS %s CASCADE;".formatted(t.getSimpleName().toUpperCase()));
    }

    public void createType(Class<?> t) throws Exception {
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

    private String name(Class<?> t, boolean toUpper) {
        if (toUpper) return t.getSimpleName().toUpperCase();
        return t.getSimpleName().toLowerCase();
    }

    private String name(Class<?> t) {
        return name(t, false);
    }

    public void createTable(Class<?> t) throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS %s (%s);";
        Field[] fields = t.getDeclaredFields();
        ArrayList<String> queryFields = new ArrayList<>();
        queryFields.add("id SERIAL PRIMARY KEY");
        for (Field f : fields) {
            String type = name(f.getType(), true);
            String dbType = TYPES.get(type);
            if (f.isAnnotationPresent(ForeignKey.class)) {
                String parent = name(f.getType());
                queryFields.add("%s INTEGER REFERENCES %s ON DELETE CASCADE".formatted(parent + "_id", parent));
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

    public int insert(Class<?> t, BaseModel values) throws Exception {
        String query = "INSERT INTO %s (%s) VALUES (%s);";
        ArrayList<Pair<Integer, Class<?>>> updateForeign = new ArrayList<>();
        ArrayList<String> insertFields = new ArrayList<>();
        ArrayList<Object> insertValues = new ArrayList<>();
        for (Field f : values.getClass().getDeclaredFields()) {
            Object value = f.get(values);
            if (f.isAnnotationPresent(ForeignKey.class)) {
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
        PreparedStatement statement = perform(query);
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

    public MObject[] getAll(Class<?> t) throws Exception {
        // JOIN useraccount USING(id)
        return queryToObject(t, "SELECT * FROM %s".formatted(name(t)));
    }

    public MObject[] getAll() throws SQLException {
        return queryToObject(Organization.class, "SELECT * FROM organization JOIN address ON (organization.id = address.organization_id) " +
                "JOIN coordinates ON organization.id = coordinates.organization_id " +
                "JOIN location ON address.id = location.address_id;");
    }

    private PreparedStatement perform(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        return statement;
    }

    public void update(Class<?> t, MObject values, Integer id) throws SQLException {
        String query = "UPDATE %s SET %s WHERE id = " + id;
        ArrayList<String> updateValues = new ArrayList<>();
        for (Map.Entry<String, Object> e : values.entrySet()) {
            updateValues.add(e.getKey() + "=" + e.getValue());
        }
        perform(query.formatted(name(t), formatQuery(updateValues.toArray())));
    }

    public MObject[] get(Class<?> t, Integer id) throws SQLException {
        return queryToObject(t, "SELECT * FROM %s WHERE id = %s".formatted(name(t), id));
    }

    public MObject[] get(Class<?> t, String where) throws SQLException {
        return queryToObject(t, "SELECT * FROM %s WHERE %s".formatted(name(t), where));
    }

    public void clear(Class<?> t) throws SQLException {
        perform("TRUNCATE %s CASCADE".formatted(name(t)));
    }

    public String formatQuery(Object[] values) {
        String result = "";
        for (Object v : values) result += v.toString() + ",";
        return result.substring(0, result.length() - 1);
    }

    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql:" + DBConfig.DATABASE;
        return DriverManager.getConnection(url, DBConfig.USER, DBConfig.PASSWORD);
    }
}
