package lk.ijse.jewellery.dao;

import lk.ijse.jewellery.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    public boolean add(T entity) throws SQLException, ClassNotFoundException;
    public boolean update(T entity) throws SQLException, ClassNotFoundException;
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException;

    ResultSet search(String id) throws SQLException, ClassNotFoundException;
   // public T search(String cusId) throws SQLException, ClassNotFoundException;
}
