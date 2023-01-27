package lk.ijse.jewellery.dao.custom.impl;

import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.EmployeeDAO;
import lk.ijse.jewellery.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployees = new ArrayList<>();
        ResultSet rst = crudUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("empId"), rst.getString("name"), rst.getString("nic"),rst.getDouble("salary"),rst.getString("telNo"),rst.getString("address"),rst.getString("jobRole"));
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute(" INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?)", entity.getEmpId(), entity.getName(), entity.getNic(), entity.getSalary(), entity.getTelNo(), entity.getAddress(), entity.getJobRole());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("UPDATE employee SET name=? , nic=? , salary=? , telNo=? , address=? , jobRole=? WHERE empId=?", entity.getName(), entity.getNic(), entity.getSalary(), entity.getTelNo(), entity.getAddress(), entity.getJobRole(), entity.getEmpId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("DELETE FROM employee WHERE empId=?", id);
    }
    @Override
    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return crudUtil.execute("SELECT * FROM employee WHERE empId=?", id);
    }
}
