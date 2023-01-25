package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.ItemBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.custom.ItemDAO;
import lk.ijse.jewellery.entity.Item;
import lk.ijse.jewellery.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems= new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item i : all) {
            allItems.add(new ItemDTO(
                    i.getItemCode(),
                    i.getDescription(),
                    i.getCategory(),
                    i.getQty(),
                    i.getUnitPrice(),
                    i.getType()));
        }
        return allItems;
    }

    @Override
    public boolean add(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(
                dto.getItemCode(),
                dto.getDescription(),
                dto.getCategory(),
                dto.getQty(),
                dto.getUnitPrice(),
                dto.getType()));
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(
                dto.getItemCode(),
                dto.getDescription(),
                dto.getCategory(),
                dto.getQty(),
                dto.getUnitPrice(),
                dto.getType()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }
}
