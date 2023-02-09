package lk.ijse.jewellery.dao.custom.impl;

import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.OrderDetailsDAO;
import lk.ijse.jewellery.entity.Order;
import lk.ijse.jewellery.entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allOrderDetails = new ArrayList<>();
        ResultSet rst = crudUtil.execute("SELECT * FROM `orderDetails`");
        while (rst.next()) {
            OrderDetails orderDetails = new OrderDetails(
                    rst.getString("orderId"),
                    rst.getString("itemCode"),
                    rst.getDouble("OrderQty"),
                    rst.getDouble("totalAmount"),
                    rst.getDouble("discount"));
            allOrderDetails.add(orderDetails);
        }
        return allOrderDetails;
    }

    @Override
    public boolean add(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("SELECT * FROM orderDetails WHERE orderId=?", id);
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetails searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


}
