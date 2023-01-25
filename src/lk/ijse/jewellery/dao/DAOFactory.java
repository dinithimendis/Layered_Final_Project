package lk.ijse.jewellery.dao;

import lk.ijse.jewellery.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.jewellery.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.jewellery.dao.custom.impl.ItemDAOImpl;
import lk.ijse.jewellery.dao.custom.impl.SupplierDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,EMPLOYEE,ITEM,SUPPLIER
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            default:
                return null;
        }
    }

}
