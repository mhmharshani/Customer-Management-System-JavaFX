package service.custom;

import model.Item;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ItemService extends SuperService {

    boolean addItem(Item item) throws SQLException;

    boolean updateItem(Item item) throws SQLException;

    boolean deleteItem(String id) throws SQLException;

    Item searchById(String id) throws SQLException;

    List<Item> getAll() throws SQLException;

    Item getItemByCode(String code) throws SQLException;

    List<String> getItemCodes() throws SQLException;
}
