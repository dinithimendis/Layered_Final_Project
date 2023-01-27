package lk.ijse.jewellery.dao.custom.impl;

import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.SupplierDAO;
import lk.ijse.jewellery.entity.Item;
import lk.ijse.jewellery.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> allSuppliers = new ArrayList<>();
        ResultSet rst = crudUtil.execute("SELECT * FROM supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(
                    rst.getString("supId"),
                    rst.getString("name"),
                    rst.getString("nic"),
                    rst.getString("address"),
                    rst.getString("telNo"),
                    rst.getString("companyName"));
            allSuppliers.add(supplier);
        }
        return allSuppliers;
    }

    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute(" INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?)",
                entity.getSupId(), entity.getName(), entity.getNic(), entity.getAddress(), entity.getTelNo(), entity.getCompanyName());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute(
                "UPDATE supplier SET name=? , nic=? , address=? , telNo=? , companyName=? WHERE supId=?",
                entity.getName(), entity.getNic(), entity.getAddress(), entity.getTelNo(), entity.getCompanyName(),entity.getSupId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("DELETE FROM supplier WHERE supId=?", id);
    }

    @Override
    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("SELECT * FROM supplier WHERE supId=?", id);
    }
}
