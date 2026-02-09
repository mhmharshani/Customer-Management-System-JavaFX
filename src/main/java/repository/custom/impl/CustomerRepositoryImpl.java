package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import repository.custom.CustomerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public boolean create(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?)");

            psTm.setString(1, customer.getId());
            psTm.setString(2, customer.getTitle());
            psTm.setString(3, customer.getName());
            psTm.setObject(4,customer.getDobValue());
            psTm.setDouble(5,customer.getSalary());
            psTm.setString(6, customer.getAddress());
            psTm.setString(7, customer.getCity());
            psTm.setString(8, customer.getProvince());
            psTm.setString(9,customer.getPostalCode());

            return (psTm.executeUpdate()>0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE customer SET CustTitle=?, CustName=?, DOB=?, salary=?, CustAddress=?, City=?, Province=?, PostalCode=? WHERE CustID= ? ");
            psTm.setString(9, customer.getId());
            psTm.setString(1, customer.getTitle());
            psTm.setString(2, customer.getName());
            psTm.setObject(3, customer.getDobValue());
            psTm.setDouble(4, customer.getSalary());
            psTm.setString(5, customer.getAddress());
            psTm.setString(6, customer.getCity());
            psTm.setString(7, customer.getProvince());
            psTm.setString(8, customer.getPostalCode());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM customer WHERE CustID = ?");
            psTm.setString(1,id);

            return (psTm.executeUpdate()>0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTM = connection.prepareStatement("SELECT * FROM customer WHERE CustID= ? ");
            psTM.setString(1,id);
            ResultSet resultSet = psTM.executeQuery();
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println("Connection in Load Table : "+connection);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
