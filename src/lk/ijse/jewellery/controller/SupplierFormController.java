package lk.ijse.jewellery.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.ItemBO;
import lk.ijse.jewellery.bo.custom.SupplierBO;
import lk.ijse.jewellery.model.ItemDTO;
import lk.ijse.jewellery.model.SupplierDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.util.validationUtil;
import lk.ijse.jewellery.view.tm.ItemTM;
import lk.ijse.jewellery.view.tm.SupplierTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierFormController {

    public Button btnSave;
    public TableColumn<SupplierDTO, String> CashierIdCol;
    public TableColumn<SupplierDTO, String> CashierNameCol;
    public TableColumn<SupplierDTO, String> nicCol;
    public TableColumn<SupplierDTO, String> AddressCol;
    public TableColumn<SupplierDTO, String> ContactCol;
    public TableColumn<SupplierDTO, String> companyName;
    public TableView<SupplierTM> SupplierTable;
    public AnchorPane SupplierAnchorPane;
    public TextField companyTxt;
    public TextField ContactTxt;
    public TextField NicTxt;
    public TextField NameTxt;
    public TextField idTxt;
    public TextField Address;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    //TODO implement initialize method first
    public void initialize() {
        CashierIdCol.setCellValueFactory(new PropertyValueFactory<>("supId"));
        CashierNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        AddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        ContactCol.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        try {
            loadAllSuppliers();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        map.put(idTxt, Pattern.compile("^(S00-)[0-9]{1,4}$"));
        map.put(Address, Pattern.compile("^[A-z]{4,15}$"));
        map.put(NameTxt, Pattern.compile("^[A-z ]{3,20}$"));
        map.put(companyTxt, Pattern.compile("^[A-z]{4,15}$"));
        map.put(NicTxt, Pattern.compile("^([0-9]{12}|[0-9]{12}v)$"));
        map.put(ContactTxt, Pattern.compile("^(07([1245678])|091)(-)[0-9]{7}$"));
    }

    /* load all supplier details */
    private void loadAllSuppliers() throws SQLException, ClassNotFoundException {
       /* ResultSet result = crudUtil.execute("SELECT * FROM supplier");
        ObservableList<SupplierDTO> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new SupplierDTO(
                            result.getString("supId"),
                            result.getString("name"),
                            result.getString("nic"),
                            result.getString("address"),
                            result.getString("telNo"),
                            result.getString("companyName")
                    ));
        }
        SupplierTable.setItems(obList);
        SupplierTable.refresh();*/
        SupplierTable.getItems().clear();
        try {
            ArrayList<SupplierDTO> allSuppliers = supplierBO.getAll();

            for (SupplierDTO s : allSuppliers) {
                SupplierTable.getItems().add(new SupplierTM(
                        s.getSupId(),
                        s.getName(),
                        s.getNic(),
                        s.getAddress(),
                        s.getTelNo(),
                        s.getCompanyName()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        SupplierTable.refresh();
    }

    /* save supplier details */
    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
      /*  String address = Address.getText();
        String company = companyTxt.getText();
        String contact = ContactTxt.getText();
        String nic = NicTxt.getText();
        String name = NameTxt.getText();
        String id = idTxt.getText();


        SupplierDTO supplier = new SupplierDTO(
                id,
                name,
                nic,
                address,
                contact,
                company
        );
        try {
            String sql = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?)";

            boolean isAdded = crudUtil.execute(sql,
                    supplier.getSupId(),
                    supplier.getName(),
                    supplier.getNic(),
                    supplier.getAddress(),
                    supplier.getTelNo(),
                    supplier.getCompanyName()
            );

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllSuppliers();
        clearText(); */
        try {

            supplierBO.add(new SupplierDTO(
                    idTxt.getText(),
                    NameTxt.getText(),
                    NicTxt.getText(),
                    Address.getText(),
                    ContactTxt.getText(),
                    companyTxt.getText()));

            SupplierTable.getItems().add(new SupplierTM(
                    idTxt.getText(),
                    NameTxt.getText(),
                    NicTxt.getText(),
                    Address.getText(),
                    ContactTxt.getText(),
                    companyTxt.getText()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadAllSuppliers();
        clearText();
    }

    /* clearing text fields */
    public void clearText() {
        Address.clear();
        companyTxt.clear();
        ContactTxt.clear();
        NicTxt.clear();
        NameTxt.clear();
        idTxt.clear();
    }

    /* update supplier details */
    public void UpdateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

       /* SupplierDTO supplier = new SupplierDTO(
                idTxt.getText(),
                NameTxt.getText(),
                NicTxt.getText(),
                Address.getText(),
                ContactTxt.getText(),
                companyTxt.getText()
        );

        boolean isUpdated = crudUtil.execute("UPDATE supplier SET name=? , nic=? , address=? , telNo=? , companyName=? WHERE supId=?",
                supplier.getName(),
                supplier.getNic(),
                supplier.getAddress(),
                supplier.getTelNo(),
                supplier.getCompanyName(),
                supplier.getSupId()
        );

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Details Updated !").show();
            clearText();
            loadAllSuppliers();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }*/
        try {
            supplierBO.update(new SupplierDTO(
                    idTxt.getText(),
                    NameTxt.getText(),
                    NicTxt.getText(),
                    Address.getText(),
                    ContactTxt.getText(),
                    companyTxt.getText()));


        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSuppliers();
        clearText();
    }

    /* removing supplier details */
    public void RemoveBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
      /*  boolean isDeleted = crudUtil.execute("DELETE FROM supplier WHERE supId=?", idTxt.getText());

        if (isDeleted) {
            NotificationController.detailsRemoved();
            clearText();
            loadAllSuppliers();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }*/
        try {
            supplierBO.delete(idTxt.getText());

            SupplierTable.getItems().remove(SupplierTable.getSelectionModel().getSelectedItem());
            SupplierTable.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the supplier " + idTxt).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearText();
        loadAllSuppliers();
    }

    /* removing text fields animations  */
    public void textFields_Key_Released(KeyEvent keyEvent) {
        validationUtil.validate(map, (JFXButton) btnSave);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, (JFXButton) btnSave);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void save(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("AdminHomeForm", SupplierAnchorPane);

    }

    //do..................................................................................
    /* search supplier */
    public void searchOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = crudUtil.execute("SELECT * FROM supplier WHERE supId=?", idTxt.getText());

        if (resultSet.next()) {

            NameTxt.setText(resultSet.getString(2));
            NicTxt.setText(resultSet.getString(3));
            Address.setText(resultSet.getString(4));
            ContactTxt.setText(resultSet.getString(5));
            companyTxt.setText(resultSet.getString(6));

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            loadAllSuppliers();
            clearText();
        }
    }

    public void shiftToAddress(ActionEvent actionEvent) {
        Address.requestFocus();
    }

    public void shiftToCompany(ActionEvent actionEvent) {
        companyTxt.requestFocus();
    }

    public void shiftToContact(ActionEvent actionEvent) {
        ContactTxt.requestFocus();
    }

    public void shiftToSave(ActionEvent actionEvent) {
        btnSave.requestFocus();
    }

    public void shiftToNic(ActionEvent actionEvent) {
        NicTxt.requestFocus();
    }

    public void shiftToName(ActionEvent actionEvent) {
        NameTxt.requestFocus();
    }
}
