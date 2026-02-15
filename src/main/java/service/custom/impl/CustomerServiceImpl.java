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
    public boolean addCustomer(Customer customer) {
        return repositoryType.create(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return repositoryType.update(customer);
    }

    @Override
    public boolean deleteCustomer(String id) {
        return repositoryType.deleteById(id);
    }

    @Override
    public Customer searchCustomerById(String id) {
        return repositoryType.getById(id);
    }

    @Override
    public List<Customer> getAll() {
        return repositoryType.getAll();
    }

    @Override
    public List<String> getAllCustomerIDs() {
        List<Customer> all = getAll();
        ArrayList<String> idList = new ArrayList<>();

        for(Customer customer: all){
            idList.add(customer.getId());
        }
        return idList;
    }
}
