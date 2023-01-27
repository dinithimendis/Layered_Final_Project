//complete***********************************************************

package lk.ijse.jewellery.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.PlaceOrderBO;
import lk.ijse.jewellery.model.EmployeeDTO;
import lk.ijse.jewellery.model.OrderDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.view.tm.EmployeeTM;
import lk.ijse.jewellery.view.tm.OrderTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFormController {

    public TableView<OrderTM> OrdersTbl;
    public TableColumn<OrderDTO, String> orderIdCol;
    public TableColumn<OrderDTO, String> customerIdCol;
    public TableColumn<OrderDTO, String> DateCol;
    public TableColumn<OrderDTO, String> TimeCol;
    public TextField CustomerSearch;
    public AnchorPane OrderFormAnchorPane;
    public TextField orderIdTxt;
    public TextField dateTxt;
    public TextField customerIdTxt;
    public TextField TimeTxt;

    PlaceOrderBO placeOrderBO  = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("OrderTime"));

        try {
            loadAllOrders();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

    }

    private void loadAllOrders() throws SQLException, ClassNotFoundException {
        OrdersTbl.getItems().clear();
        try {
            ArrayList<OrderDTO> allOrders = placeOrderBO.getAllOrders();

            for (OrderDTO o : allOrders) {
                OrdersTbl.getItems().add(new OrderTM(o.getOrderId(), o.getCusId(), o.getOrderDate(), o.getOrderTime()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        OrdersTbl.refresh();
    }


    public void save(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", OrderFormAnchorPane);
    }

    /* search any orders */
    public void searchOrders(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = placeOrderBO.searchOrders(orderIdTxt.getText());
        if (resultSet == null){
            new Alert(Alert.AlertType.ERROR, "Invalid Item Id").show();
        } else {
            if (resultSet.next()) {
                orderIdTxt.setText(resultSet.getString(1));
                customerIdTxt.setText(resultSet.getString(2));
                dateTxt.setText(resultSet.getString(3));
                TimeTxt.setText(resultSet.getString(4));
                NotificationController.searchResultFound();
            } else {
                NotificationController.searchResultNotFound();
                loadAllOrders();
            }
        }
    }

}
