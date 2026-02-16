package service.custom.impl;

import db.DBConnection;
import model.Item;
import repository.RepositoryFactory;
import repository.SuperRepository;
import repository.custom.ItemRepository;
import service.custom.ItemService;
import util.RepositoryType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    ItemRepository repositoryType = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ITEM);

    @Override
    public boolean addItem(Item item) throws SQLException {
        return repositoryType.create(item);
    }

    @Override
    public boolean updateItem(Item item) throws SQLException {
        return repositoryType.update(item);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return repositoryType.deleteById(id);
    }

    @Override
    public Item searchById(String id) throws SQLException {
        return repositoryType.getById(id);
    }

    @Override
    public List<Item> getAll() throws SQLException {
        return repositoryType.getAll();
    }

    @Override
    public Item getItemByCode(String code) throws SQLException {
        return repositoryType.getById(code);
    }

    @Override
    public List<String> getItemCodes() throws SQLException {
        return repositoryType.getItemCodes();
    }
}
