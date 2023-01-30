package lk.ijse.jewellery.dao.custom;

import lk.ijse.jewellery.dao.CrudDAO;
import lk.ijse.jewellery.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {

    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    Customer getCustomer(String id) throws SQLException, ClassNotFoundException;

}
