package lk.ijse.jewellery.dao.custom.impl;

import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.OrderDAO;
import lk.ijse.jewellery.entity.Employee;
import lk.ijse.jewellery.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList<>();
        ResultSet rst = crudUtil.execute("SELECT * FROM `order`");
        while (rst.next()) {
            Order order = new Order(rst.getString("orderId"), rst.getString("cusId"), rst.getString("orderDate"),rst.getString("orderTime"));
            allOrders.add(order);
        }
        return allOrders;
    }

    @Override
    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("SELECT * FROM order WHERE orderId=?", id);
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
