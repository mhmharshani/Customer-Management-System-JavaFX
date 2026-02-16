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
    public boolean create(Item item) throws SQLException {
        return CrudUtil.execute("INSERT INTO item VALUES (?,?,?,?,?)",
                item.getCode(),
                item.getDescription(),
                item.getSize(),
                item.getPrice(),
                item.getQtyOnHand()
        );
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return CrudUtil.execute("UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode= ? ",
                item.getDescription(),
                item.getSize(),
                item.getPrice(),
                item.getQtyOnHand(),
                item.getCode()
        );
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM item WHERE ItemCode = ?",id);
    }

    @Override
    public Item getById(String id) throws SQLException {
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

            //Using setters
//            Item item2 = new Item();
//            item2.setCode(resultSet.getString(1));
//            item2.setDescription(resultSet.getString(2));
//            item2.setSize(resultSet.getString(3));
//            item2.setPrice(resultSet.getDouble(4));

            System.out.println(item);

            return item;
        }
        return null;
    }

    @Override
    public List<Item> getAll() throws SQLException {
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
    }

    @Override
    public List<String> getItemCodes() throws SQLException {
        ArrayList<String> itemCodeList = new ArrayList<>();

        List<Item> all = getAll();

        all.forEach(item -> itemCodeList.add(item.getCode()));

        return itemCodeList;
    }
}
