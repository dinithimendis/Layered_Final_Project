package lk.ijse.jewellery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.DailyIncomeReportsBO;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.model.DailyIncomeReportsDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.view.tm.DailyIncomeReportsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeFormController implements Initializable {
    public TableView<DailyIncomeReportsTM> tblReport;
    public TableColumn<DailyIncomeReportsDTO, String> colDate;
    public TableColumn<DailyIncomeReportsDTO, String> colOrderCost;
    public TableColumn<DailyIncomeReportsDTO, String> colItemQty;
    public TableColumn<DailyIncomeReportsDTO, String> colCost;
    public AnchorPane dailyIncomeReportContext;

    DailyIncomeReportsBO incomeReportsBO = (DailyIncomeReportsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INCOME_REPORT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));

        try {

            tblReport.setItems(loadDailyIncomeReport());
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            pieChartData.add(new PieChart.Data("Iphone 5S", 13));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * load all reports details
     *
     * @return
     */
    // TOdo......................................
    private ObservableList<DailyIncomeReportsTM> loadDailyIncomeReport() throws SQLException, ClassNotFoundException  {
        tblReport.getItems().clear();
        try {
            ArrayList<DailyIncomeReportsDTO> allReports = incomeReportsBO.getAll();

            for (DailyIncomeReportsDTO c : allReports) {
                tblReport.getItems().add(new DailyIncomeReportsTM(c.getDate(), c.getNumberOfOrders(), c.getNumberOfSoldItem(), c.getFinalCost()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        tblReport.refresh();
        return null;
    }


   /**
     * calculate item count
     */
    // TOdo......................................
    private ArrayList<DailyIncomeReportsDTO> getCountItems() throws SQLException, ClassNotFoundException {
        ResultSet rest = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT DISTINCT(`order`.OrderDate),sum(orderDetails.OrderQty) " +
                        "FROM `Order` INNER JOIN orderDetails ON  `order`.orderId = orderDetails.orderId " +
                        "GROUP BY `order`.OrderDate").executeQuery();
        ArrayList<DailyIncomeReportsDTO> temp = new ArrayList<>();
        while (rest.next()) {

            temp.add(new DailyIncomeReportsDTO(rest.getString(1), rest.getInt(2)));
        }

        return temp; }

   public void backToOnAction(ActionEvent actionEvent) throws IOException {
       Navigation.AdminORCashierUI("BarChartFormController", dailyIncomeReportContext);
    }}
