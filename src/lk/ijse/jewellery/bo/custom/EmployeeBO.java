package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

        public ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException;

        public boolean add(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;

        public boolean update(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;

        public boolean delete(String id) throws SQLException, ClassNotFoundException;

}
