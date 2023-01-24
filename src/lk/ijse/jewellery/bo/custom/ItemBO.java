package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.model.EmployeeDTO;
import lk.ijse.jewellery.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException;

    public boolean add(ItemDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean delete(String id) throws SQLException, ClassNotFoundException;
}
