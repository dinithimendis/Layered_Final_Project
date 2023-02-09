package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.DailyIncomeReportsBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.custom.DailyIncomeReportsDAO;
import lk.ijse.jewellery.entity.DailyIncomeReports;
import lk.ijse.jewellery.model.DailyIncomeReportsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeReportsBOImpl implements DailyIncomeReportsBO {
    DailyIncomeReportsDAO incomeReportsDAO = (DailyIncomeReportsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INCOME_REPORTS);

    @Override
    public ArrayList<DailyIncomeReportsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<DailyIncomeReportsDTO> allReports= new ArrayList<>();
        ArrayList<DailyIncomeReports> all = incomeReportsDAO.getAll();
        for (DailyIncomeReports c : all) {
            allReports.add(new DailyIncomeReportsDTO(c.getDate(), c.getNumberOfOrders(),c.getNumberOfSoldItem(),c.getFinalCost()));
        }
        return allReports;
    }
}
