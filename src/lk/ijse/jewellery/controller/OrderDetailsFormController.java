package lk.ijse.jewellery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.EmployeeBO;
import lk.ijse.jewellery.bo.custom.PlaceOrderBO;
import lk.ijse.jewellery.model.EmployeeDTO;
import lk.ijse.jewellery.model.OrderDetailsDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.view.tm.EmployeeTM;
import lk.ijse.jewellery.view.tm.OrderDetailsTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsFormController {
    public TextField SearchOrderID;
    public TableColumn<OrderDetailsDTO, String> orderIdCol;
    public TableColumn<OrderDetailsDTO, String> ItemCodeCol;
    public TableColumn<OrderDetailsDTO, String> QtyCol;
    public TableColumn<OrderDetailsDTO, String> DiscountCol;
    public TableColumn<OrderDetailsDTO, String> PriceCol;
    public AnchorPane OrderDetailsFormAnchorPane;
    public TableView<OrderDetailsTM> ODetailFormTbl;
    public TextField itemCodeTxt;
    public TextField qtyTxt;
    public TextField discountTxt;
    public TextField priceTxt;


    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
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
        ODetailFormTbl.getItems().clear();
        try {
            ArrayList<OrderDetailsDTO> allOrderDetails = placeOrderBO.getAll();

            for (OrderDetailsDTO od : allOrderDetails) {
                ODetailFormTbl.getItems().add(new OrderDetailsTM(od.getOrderId(), od.getItemCode(), od.getOrderQty(), od.getTotalAmount(), od.getDiscount()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        ODetailFormTbl.refresh();
    }

    /* searching order details  */
    public void search(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = placeOrderBO.search(SearchOrderID.getText());
        if (resultSet == null){
            new Alert(Alert.AlertType.ERROR, "Invalid Item Id").show();
        } else {

            if (resultSet.next()) {
                itemCodeTxt.setText(resultSet.getString(2));
                qtyTxt.setText(resultSet.getString(3));
                priceTxt.setText(resultSet.getString(4));
                discountTxt.setText(resultSet.getString(5));


            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
                loadAllOrderDetails();
            }
        }

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", OrderDetailsFormAnchorPane);
    }

    //TOdo......................................
     public static boolean  saveOrderDetails(ArrayList<OrderDetailsDTO> details) throws SQLException, ClassNotFoundException {
        for (OrderDetailsDTO det : details) {

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

    //TOdo......................................
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
