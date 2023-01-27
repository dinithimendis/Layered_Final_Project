package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.model.ItemDTO;
import lk.ijse.jewellery.model.SupplierDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    public ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException;

    public boolean add(SupplierDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean update(SupplierDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    ResultSet search(String text) throws SQLException, ClassNotFoundException;
}
