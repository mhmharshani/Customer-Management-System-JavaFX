package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import model.tm.ItemTM;
import service.ServiceFactory;
import service.custom.ItemService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colSize;

    @FXML
    private TableView tblItem;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtSize;

    ItemService serviceType = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        loadTable();

        //Enable select a record from table directly
        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue,oldValue,newValue) ->{

            System.out.println(newValue);

            assert newValue !=null;
            setTextToValues((ItemTM) newValue);
        });
    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {

        String code = txtCode.getText();
        String description = txtDescription.getText();
        String size = txtSize.getText();
        Double price = Double.parseDouble(txtPrice.getText());
        Integer qty = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code,description,size,price,qty);

        System.out.println(item);

        try {
            if(serviceType.addItem(item)){
                new Alert(Alert.AlertType.INFORMATION,"Item Added").show();
                loadTable();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Item Not Added").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        try {
            if(serviceType.deleteItem(txtCode.getText())){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        Item item = null;
        try {
            item = serviceType.searchById(txtCode.getText());

            if(item!=null){
                setTextToValues(item);
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"No item found.").show();

                txtCode.setText("");
                txtDescription.setText("");
                txtSize.setText("");
                txtPrice.setText("");
                txtQtyOnHand.setText("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setTextToValues(Item item){
        txtCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtSize.setText(item.getSize());
        txtPrice.setText(item.getPrice().toString());
        txtQtyOnHand.setText(item.getQtyOnHand().toString());
    }

    private void setTextToValues(ItemTM itemTm){
        if(itemTm!=null){
            txtCode.setText(itemTm.getCode());
            txtDescription.setText(itemTm.getDescription());
            txtSize.setText(itemTm.getSize());
            txtPrice.setText(itemTm.getPrice().toString());
            txtQtyOnHand.setText(itemTm.getQtyOnHand().toString());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String code = txtCode.getText();
        String description = txtDescription.getText();
        String size = txtSize.getText();
        Double price = Double.parseDouble(txtPrice.getText());
        Integer qty = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code,description,size,price,qty);

        System.out.println(item);

        try {
            if(serviceType.updateItem(item)){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated").show();
                loadTable();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Item Not Updated").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTable(){

        List<Item> all = null;
        try {
            all = serviceType.getAll();

            ArrayList<ItemTM> itemTMArrayList = new ArrayList<>();
            all.forEach(item -> {
                itemTMArrayList.add(new ItemTM(
                        item.getCode(),
                        item.getDescription(),
                        item.getSize(),
                        item.getPrice(),
                        item.getQtyOnHand()
                ));
            });
            tblItem.setItems(FXCollections.observableArrayList(itemTMArrayList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
