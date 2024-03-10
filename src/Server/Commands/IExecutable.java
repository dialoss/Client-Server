package Server.Commands;

import Server.Storage.CollectionManager;

public interface IExecutable {
    String execute(CollectionManager collectionManager);
}
