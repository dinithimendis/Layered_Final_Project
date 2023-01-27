//complete***********************************************************

package lk.ijse.jewellery.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.bo.BOFactory;
import lk.ijse.jewellery.bo.custom.ItemBO;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.model.ItemDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.validationUtil;
import lk.ijse.jewellery.view.tm.ItemTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemFormController {
    public AnchorPane ManagerItemDetailContext;
    public JFXButton SaveButton;
    public TableView<ItemTM> FullTable;
    public TableColumn<ItemDTO, String> ItemTableCol;
    public TableColumn<ItemDTO, String> DescriptionCol;
    public TableColumn<ItemDTO, String> QtyCol;
    public TableColumn<ItemDTO, String> unitPriceCol;
    public TableColumn<ItemDTO, String> categoryCol;
    public TableColumn<ItemDTO, String> typeCol;
    public Label dateLabel;
    public Label TimeLabel;
    public TextField txtCategory;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQty;
    public TextField txtType;
    public TextField txtItemCode;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        ItemTableCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

            loadAllItems();


        SaveButton.setOnMouseClicked(event -> {
            try {
                saveOnAction();
            } catch (SQLException | ClassNotFoundException | NumberFormatException ignored) {
            }
        });

        map.put(txtItemCode, Pattern.compile("^(I00-)[0-9]{1,4}$"));
        map.put(txtUnitPrice, Pattern.compile("^([0-9]{2,6}.[0-9]{1,2})$"));
        map.put(txtDescription, Pattern.compile("^[A-z]{3,20}$"));
        map.put(txtType, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtCategory, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtQty, Pattern.compile("^[1-9 ]{1,20}$"));
    }

    /* save item */
    public void saveOnAction() throws SQLException, ClassNotFoundException {
        try {

            itemBO.add(new ItemDTO(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    txtCategory.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    txtType.getText()));

            FullTable.getItems().add(new ItemTM(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    txtCategory.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    txtType.getText()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadAllItems();
        clearTextField();
    }

    /* load all items */
    private void loadAllItems() throws SQLException, ClassNotFoundException {
        FullTable.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAll();

            for (ItemDTO e : allItems) {
                FullTable.getItems().add(new ItemTM(
                        e.getItemCode(),
                        e.getDescription(),
                        e.getCategory(),
                        e.getQty(),
                        e.getUnitPrice(),
                        e.getType()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        FullTable.refresh();
    }

    /* update item */
    public void UpdateButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            itemBO.update(new ItemDTO(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    txtCategory.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    txtType.getText()));


        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllItems();
        clearTextField();
    }

    /* delete item */
    public void DeleteBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            itemBO.delete(txtItemCode.getText());

            FullTable.getItems().remove(FullTable.getSelectionModel().getSelectedItem());
            FullTable.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + txtItemCode).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearTextField();
        loadAllItems();
    }

    /* clearing text fields */
    public void clearTextField() {
        txtCategory.clear();
        txtUnitPrice.clear();
        txtDescription.clear();
        txtQty.clear();
        txtType.clear();
        txtItemCode.clear();
    }

    /* search items */
    public void searchOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = itemBO.search(txtItemCode.getText());
        if (resultSet == null){
            new Alert(Alert.AlertType.ERROR, "Invalid Item Id").show();
        } else {
            if (resultSet.next()) {
                txtDescription.setText(resultSet.getString(2));
                txtCategory.setText(resultSet.getString(3));
                txtQty.setText(resultSet.getString(4));
                txtUnitPrice.setText(resultSet.getString(5));
                txtType.setText(resultSet.getString(6));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
                loadAllItems();
            }
        }
    }

    /* exit from item form */
    public void ExitBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("AdminHomeForm", ManagerItemDetailContext);
    }

    /* adding and removing text field effects */
    public void textFields_Key_Released_Item(KeyEvent keyEvent) {
        validationUtil.validate(map, SaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, SaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }


    public void save(ActionEvent actionEvent) {
    }


    //    requestFocus options -----------------------------------------------------------------------------------------
    public void shiftToQty(ActionEvent actionEvent) {
        txtQty.requestFocus();
    }

    public void shiftToDescription(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void shiftToType(ActionEvent actionEvent) {
        txtCategory.requestFocus();
    }

    public void shiftToSaveBtn(ActionEvent actionEvent) {
        SaveButton.requestFocus();
    }

    public void shiftToCategory(ActionEvent actionEvent) {
        txtCategory.requestFocus();
    }

    public void shiftToUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

}
