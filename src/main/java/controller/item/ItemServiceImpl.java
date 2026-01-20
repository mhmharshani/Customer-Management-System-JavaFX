package controller.item;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import model.ItemTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {


    @Override
    public boolean addItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?)");

            psTm.setString(1, item.getCode());
            psTm.setString(2, item.getDescription());
            psTm.setString(3, item.getSize());
            psTm.setDouble(4, item.getPrice());
            psTm.setInt(5, item.getQtyOnHand());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode= ? ");

            psTm.setString(5, item.getCode());
            psTm.setString(1, item.getDescription());
            psTm.setString(2, item.getSize());
            psTm.setDouble(3, item.getPrice());
            psTm.setInt(4, item.getQtyOnHand());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM item WHERE ItemCode = ?");
            psTm.setString(1,id);

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchById(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTM = connection.prepareStatement("SELECT * FROM item WHERE ItemCode= ? ");
            psTM.setString(1,id);
            ResultSet resultSet = psTM.executeQuery();
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
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Item");

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
