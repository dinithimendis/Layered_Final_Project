package lk.ijse.jewellery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.model.OrderDetails;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.dao.crudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsFormController {
    public TextField SearchOrderID;
    public TableColumn<OrderDetails, String> orderIdCol;
    public TableColumn<OrderDetails, String> ItemCodeCol;
    public TableColumn<OrderDetails, String> QtyCol;
    public TableColumn<OrderDetails, String> DiscountCol;
    public TableColumn<OrderDetails, String> PriceCol;
    public AnchorPane OrderDetailsFormAnchorPane;
    public TableView<OrderDetails> ODetailFormTbl;
    public TextField itemCodeTxt;
    public TextField qtyTxt;
    public TextField discountTxt;
    public TextField priceTxt;


    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            loadAllOrderDetails();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

    }

    /* loading all order details */
    private void loadAllOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM orderDetails");
        ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new OrderDetails(
                            result.getString("orderId"),
                            result.getString("itemCode"),
                            result.getDouble("OrderQty"),
                            result.getDouble("totalAmount"),
                            result.getDouble("discount")
                    ));
        }
        ODetailFormTbl.setItems(obList);
        ODetailFormTbl.refresh();
    }

    /* searching order details  */
    public void search(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = crudUtil.execute("SELECT * FROM orderDetails WHERE orderId=?", SearchOrderID.getText());

        if (resultSet.next()) {
            itemCodeTxt.setText(resultSet.getString(2));
            qtyTxt.setText(resultSet.getString(3));
            priceTxt.setText(resultSet.getString(4));
            discountTxt.setText(resultSet.getString(5));

            NotificationController.searchResultFound();
        } else {
            NotificationController.searchResultNotFound();
            loadAllOrderDetails();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", OrderDetailsFormAnchorPane);
    }

     public static boolean  saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det : details
        ) {

            boolean isDetailsSaved = crudUtil.execute("INSERT INTO orderDetails VALUES(?,?,?,?,?)",
                    det.getOrderId(), det.getItemCode(), det.getOrderQty(), det.getDiscount(), det.getTotalAmount());
            if (isDetailsSaved) {

                if (!(boolean)crudUtil.execute("UPDATE ITEM SET itemCode=itemCode-? WHERE itemCode = ?", det.getItemCode(), (int) det.getOrderQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = crudUtil.execute("SELECT orderId FROM  `order` ORDER BY orderId DESC LIMIT 1");
        if (set.next()) {
            int tempId = Integer.parseInt(set.getString(1).split("-")[1]);

            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }

    }
}
