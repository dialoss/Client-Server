package Server.Models;

import Server.Storage.OrderedItem;

public class OrderedModel implements OrderedItem {
    @ModelField(MIN = 2, UNIQUE = true, AUTO_GENERATE = true)
    public Integer id;

    @Override
    public Integer getId() {
        return this.id;
    }
}
