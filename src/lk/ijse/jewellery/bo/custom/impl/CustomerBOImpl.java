package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.CustomerBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.CustomerDAO;
import lk.ijse.jewellery.entity.Customer;
import lk.ijse.jewellery.model.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCusId(), c.getTitle(),c.getCusName(), c.getAddress(),c.getTelNo(),c.getProvince(),c.getNic()));
        }
        return allCustomers;
    }

    @Override
    public boolean add(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(
                dto.getCusId(),
                dto.getTitle(),
                dto.getCusName(),
                dto.getAddress(),
                dto.getTelNo(),
                dto.getProvince(),
                dto.getNic()));
    }
    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCusId(), dto.getTitle(),dto.getCusName(), dto.getAddress(),dto.getTelNo(),dto.getProvince(),dto.getNic()));
    }

    @Override
    public boolean deleteCustomer(String cusId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(cusId);
    }
   /* public Customer searchCustomer(String cusId) throws SQLException, ClassNotFoundException {
        return new customerDAO.search(cusId);
    }*/

}
