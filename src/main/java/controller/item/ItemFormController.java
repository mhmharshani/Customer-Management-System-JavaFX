package controller.item;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerTM;
import model.Item;
import model.ItemTM;

import java.net.URL;
import java.sql.*;
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?)");

            psTm.setString(1, item.getCode());
            psTm.setString(2, item.getDescription());
            psTm.setString(3, item.getSize());
            psTm.setDouble(4, item.getPrice());
            psTm.setInt(5, item.getQtyOnHand());

            if(psTm.executeUpdate()>0){
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
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM item WHERE ItemCode = ?");
            psTm.setString(1,txtCode.getText());

            if(psTm.executeUpdate()>0){
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
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTM = connection.prepareStatement("SELECT * FROM item WHERE ItemCode= ? ");
            psTM.setString(1,txtCode.getText());
            ResultSet resultSet = psTM.executeQuery();
            Boolean isExist = resultSet.next();

            if(isExist){
                Item item = new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );

                System.out.println(item);

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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode= ? ");

            psTm.setString(5, item.getCode());
            psTm.setString(1, item.getDescription());
            psTm.setString(2, item.getSize());
            psTm.setDouble(3, item.getPrice());
            psTm.setInt(4, item.getQtyOnHand());

            if(psTm.executeUpdate()>0){
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

        ItemServiceImpl itemService = new ItemServiceImpl();
        List<Item> all = itemService.getAll();

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
    }

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
}
