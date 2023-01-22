package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public boolean add(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteCustomer(String cusId) throws SQLException, ClassNotFoundException;

}
