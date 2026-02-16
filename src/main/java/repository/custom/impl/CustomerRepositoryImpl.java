package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import repository.custom.CustomerRepository;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public boolean create(Customer customer) throws SQLException {
            return CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?)",
                customer.getId(),
                customer.getTitle(),
                customer.getName(),
                customer.getDobValue(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode()
                );
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.execute("UPDATE customer SET CustTitle=?, CustName=?, DOB=?, salary=?, CustAddress=?, City=?, Province=?, PostalCode=? WHERE CustID= ? ",
                customer.getTitle(),
                customer.getName(),
                customer.getDobValue(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode(),
                customer.getId()
        );
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM customer WHERE CustID = ?",id);
    }

    @Override
    public Customer getById(String id) throws SQLException {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE CustID= ? ",id);
            Boolean isExist = resultSet.next();

            if(isExist){
                Customer customer = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getString(2),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );

                System.out.println(customer);

                return customer;
            }

        return null;
    }

    @Override
    public List<Customer> getAll() throws SQLException {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer");
            ArrayList<Customer> customerList = new ArrayList<>();

            while(resultSet.next()){
                customerList.add(
                        new Customer(
                                resultSet.getString(1),
                                resultSet.getString(3),
                                resultSet.getString(2),
                                resultSet.getDate(4).toLocalDate(),
                                resultSet.getDouble(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        )
                );

            }
            return customerList;

    }
}
