package service.custom;

import model.Item;
import service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {

    boolean addItem(Item item);

    boolean updateItem(Item item);

    boolean deleteItem(String id);

    Item searchById(String id);

    List<Item> getAll();
}
