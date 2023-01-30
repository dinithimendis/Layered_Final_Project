package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.PlaceOrderBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.custom.*;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.entity.Employee;
import lk.ijse.jewellery.entity.Order;
import lk.ijse.jewellery.entity.OrderDetails;
import lk.ijse.jewellery.model.EmployeeDTO;
import lk.ijse.jewellery.model.OrderDTO;
import lk.ijse.jewellery.model.OrderDetailsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO= (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO= (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);



   // OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    //OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allOrders= new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll();
        for (Order o : all) {
            allOrders.add(new OrderDTO(o.getOrderId(),o.getCusId(),o.getOrderDate(),o.getOrderTime()));
        }
        return allOrders;
    }
    @Override
    public ResultSet searchOrders(String text) throws SQLException, ClassNotFoundException{
        return orderDAO.search(text);
    }
    @Override
    public ArrayList<OrderDetailsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> allOrderDetails= new ArrayList<>();
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll();
        for (OrderDetails od : all) {
            allOrderDetails.add(new OrderDetailsDTO(od.getOrderId(),od.getItemCode(),od.getOrderQty(),od.getTotalAmount(),od.getDiscount()));
        }
        return allOrderDetails;
    }
    @Override
    public ResultSet search(String text) throws SQLException, ClassNotFoundException{
        return orderDetailsDAO.search(text);
    }

    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomer(id);
    }
   @Override
   public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
       return customerDAO.getCustomerIds();
   }
}
