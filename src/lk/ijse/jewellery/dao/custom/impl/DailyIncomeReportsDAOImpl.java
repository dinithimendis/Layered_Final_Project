package lk.ijse.jewellery.dao.custom.impl;

import lk.ijse.jewellery.dao.crudUtil;
import lk.ijse.jewellery.dao.custom.DailyIncomeReportsDAO;
import lk.ijse.jewellery.entity.DailyIncomeReports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeReportsDAOImpl implements DailyIncomeReportsDAO {

    @Override
    public ArrayList<DailyIncomeReports> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<DailyIncomeReports> allReports = new ArrayList<>();
        ResultSet result = crudUtil.execute("SELECT `order`.OrderDate,count(`order`.orderId),sum(orderDetails.totalAmount) " +
                "FROM `order` INNER JOIN orderDetails ON `order`.orderId = orderDetails.orderId " +
                "GROUP BY OrderDate");

        while (result.next()) {
            DailyIncomeReports incomeReports = new DailyIncomeReports(result.getString(1), result.getInt(2),
                    result.getInt(3), result.getDouble(4));
            allReports.add(incomeReports);
        }
        return allReports;
    }
    @Override
    public boolean add(DailyIncomeReports entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(DailyIncomeReports entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public DailyIncomeReports searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
