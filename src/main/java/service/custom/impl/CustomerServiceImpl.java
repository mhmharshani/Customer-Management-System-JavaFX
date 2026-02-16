package service.custom.impl;

import db.DBConnection;
import model.Customer;
import repository.RepositoryFactory;
import repository.SuperRepository;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import util.RepositoryType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerRepository repositoryType = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) throws SQLException {
        return repositoryType.create(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        return repositoryType.update(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException  {
        return repositoryType.deleteById(id);
    }

    @Override
    public Customer searchCustomerById(String id) throws SQLException {
        return repositoryType.getById(id);
    }

    @Override
    public List<Customer> getAll() throws SQLException{
        return repositoryType.getAll();
    }

    @Override
    public List<String> getAllCustomerIDs() throws SQLException {
        List<Customer> all = getAll();
        ArrayList<String> idList = new ArrayList<>();

        for(Customer customer: all){
            idList.add(customer.getId());
        }
        return idList;
    }
}
