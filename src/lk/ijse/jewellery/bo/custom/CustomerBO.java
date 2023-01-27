package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.model.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException;

    public boolean add(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean update(CustomerDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean delete(String cusId) throws SQLException, ClassNotFoundException;

    ResultSet search(String text) throws SQLException, ClassNotFoundException;
    //public Customer searchCustomer(String cusId) throws SQLException, ClassNotFoundException;

}
