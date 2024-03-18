package Server.Storage.Database;

import Server.Data.MObject;
import Server.Data.Models.Organization;

public class DBManager {
    DBOperations session;

    public DBManager() {
        this.session = new DBOperations();
    }

    public MObject[] load() throws Exception {
        return session.getAll(Organization.class);
    }

    public void save(Organization[] items) throws Exception {
        session.clear(Organization.class);
        for (Organization it : items) {
            session.insert(Organization.class, it);
        }
    }

    public DBOperations getSession() {
        return session;
    }
}
