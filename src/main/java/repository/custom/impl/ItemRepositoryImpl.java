package repository.custom.impl;

import db.DBConnection;
import model.Item;
import repository.custom.ItemRepository;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public boolean create(Item item) {
        try {

            return CrudUtil.execute("INSERT INTO item VALUES (?,?,?,?,?)",
                    item.getCode(),
                    item.getDescription(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQtyOnHand()
                    );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Item item) {
        try {

            return CrudUtil.execute("UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode= ? ",
                    item.getDescription(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQtyOnHand(),
                    item.getCode()
                    );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {

            return CrudUtil.execute("DELETE FROM item WHERE ItemCode = ?",id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item getById(String id) {
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM item WHERE ItemCode= ? ",id);
            Boolean isExist = resultSet.next();

            if(isExist) {
                Item item = new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );

                System.out.println(item);

                return item;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Item> getAll() {
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");

            ArrayList<Item> itemList = new ArrayList<>();

            while(resultSet.next()){
                itemList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getInt(5)
                        )
                );

            }
            return itemList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
