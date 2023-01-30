/*
package lk.ijse.jewellery.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.model.IncomeReportsDTO;
import lk.ijse.jewellery.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MonthlyIncomeFormController implements Initializable {
    public TableView<IncomeReportsDTO> tblReport;
    public TableColumn<IncomeReportsDTO, String> colMonth;
    public TableColumn<IncomeReportsDTO, String> colOrderCount;
    public TableColumn<IncomeReportsDTO, String> colItemSoldQty;
    public TableColumn<IncomeReportsDTO, String> colCost;
    public AnchorPane monthlyBackOnAction;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMonth.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemSoldQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));


        try {
            tblReport.setItems(loadMonthlyIncomeReport());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //TOdo......................................

    */
/* generating monthly wise income  *//*

    private ObservableList<IncomeReportsDTO> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT (MONTHNAME(OrderDate)) ,sum(orderDetails.OrderQty),count(`order`.orderId),sum(orderDetails.totalAmount)  FROM `order` INNER JOIN orderDetails ON `order`.orderId = orderDetails.orderId GROUP BY extract(MONTH FROM(OrderDate))").executeQuery();
        ObservableList<IncomeReportsDTO> tempo = FXCollections.observableArrayList();

        while (resultSet.next()) {

            String date = resultSet.getString(1);
            int countOrderId = resultSet.getInt(3);
            int numberOfSoldItem = resultSet.getInt(2);
            double sumOfTotal = resultSet.getDouble(4);

            tempo.add(new IncomeReportsDTO(date, countOrderId, numberOfSoldItem, sumOfTotal));
        }
        return tempo;
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("BarChartFormController", monthlyBackOnAction);
    }
}*/
