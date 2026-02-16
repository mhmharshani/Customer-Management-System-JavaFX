package repository.custom;

import model.OrderDetails;
import repository.SuperRepository;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailRepository extends SuperRepository {

    boolean insertOrderDetail(List<OrderDetails> orderDetailsList) throws SQLException;
}
