package lk.ijse.jewellery.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.DailyIncomeReportsBO;
import lk.ijse.jewellery.bo.custom.MonthlyReportBO;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.model.DailyIncomeReportsDTO;
import lk.ijse.jewellery.model.DailyIncomeReportsDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.view.tm.DailyIncomeReportsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MonthlyIncomeFormController implements Initializable {
    public TableView<DailyIncomeReportsTM> tblReport;
    public TableColumn<DailyIncomeReportsDTO, String> colMonth;
    public TableColumn<DailyIncomeReportsDTO, String> colOrderCount;
    public TableColumn<DailyIncomeReportsDTO, String> colItemSoldQty;
    public TableColumn<DailyIncomeReportsDTO, String> colCost;
    public AnchorPane monthlyBackOnAction;

    MonthlyReportBO monthlyReportBO = (MonthlyReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MONTHLY_REPORT);


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

 //generating monthly wise income

    /*private ObservableList<DailyIncomeReportsTM> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT (MONTHNAME(OrderDate)) ,sum(orderDetails.OrderQty),count(`order`.orderId),sum(orderDetails.totalAmount)  FROM `order` INNER JOIN orderDetails ON `order`.orderId = orderDetails.orderId GROUP BY extract(MONTH FROM(OrderDate))").executeQuery();
        ObservableList<DailyIncomeReportsDTO> tempo = FXCollections.observableArrayList();

        while (resultSet.next()) {

            String date = resultSet.getString(1);
            int countOrderId = resultSet.getInt(3);
            int numberOfSoldItem = resultSet.getInt(2);
            double sumOfTotal = resultSet.getDouble(4);

            tempo.add(new DailyIncomeReportsDTO(date, countOrderId, numberOfSoldItem, sumOfTotal));
        }
        return null;
    }*/

    private ObservableList<DailyIncomeReportsTM> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException  {
        tblReport.getItems().clear();
        try {
            ArrayList<DailyIncomeReportsDTO> allReports = monthlyReportBO.getAll();

            for (DailyIncomeReportsDTO c : allReports) {
                tblReport.getItems().add(new DailyIncomeReportsTM(c.getDate(), c.getNumberOfSoldItem(), c.getNumberOfOrders(), c.getFinalCost()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        tblReport.refresh();
        return null;
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("BarChartFormController", monthlyBackOnAction);
    }
}
