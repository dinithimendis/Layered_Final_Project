package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.EmployeeBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.custom.EmployeeDAO;
import lk.ijse.jewellery.entity.Employee;
import lk.ijse.jewellery.model.EmployeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployees= new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee e : all) {
            allEmployees.add(new EmployeeDTO(e.getEmpId(),e.getName(),e.getNic(),e.getSalary(),e.getTelNo(),e.getAddress(), e.getJobRole()));
        }
        return allEmployees;
    }

    @Override
    public boolean add(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(
                dto.getEmpId(),
                dto.getName(),
                dto.getNic(),
                dto.getSalary(),
                dto.getTelNo(),
                dto.getAddress(),
                dto.getJobRole()));
    }

    @Override
    public boolean update(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
                dto.getEmpId(),
                dto.getName(),
                dto.getNic(),
                dto.getSalary(),
                dto.getTelNo(),
                dto.getAddress(),
                dto.getJobRole()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public ResultSet search(String text) throws SQLException, ClassNotFoundException{
        return employeeDAO.search(text);
    }

}
