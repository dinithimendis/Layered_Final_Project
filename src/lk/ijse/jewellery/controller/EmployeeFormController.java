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
import lk.ijse.jewellery.bo.custom.EmployeeBO;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.model.EmployeeDTO;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.validationUtil;
import lk.ijse.jewellery.view.tm.EmployeeTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public TableView<EmployeeTM> EmployeeTable;
    public AnchorPane EmployeeAnchorPane;
    public Button btnSaveEmployee;
    public TextField txtContact;
    public TextField txtNic;
    public TextField txtName;
    public TextField txtId;
    public TextField txtAddress;
    public TextField txtJobRole;
    public TextField salary;

    public TableColumn<EmployeeDTO, String> EmpID;
    public TableColumn<EmployeeDTO, String> employeeNameCol;
    public TableColumn<EmployeeDTO, String> nicCol;
    public TableColumn<EmployeeDTO, String> ContactCol;
    public TableColumn<EmployeeDTO, String> roleCol;
    public TableColumn<EmployeeDTO, String> addressCol;
    public TableColumn<EmployeeDTO, String> salaryCol;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() {
        EmpID.setCellValueFactory(new PropertyValueFactory<>("empId"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ContactCol.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("jobRole"));

        try {
            loadAllEmployee();
        } catch (SQLException | ClassNotFoundException ignored) {
        }



        btnSaveEmployee.setOnMouseClicked(event -> {
            try {

                //TODO check this = numberFormatException
                saveOnAction();

            } catch (SQLException | ClassNotFoundException | NumberFormatException ignored) {


            //} catch (SQLException | ClassNotFoundException ignored) {

            }
        });

        map.put(txtId, Pattern.compile("^(E00-)[0-9]{1,5}$"));
        map.put(txtAddress, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtName, Pattern.compile("^[A-z ]{3,20}$"));
        map.put(txtJobRole, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtNic, Pattern.compile("^([0-9]{12}|[0-9]{12}v)$"));
        map.put(salary, Pattern.compile("^([0-9]{2,6}.[0-9]{1,2})$"));
        map.put(txtContact, Pattern.compile("^(07([1245678])|091)(-)[0-9]{7}$"));

    }

    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        EmployeeTable.getItems().clear();
        try {
            ArrayList<EmployeeDTO> allEmployees = employeeBO.getAll();

            for (EmployeeDTO e : allEmployees) {
                EmployeeTable.getItems().add(new EmployeeTM(
                        e.getEmpId(), e.getName(),
                        e.getNic(), e.getSalary(),
                        e.getTelNo(), e.getAddress(),
                        e.getJobRole()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        EmployeeTable.refresh();
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        EmployeeDTO employee = new EmployeeDTO(
                txtId.getText(),
                txtName.getText(),
                txtNic.getText(),
                Double.parseDouble(salary.getText()),
                txtContact.getText(),
                txtAddress.getText(),
                txtJobRole.getText()
        );

        try {
            employeeBO.update(new EmployeeDTO(txtId.getText(), txtName.getText(), txtNic.getText(), Double.parseDouble(salary.getText()), txtContact.getText(), txtAddress.getText(), txtJobRole.getText()));

            EmployeeTable.refresh();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the employee " + txtId + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadAllEmployee();
        clearText();
    }

    public void RemoveBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            employeeBO.delete(txtId.getText());

            EmployeeTable.getItems().remove(EmployeeTable.getSelectionModel().getSelectedItem());
            EmployeeTable.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the employee " + txtId).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearText();
        loadAllEmployee();
    }

    public void saveOnAction() throws SQLException, ClassNotFoundException {
        try {

            employeeBO.add(new EmployeeDTO(
                    txtId.getText(),
                    txtName.getText(),
                    txtNic.getText(),
                    Double.parseDouble(salary.getText()),
                    txtContact.getText(),
                    txtAddress.getText(),
                    txtJobRole.getText()));

            EmployeeTable.getItems().add(new EmployeeTM(
                    txtId.getText(),
                    txtName.getText(),
                    txtNic.getText(),
                    Double.parseDouble(salary.getText()),
                    txtContact.getText(),
                    txtAddress.getText(),
                    txtJobRole.getText()));

        } catch (SQLException | ClassNotFoundException ignored){

        }
        clearText();
        loadAllEmployee();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("AdminHomeForm", EmployeeAnchorPane);
    }

    public void employeeSearch(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = employeeBO.search(txtId.getText());
        if (resultSet == null){
            new Alert(Alert.AlertType.ERROR, "Invalid Item Id").show();
        } else {

        if (resultSet.next()) {
            txtName.setText(resultSet.getString(2));
            txtNic.setText(resultSet.getString(3));
            salary.setText(resultSet.getString(4));
            txtContact.setText(resultSet.getString(5));
            txtAddress.setText(resultSet.getString(6));
            txtJobRole.setText(resultSet.getString(7));


        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            loadAllEmployee();
        }
        }
    }

    public void clearText() {
        txtContact.clear();
        txtNic.clear();
        txtName.clear();
        txtId.clear();
        txtAddress.clear();
        txtJobRole.clear();
        salary.clear();
    }

    public void save(ActionEvent actionEvent) {
    }


    public void textFields_Key_Released(KeyEvent keyEvent) {
        validationUtil.validate(map, (JFXButton) btnSaveEmployee);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, (JFXButton) btnSaveEmployee);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void shiftToContact(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void shiftToNic(ActionEvent actionEvent) {
        txtNic.requestFocus();
    }

    public void shiftToName(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void shiftToAddress(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void shiftToJobRole(ActionEvent actionEvent) {
        txtJobRole.requestFocus();
    }

    public void shiftToSave(ActionEvent actionEvent) {
        btnSaveEmployee.requestFocus();
    }

    public void shiftToSal(ActionEvent actionEvent) {
        salary.requestFocus();
    }
}
