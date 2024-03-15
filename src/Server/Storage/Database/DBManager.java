package Server.Storage.Database;

import Server.Models.MObject;
import Server.Models.Organization;

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
}
