package repository.custom.impl;

import model.OrderDetails;
import repository.custom.OrderDetailRepository;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    @Override
    public boolean insertOrderDetail(List<OrderDetails> orderDetailsList) throws SQLException {
        for(OrderDetails orderDetails : orderDetailsList){
            boolean isInsertOrderDetail = insertOrderDetail(orderDetails);
            if (!isInsertOrderDetail){
                return false;
            }
        }

        return true;
    }

    //Method overloading
    public boolean insertOrderDetail(OrderDetails orderDetails) throws SQLException {
        System.out.println("in order detail insert");
        return CrudUtil.execute("INSERT INTO OrderDetail VALUES (?,?,?,?)",
                orderDetails.getOrderId(),
                orderDetails.getItemCode(),
                orderDetails.getQtyOnHand(),
                orderDetails.getDiscount()
                );
    }
}
