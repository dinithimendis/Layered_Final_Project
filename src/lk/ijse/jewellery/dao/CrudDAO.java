package lk.ijse.jewellery.dao;

import lk.ijse.jewellery.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    public ArrayList<T> loadAllCustomers() throws SQLException, ClassNotFoundException;
    public boolean loadAllCustomers(T entity) throws SQLException, ClassNotFoundException;
    public boolean update(T entity) throws SQLException, ClassNotFoundException;
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException;
}
