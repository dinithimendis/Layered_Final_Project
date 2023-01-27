package lk.ijse.jewellery.dao.custom.impl;

import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.EmployeeDAO;
import lk.ijse.jewellery.dao.custom.ItemDAO;
import lk.ijse.jewellery.entity.Employee;
import lk.ijse.jewellery.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = crudUtil.execute("SELECT * FROM item");
        while (rst.next()) {
            Item item = new Item(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getString("category"),
                    Integer.parseInt(rst.getString("qty")),
                    Double.parseDouble(rst.getString("unitPrice")),
                    rst.getString("type"));
            allItems.add(item);
        }
        return allItems;
    }

    @Override
    public boolean add(Item entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute(" INSERT INTO item VALUES (?, ?, ?, ?, ?, ?)",
                entity.getItemCode(), entity.getDescription(), entity.getCategory(), entity.getQty(), entity.getUnitPrice(), entity.getType());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute(
                "UPDATE item SET description=? , category=? , qty=? , unitPrice=? , type=? WHERE itemCode=?",
                 entity.getDescription(), entity.getCategory(), entity.getQty(), entity.getUnitPrice(), entity.getType(),entity.getItemCode());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("DELETE FROM item WHERE itemCode=?", id);
    }
    @Override
    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("SELECT * FROM item WHERE itemCode=?", id);
    }
}
