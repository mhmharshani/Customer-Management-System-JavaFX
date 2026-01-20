package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.item.ItemServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.CustomerTM;
import db.DBConnection;
import model.Item;
import model.ItemTM;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPostalCode;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colSalary;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String title = cmbTitle.getValue().toString();
        LocalDate dobValue = dateDob.getValue();
        Double salary = Double.parseDouble(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        Customer customer = new Customer(id,name,title,dobValue,salary,address,city,province,postalCode);

        System.out.println(customer);

        Boolean isAdded = new CustomerServiceImpl().addCustomer(customer);

        if(isAdded){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added").show();
            loadTable();
        }
        else{
            new Alert(Alert.AlertType.ERROR,"Customer Not Added").show();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    public void loadTable(){

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        List<Customer> all = customerService.getAll();

        ArrayList<CustomerTM> customerTMArrayList = new ArrayList<>();
        all.forEach(customer -> {
            customerTMArrayList.add(new CustomerTM(
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    Date.valueOf(customer.getDobValue()),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        });

        ObservableList<CustomerTM> observableList = FXCollections.observableArrayList(customerTMArrayList);
        tblCustomers.setItems(observableList);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        Boolean isDeleted = new CustomerServiceImpl().deleteCustomer(txtId.getText());

        if(isDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer NOT Deleted!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

        Customer customer = new CustomerServiceImpl().searchCustomerById(txtId.getText());
        if(customer !=null){
            setTextToValues(customer);
        }
        else{
            new Alert(Alert.AlertType.INFORMATION,"No customer found.").show();

            cmbTitle.setValue("");
            txtName.setText("");
            dateDob.setValue(null);
            txtSalary.setText("");
            txtAddress.setText("");
            txtCity.setText("");
            txtProvince.setText("");
            txtPostalCode.setText("");
        }

    }

    private void setTextToValues(CustomerTM customerTm){
        if(customerTm !=null){
            txtId.setText(customerTm.getId());
            String[] details = customerTm.getName().split(". ");
            cmbTitle.setValue(details[0]);
            txtName.setText(details[1]);
            dateDob.setValue(customerTm.getDob().toLocalDate());
            txtSalary.setText(customerTm.getSalary().toString());
            txtAddress.setText(customerTm.getAddress());
            txtCity.setText(customerTm.getCity());
            txtProvince.setText(customerTm.getProvince());
            txtPostalCode.setText(customerTm.getPostalCode());
        }

    }

    private void setTextToValues(Customer customer){
        txtId.setText(customer.getId());
        cmbTitle.setValue(customer.getTitle());
        txtName.setText(customer.getName());
        dateDob.setValue(customer.getDobValue());
        txtSalary.setText(customer.getSalary().toString());
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostalCode.setText(customer.getPostalCode());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String name = txtName.getText();
        String title = cmbTitle.getValue().toString();
        LocalDate dobValue = dateDob.getValue();
        Double salary = Double.parseDouble(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        Customer customer = new Customer(id,name,title,dobValue,salary,address,city,province,postalCode);

        Boolean isUpdated = new CustomerServiceImpl().updateCustomer(customer);

        if(isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated").show();
            loadTable();
        }
        else{
            new Alert(Alert.AlertType.ERROR,"Customer Not Updated").show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitle.setItems(FXCollections.observableArrayList(Arrays.asList("Mr","Miss","Ms")));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        loadTable();

        //Enable select a record from table directly
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue,oldValue,newValue) ->{

            System.out.println("Select record new value : "+newValue);

            assert newValue !=null;
            setTextToValues((CustomerTM) newValue);
        });

    }
}
