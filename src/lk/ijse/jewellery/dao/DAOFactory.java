package lk.ijse.jewellery.dao;

import lk.ijse.jewellery.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,EMPLOYEE,ITEM,SUPPLIER,ORDER,ORDER_DETAILS,INCOME_REPORTS,MONTHLY_REPORTS
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
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOImpl();
            case INCOME_REPORTS:
                return new DailyIncomeReportsDAOImpl();
            case MONTHLY_REPORTS:
                return new MonthlyReportDAOImpl();
            default:
                return null;
        }
    }

}
