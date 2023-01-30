package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.model.EmployeeDTO;
import lk.ijse.jewellery.model.OrderDTO;
import lk.ijse.jewellery.model.OrderDetailsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {

    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    ResultSet searchOrders(String text) throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDetailsDTO> getAll() throws SQLException, ClassNotFoundException;

    ResultSet search(String text) throws SQLException, ClassNotFoundException;

    Customer getCustomer(String id) throws SQLException, ClassNotFoundException;
    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;
}
