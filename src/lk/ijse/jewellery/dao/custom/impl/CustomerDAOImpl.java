package lk.ijse.jewellery.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.CustomerDAO;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.model.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> loadAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet result = crudUtil.execute("SELECT * FROM customer");

        while (result.next()) {
            Customer customer = new Customer(result.getString("cusId"), result.getString("title"), result.getString("cusName"), result.getString("address"), result.getString("telNo"), result.getString("province"), result.getString("nic"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean loadAllCustomers(Customer entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?)", entity.getCusId(), entity.getTitle(), entity.getCusName(), entity.getAddress(), entity.getTelNo(), entity.getProvince(), entity.getNic());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("UPDATE customer SET title=? , cusName=? , address=? , telNo=? , province=? , nic=? WHERE cusId=?", entity.getTitle(), entity.getCusName(), entity.getAddress(), entity.getTelNo(), entity.getProvince(), entity.getNic(), entity.getCusId());
    }

    @Override
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("DELETE FROM customer WHERE cusId=?", cusId);
    }
}
