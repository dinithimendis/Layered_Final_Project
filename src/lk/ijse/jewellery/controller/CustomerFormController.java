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
import lk.ijse.jewellery.bo.custom.CustomerBO;
import lk.ijse.jewellery.model.CustomerDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.util.validationUtil;
import lk.ijse.jewellery.view.tm.CartTM;
import lk.ijse.jewellery.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.name;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.title;

public class CustomerFormController {
    public JFXButton CustomerSaveButton;
    public TableView<CustomerTM> TableContextFull;
    public Label DateLbl;
    public Label TimeLbl;
    public AnchorPane CustomerAnchorPane;
    public TextField txtCustomerID;
    public TextField txtCustomerProvince;
    public TextField txtCustomerName;
    public TextField txtTelNo;
    public TextField txtMrMrs;
    public TextField txtCustomerAddress;
    public TextField txtNic;
    public TableColumn<CustomerDTO, String> id;
    public TableColumn<CustomerDTO, String> mrMrs;
    public TableColumn<CustomerDTO, String> Address;
    public TableColumn<CustomerDTO, String> TelNo;
    public TableColumn<CustomerDTO, String> Province;
    public TableColumn<CustomerDTO, String> NIC;
    public TableColumn<CustomerDTO, String> colCustomer_Name;
    public Label customerIdLbl;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        mrMrs.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCustomer_Name.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        TelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        Province.setCellValueFactory(new PropertyValueFactory<>("province"));
        NIC.setCellValueFactory(new PropertyValueFactory<>("nic"));

        /* load all customers in to the table */
       // try {
            loadAllCustomers();
      //  } catch (SQLException | ClassNotFoundException ignored) {
       // }

        CustomerSaveButton.setOnMouseClicked(event -> {
            try {
                btnSaveOnAction();
            } catch (SQLException | ClassNotFoundException ignored) {
            }
        });


        /* regular expression validator */
        map.put(txtCustomerID, Pattern.compile("^(C00-)[0-9]{1,4}$"));
        map.put(txtMrMrs, Pattern.compile("^(mr|mrs)$"));
        map.put(txtCustomerName, Pattern.compile("^[A-z ]{3,20}$"));
        map.put(txtCustomerProvince, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtTelNo, Pattern.compile("^(07([1245678])|091)(-)[0-9]{7}$"));
        map.put(txtCustomerAddress, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtNic, Pattern.compile("^([0-9]{12}|[0-9]{12}v)$"));

    }

    /* managing all TextField's like removing and adding errors   */
    public void textFields_Key_Released_Customer(KeyEvent keyEvent) {
        validationUtil.validate(map, CustomerSaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, CustomerSaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    /* load all customer details in to the customerForm ui - > table */
    private void loadAllCustomers()  {
        TableContextFull.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();

            for (CustomerDTO c : allCustomers) {
                TableContextFull.getItems().add(new CustomerTM(c.getCusId(),c.getTitle(), c.getCusName(), c.getAddress(), c.getTelNo(), c.getProvince(), c.getNic()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        TableContextFull.refresh();

      //TableContextFull.setItems();

    }

    /* save customer */
    public void btnSaveOnAction() throws SQLException, ClassNotFoundException {
        String id = txtCustomerID.getText();
        String title = txtMrMrs.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String telNo = txtTelNo.getText();
        String province = txtCustomerProvince.getText();
        String nic = txtNic.getText();


       // CustomerDTO customer = new CustomerDTO(id, title, name, address, telNo, province, nic);
       // String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?)";

    // customerBO.addCustomer(new CustomerDTO(id, title, name, address, telNo, province, nic));

      //  TableContextFull.getItems().add(new CustomerTM(id, title, name, address, telNo, province, nic));

       try {
            /* boolean isAdded = crudUtil.execute(sql,
                    customer.getCusId(),
                    customer.getTitle(),
                    customer.getCusName(),
                    customer.getAddress(),
                    customer.getTelNo(),
                    customer.getProvince(),
                    customer.getNic()
            );*/
           customerBO.add(new CustomerDTO(id, title, name, address, telNo, province, nic));

           TableContextFull.getItems().add(new CustomerTM(id, title, name, address, telNo, province, nic));

       } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }

            /*if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        loadAllCustomers();
        clearText();
    }

    /* update customer */
    public void UpdateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
try {
        customerBO.updateCustomer(new CustomerDTO(id,title,name,address,telNo,province,nic));

    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    CustomerTM selectedCustomer = TableContextFull.getSelectionModel().getSelectedItem();
            selectedCustomer.setTitle(String.valueOf(title));
            selectedCustomer.setCusName(String.valueOf(name));
            selectedCustomer.setAddress();
            selectedCustomer.setTelNo(telNo);
            selectedCustomer.setProvince(province);
            selectedCustomer.setNic(nic);
       /* CustomerDTO customer = new CustomerDTO(
                txtCustomerID.getText(),
                txtMrMrs.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtTelNo.getText(),
                txtCustomerProvince.getText(),
                txtNic.getText()
        );*/

       /* boolean isUpdated = crudUtil.execute("UPDATE customer SET title=? , cusName=? , address=? , telNo=? , province=? , nic=? WHERE cusId=?",
                customer.getTitle(),
                customer.getCusName(),
                customer.getAddress(),
                customer.getTelNo(),
                customer.getProvince(),
                customer.getNic(),
                customer.getCusId()
        );*/

       // if (isUpdated) {
         //   new Alert(Alert.AlertType.CONFIRMATION, "Customer Details Updated !").show();
            clearText();
            loadAllCustomers();
        //} else {
          //  new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
       // }

    }

    /* delete customer */
    public void DeleteBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        /*boolean isDeleted =crudUtil.execute("DELETE FROM customer WHERE cusId=?", txtCustomerID.getText());

        if (isDeleted) {
            clearText();
            loadAllCustomers();
            NotificationController.detailsRemoved();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }*/
        try {
            customerBO.deleteCustomer(cusId);

            TableContextFull.getItems().remove(TableContextFull.getSelectionModel().getSelectedItem());
            TableContextFull.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }


    /* type id and search customer using clicking search image */
    public void searchOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        customerIdLbl.setDisable(false);
        ResultSet resultSet = crudUtil.execute("SELECT * FROM customer WHERE cusId=?", txtCustomerID.getText());

        if (resultSet.next()) {
            txtMrMrs.setText(resultSet.getString(2));
            txtCustomerName.setText(resultSet.getString(3));
            txtCustomerAddress.setText(resultSet.getString(4));
            txtTelNo.setText(resultSet.getString(5));
            txtCustomerProvince.setText(resultSet.getString(6));
            txtNic.setText(resultSet.getString(7));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            loadAllCustomers();
        }
    }

    /* if you want to clear the text fields please use this... */
    private void clearText() {
        txtCustomerID.clear();
        txtCustomerProvince.clear();
        txtCustomerName.clear();
        txtTelNo.clear();
        txtMrMrs.clear();
        txtCustomerAddress.clear();
        txtNic.clear();
    }

    public void save(ActionEvent actionEvent) {
    }

    /* ui management ---------------------------------------------------------------------- */
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", CustomerAnchorPane);
    }

    public void detailsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("CustomerDetailsForm", CustomerAnchorPane);
    }
    /* ui management closed --------------------------------------------------------------- */


    /* type data and press enter to shift next text field -------------------------------- */

    public void shiftToName(ActionEvent actionEvent) {
//        txtCustomerName.requestFocus();
    }

    public void shiftToProvince(ActionEvent actionEvent) {
//        txtCustomerProvince.requestFocus();
    }

    public void shiftToTelNo(ActionEvent actionEvent) {
//        txtTelNo.requestFocus();
    }

    public void shiftToAddress(ActionEvent actionEvent) {
//        txtCustomerAddress.requestFocus();
    }

    public void shiftToNic(ActionEvent actionEvent) {
//        txtNic.requestFocus();
    }

    public void shiftToSave(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        btnSaveOnAction();
    }

    public static ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT cusId FROM customer");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()) {
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static CustomerDTO getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM customer WHERE cusId=?", id);
        if (result.next()) {
            return new CustomerDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7)
            );
        }
        return null;
    }


}
