package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import service.ServiceFactory;
import service.SuperService;
import service.custom.CustomerService;
import service.custom.ItemService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public TextField txtOrderId;
    public TableView tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colTotal;
    public TextField txtQtyOnHand;
    public Label lblNetTotal;
    @FXML
    private ComboBox cmbCustomerIds;

    @FXML
    private ComboBox cmbItemIds;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblName;

    @FXML
    private Label lblCity;

    @FXML
    private Label lblStock;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUnitPrice;

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        loadCustomerIDs();
        loadItemCodes();

        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue )-> {
            assert newValue != null;
            setCustomerDataToLabels((String)newValue);
        });

        cmbItemIds.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue )-> {
            assert newValue != null;
            setItemDataToLables((String)newValue);
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        lblDate.setText(sdf.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setItemDataToLables(String newValue) {


        try {
            Item itemByCode = itemService.getItemByCode(newValue);
            lblDescription.setText(itemByCode.getDescription());
            lblStock.setText(itemByCode.getQtyOnHand().toString());
            lblUnitPrice.setText(itemByCode.getPrice().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCustomerDataToLabels(String id){

        try {
            Customer customer = customerService.searchCustomerById(id);
            lblName.setText(customer.getName());
            lblAddress.setText(customer.getAddress());
            lblCity.setText(customer.getCity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadCustomerIDs(){

        try {
            List<String> allCustomerIDs = customerService.getAllCustomerIDs();
            cmbCustomerIds.setItems(FXCollections.observableArrayList(allCustomerIDs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadItemCodes(){
        try {
            List<String> itemCodes = itemService.getItemCodes();
            cmbItemIds.setItems(FXCollections.observableArrayList(itemCodes));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void btnAddToCartOnAction(ActionEvent actionEvent) {

    }
}

