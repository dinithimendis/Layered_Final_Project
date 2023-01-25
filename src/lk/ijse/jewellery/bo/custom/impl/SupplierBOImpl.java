package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.ItemBO;
import lk.ijse.jewellery.bo.custom.SupplierBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.custom.ItemDAO;
import lk.ijse.jewellery.dao.custom.SupplierDAO;
import lk.ijse.jewellery.entity.Item;
import lk.ijse.jewellery.entity.Supplier;
import lk.ijse.jewellery.model.ItemDTO;
import lk.ijse.jewellery.model.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSuppliers= new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();
        for (Supplier s : all) {
            allSuppliers.add(new SupplierDTO(
                    s.getSupId(),
                    s.getName(),
                    s.getNic(),
                    s.getAddress(),
                    s.getTelNo(),
                    s.getCompanyName()));
        }
        return allSuppliers;
    }

    @Override
    public boolean add(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(
                dto.getSupId(),
                dto.getName(),
                dto.getNic(),
                dto.getAddress(),
                dto.getTelNo(),
                dto.getCompanyName()));
    }

    @Override
    public boolean update(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(
                dto.getSupId(),
                dto.getName(),
                dto.getNic(),
                dto.getAddress(),
                dto.getTelNo(),
                dto.getCompanyName()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }
}
