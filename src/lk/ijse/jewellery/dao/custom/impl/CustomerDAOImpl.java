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
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet result = crudUtil.execute("SELECT * FROM customer");

        while (result.next()) {
            Customer customer = new Customer(result.getString("cusId"), result.getString("title"), result.getString("cusName"), result.getString("address"), result.getString("telNo"), result.getString("province"), result.getString("nic"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
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

  /*  @Override
    public Customer search(String cusId) throws SQLException, ClassNotFoundException {
        ResultSet rst = crudUtil.execute("SELECT * FROM Customer WHERE id=?", cusId + "");
        rst.next();
        return new Customer(cusId + "",rst.getString("title"), rst.getString("name"), rst.getString("address"), rst.getString("telNo"), rst.getString("province"), rst.getString("nic") );
    }*/
  @Override
  public ResultSet search(String id) throws SQLException, ClassNotFoundException {
      return crudUtil.execute("SELECT * FROM customer WHERE cusId=?", id);
  }
    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = crudUtil.execute("SELECT * FROM customer");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }

    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = crudUtil.execute("SELECT * FROM customer WHERE cusId=?", id);
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }
}
