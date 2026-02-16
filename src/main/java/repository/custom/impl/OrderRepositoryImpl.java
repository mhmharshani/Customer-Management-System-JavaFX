package repository.custom.impl;

import db.DBConnection;
import model.Order;
import repository.RepositoryFactory;
import repository.SuperRepository;
import repository.custom.CustomerRepository;
import repository.custom.ItemRepository;
import repository.custom.OrderDetailRepository;
import repository.custom.OrderRepository;
import util.RepositoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderRepositoryImpl implements OrderRepository {

    ItemRepository itemRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ITEM);
    OrderDetailRepository detailRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ORDERDETAIL);

    @Override
    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement psTM = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?)");
            psTM.setString(1, order.getOrderId());
            psTM.setObject(2,order.getOrderDate());
            psTM.setString(3, order.getCustomerId());

            boolean isOrderInsert = psTM.executeUpdate() > 0;
            if(isOrderInsert){
                boolean isOrderDetailsInsert = detailRepository.insertOrderDetail(order.getOrderDetailsList());
                if(isOrderDetailsInsert){
                    boolean isStockUpdate = itemRepository.updateStock((order.getOrderDetailsList()));
                    if(isStockUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }

    }
}
