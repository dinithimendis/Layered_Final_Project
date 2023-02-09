package lk.ijse.jewellery.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.PlaceOrderBO;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.entity.Item;
import lk.ijse.jewellery.model.ItemDTO;
import lk.ijse.jewellery.model.OrderDTO;
import lk.ijse.jewellery.model.OrderDetailsDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.view.tm.CartTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static lk.ijse.jewellery.controller.OrderDetailsFormController.getOrderId;
import static lk.ijse.jewellery.controller.OrderDetailsFormController.saveOrderDetails;

public class PlaceOrderFormController {
    public TextField NameTxt;
    public TextField AddressTxt;
    public ComboBox<String> CustomerIDCombo;
    public TextField CityTxt;
    public TextField UnitPriceTxt;
    public TextField QtyOnHandTxt;
    public TextField DescriptionTxt;
    public TextField QtyTxt;
    public TextField DiscountTxt;
    public ComboBox<String> ItemCodeCombo;
    public Label OrderID;
    public TableView<CartTM> PlaceOrderTbl;
    public TableColumn<OrderDetailsDTO, String> OrderIDCol;
    public TableColumn<OrderDetailsDTO, String> ItemCodeCol;
    public TableColumn<OrderDetailsDTO, String> QtyCol;
    public TableColumn<OrderDetailsDTO, String> UnitPriceCol;
    public TableColumn<OrderDetailsDTO, String> DiscountCol;
    public TableColumn<OrderDetailsDTO, String> TotalCol;
    public JFXButton RemoveBtn;
    public Label TotalLbl;
    public Label DateLbl;
    public Label TimeLbl;
    public AnchorPane placeOrderAnchorPane;
    public int count;
    int cartSelectedRowForRemove = -1;
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    public static ItemDTO getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM item WHERE itemCode=?", code);
        if (result.next()) {
            return new ItemDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getDouble(5),
                    result.getString(6)
            );
        }
        return null;
    }

    public void initialize() throws SQLException, ClassNotFoundException {

        OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));


        loadDateAndTimeForPlaceOrderForm();
        setItemCodes();
        setOrderId();
        setCustomerIds();
        getItemCodes();
        //--------------------

        CustomerIDCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setCustomerDetails(newValue);
                } catch (SQLException | ClassNotFoundException throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        ItemCodeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setItemDetails(newValue);
                } catch (SQLException | ClassNotFoundException throwable) {
                    throwable.printStackTrace();
                }
            }
        });

       /* CustomerIDCombo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerDetails(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

        ItemCodeCombo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        setItemDetails(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });*/

        PlaceOrderTbl.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    cartSelectedRowForRemove = (int) newValue;

                });

    }

    /* setting item code's to combo box  */
    private void setItemCodes() throws SQLException, ClassNotFoundException {
        List<String> id = placeOrderBO.getItemCodes();
        ItemCodeCombo.getItems().addAll(id);

        // ItemCodeCombo.setItems(FXCollections.observableArrayList());
        //CustomerIDCombo.getItems().addAll(id);
    }

         /*try {
           ResultSet result = crudUtil.execute("SELECT cusId FROM customer");
            ArrayList<String> ids = new ArrayList<>();
            while (result.next()) {
                ids.add(result.getString(1));
            }

            ObservableList<String> cIdObList =
                    FXCollections.observableArrayList(ids);
            CustomerIDCombo.setItems(cIdObList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/

    /* setting customer id's to combo box  */
    //private void setCustomerIds() {
    public void setCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> id = placeOrderBO.getCustomerIds();
        CustomerIDCombo.getItems().addAll(id);

    }

    /**
     * if you select any kind of customer id loading their details into text fields
     */
    // public void setCustomerDetails(String selectedCustomerId) {
    private void setCustomerDetails(String ID) throws SQLException, ClassNotFoundException {

        Customer c1 = placeOrderBO.getCustomer(ID);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Customer data");
        } else {
            NameTxt.setText(c1.getCusName());
            AddressTxt.setText(c1.getAddress());
            CityTxt.setText(c1.getProvince());
        }
       /* try {
            CustomerDTO c = getCustomer(selectedCustomerId);
            if (c != null) {

                NameTxt.setText(c.getCusName());
                AddressTxt.setText(c.getAddress());
                CityTxt.setText(c.getProvince());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * if you select any kind of item id loading their details into text fields
     */
    //  public void setItemDetails(String selectedItemCode) {
    private void setItemDetails(String ItemCode) throws SQLException, ClassNotFoundException {

        Item i = placeOrderBO.searchItem(ItemCode);
        if (i == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Item data");
        } else {
            DescriptionTxt.setText(i.getDescription());
            QtyOnHandTxt.setText(String.valueOf(i.getQty()));
            UnitPriceTxt.setText(String.valueOf(i.getUnitPrice()));
        }

       /* try {

            //TODO -------------------------------------------------
            ItemDTO i = getItem(selectedItemCode);
            if (i != null) {
                DescriptionTxt.setText(i.getDescription());
                QtyOnHandTxt.setText(String.valueOf((i.getQty())));
                UnitPriceTxt.setText(String.valueOf(i.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * date and time thread
     */
    private void loadDateAndTimeForPlaceOrderForm() {
        //Set Date
        DateLbl.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            TimeLbl.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    /* set order id */
    private void setOrderId() {
        try {
            OrderID.setText(getOrderId());
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
    }

    /* conforming orders */
    public void ConfirmOrderOnAction() {
        ArrayList<OrderDetailsDTO> details = new ArrayList();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetailsDTO(
                            tm.getOrderId(),
                            tm.getItemCode(),
                            tm.getQty(),
                            tm.getTotal(),
                            tm.getDiscount()
                    ));
            tm.getItemCode();

        }
        OrderDTO order = new OrderDTO(
                OrderID.getText(),
                CustomerIDCombo.getValue(),
                DateLbl.getText(),
                TimeLbl.getText()
        );

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = crudUtil.execute("INSERT INTO `order` VALUES(?,?,?,?)",
                    order.getOrderId(), order.getCusId(), order.getOrderDate(), order.getOrderTime());
            if (isOrderSaved) {

                //TODO -----------------------------------------------
                boolean isDetailsSaved = saveOrderDetails(details);
                if (isDetailsSaved) {
                    connection.commit();
                    NotificationController.AddedDetailsSuccessFully();
                } else {
                    connection.rollback();
                    NotificationController.cantAddedDetailsSuccessFully();
                }
            } else {
                connection.rollback();
                NotificationController.cantAddedDetailsSuccessFully();
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        } finally {

            try {
                assert connection != null;
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
        setOrderId();
    }

    /* if exists previous item codes  */
    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    /* calculate total */
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getTotal();
        }
        TotalLbl.setText(String.valueOf(total));
    }

    /* removing or clearing tm */
    public void RemoveOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
        } else {
            tmList.remove(cartSelectedRowForRemove);
            if (tmList.isEmpty()) {
            }
            NotificationController.detailsRemoved();
            calculateTotal();
            PlaceOrderTbl.refresh();
        }
    }

    /* adding to the cart */
    public void AddToCartOnAction(ActionEvent actionEvent) {
        try {
            double unitPrice = Double.parseDouble(UnitPriceTxt.getText());
            double discount = Double.parseDouble(DiscountTxt.getText());
            double qty = Double.parseDouble((QtyTxt.getText()));
            double Cost = ((unitPrice * qty) * discount / 100);
            double totalCost = (unitPrice * qty) - Cost;

            CartTM isExists = isExists(ItemCodeCombo.getValue());

            if (isExists != null) {
                for (CartTM temp : tmList
                ) {
                    if (temp.equals(isExists)) {
                        temp.setTotal(temp.getTotal() + totalCost);
                        temp.setQty((temp.getQty() + qty));
                    }
                }
            } else {

                CartTM tm = new CartTM(

                        OrderID.getText(),
                        ItemCodeCombo.getValue(),
                        DescriptionTxt.getText(),
                        qty,
                        unitPrice,
                        discount,
                        totalCost
                );

                tmList.add(tm);
                PlaceOrderTbl.setItems(tmList);
            }
            PlaceOrderTbl.refresh();
            calculateTotal();
        } catch (Exception ignored) {
        }
    }

    /* clearing details */
    public void ClearOrderOnAction() {
        tmList.clear();
        PlaceOrderTbl.refresh();
        TotalLbl.setText("0.00/=");
        NameTxt.clear();
        AddressTxt.clear();
        CityTxt.clear();
        UnitPriceTxt.clear();
        QtyOnHandTxt.clear();
        DescriptionTxt.clear();
        QtyTxt.clear();
        DiscountTxt.clear();
    }

    public void discountShiftOnAction(ActionEvent actionEvent) {
    }

    public void playMouseExitedAnimation(MouseEvent mouseEvent) {
    }

    public void exitOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", placeOrderAnchorPane);
    }

    /* conform order */
    public void confirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ConfirmOrderOnAction();
        ClearOrderOnAction();

        try {
            count = (Integer.parseInt(QtyOnHandTxt.getText()) - Integer.parseInt(QtyTxt.getText()));
            QtyOnHandTxt.setText(Integer.toString(count));

            ItemDTO it = new ItemDTO();
            it.setQty(count);


            it.setItemCode(ItemCodeCombo.getValue());
            it.setQty(count);
            crudUtil.execute("UPDATE Item SET qty=? WHERE ItemCode = ?", it.getQty(), it.getItemCode());
        } catch (NumberFormatException ignored) {
        }
    }

    /* print bill on action */
    public void printBillOnAction(ActionEvent actionEvent) {
        ObservableList<CartTM> tableRecords = PlaceOrderTbl.getItems();

        try {

            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/Prison.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // public static ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
    public void getItemCodes() throws SQLException, ClassNotFoundException {
      /*  ResultSet result = crudUtil.execute("SELECT itemCode FROM item");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;*/
        ArrayList<String> ItemCodes = placeOrderBO.getItemCodes();
        ItemCodeCombo.getItems().addAll(ItemCodes);


    }


}
