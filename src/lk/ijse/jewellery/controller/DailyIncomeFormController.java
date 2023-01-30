//package lk.ijse.jewellery.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.Initializable;
//import javafx.scene.chart.PieChart;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.AnchorPane;
//import lk.ijse.jewellery.bo.BOFactory;
//import lk.ijse.jewellery.bo.custom.IncomeReportsBO;
//import lk.ijse.jewellery.db.DBConnection;
//import lk.ijse.jewellery.model.IncomeReportsDTO;
//import lk.ijse.jewellery.util.Navigation;
//import lk.ijse.jewellery.view.tm.IncomeReportsTM;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class DailyIncomeFormController implements Initializable {
//    public TableView<IncomeReportsTM> tblReport;
//    public TableColumn<IncomeReportsDTO, String> colDate;
//    public TableColumn<IncomeReportsDTO, String> colOrderCost;
//    public TableColumn<IncomeReportsDTO, String> colItemQty;
//    public TableColumn<IncomeReportsDTO, String> colCost;
//    public AnchorPane dailyIncomeReportContext;
//
//    IncomeReportsBO incomeReportsBO = (IncomeReportsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.I);
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
//        colItemQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
//        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));
//
//        try {
//
//            tblReport.setItems(loadDailyIncomeReport());
//            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
//            pieChartData.add(new PieChart.Data("Iphone 5S", 13));
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * load all reports details
//     */
//    // TOdo......................................
//    private ObservableList<IncomeReportsDTO> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
//     /*   ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement(
//                "SELECT `order`.OrderDate,count(`order`.orderId),sum(orderDetails.totalAmount) " +
//                        "FROM `order` INNER JOIN orderDetails ON `order`.orderId = orderDetails.orderId " +
//                        "GROUP BY OrderDate").executeQuery();
//        ObservableList<IncomeReportsDTO> obList = FXCollections.observableArrayList();
//        ArrayList<IncomeReportsDTO> data = getCountItems();
//
//        int i = 0;
//        while (resultSet.next()) {
//
//            String date = resultSet.getString(1);
//            int countOrderId = resultSet.getInt(2);
//            double sumOfTotal = resultSet.getDouble(3);
//
//            obList.add(new IncomeReportsDTO(date, countOrderId, data.get(i).getNumberOfSoldItem(), sumOfTotal));
//            i++;
//        }*/
//        ArrayList<IncomeReportsDTO> incomeReportsDTOS = incomeReportsBO.getdailyItems();
//        for (IncomeReportsDTO c : incomeReportsDTOS) {
//            dailyTmArrayList.add(new IncomeReportsTM(c.getDate(), c.getNumberOfSoldItem(), c.getNumberOfOrders(), c.getFinalCost()));
//        }
//        return obList;
//    }
//
//
//    /**
//     * calculate item count
//     */
//    // TOdo......................................
//    private ArrayList<IncomeReportsDTO> getCountItems() throws SQLException, ClassNotFoundException {
//        ResultSet rest = DBConnection.getInstance().getConnection().prepareStatement(
//                "SELECT DISTINCT(`order`.OrderDate),sum(orderDetails.OrderQty) " +
//                        "FROM `Order` INNER JOIN orderDetails ON  `order`.orderId = orderDetails.orderId " +
//                        "GROUP BY `order`.OrderDate").executeQuery();
//        ArrayList<IncomeReportsDTO> temp = new ArrayList<>();
//
//        while (rest.next()) {
//
//            temp.add(new IncomeReportsDTO(rest.getString(1), rest.getInt(2)));
//        }
//
//        return temp;
//    }
//
//    public void backToOnAction(ActionEvent actionEvent) throws IOException {
//        Navigation.AdminORCashierUI("BarChartFormController", dailyIncomeReportContext);
//    }
//}