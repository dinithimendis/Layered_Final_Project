package lk.ijse.jewellery.bo.custom.impl;

import lk.ijse.jewellery.bo.custom.DailyIncomeReportsBO;
import lk.ijse.jewellery.bo.custom.MonthlyReportBO;
import lk.ijse.jewellery.dao.DAOFactory;
import lk.ijse.jewellery.dao.custom.DailyIncomeReportsDAO;
import lk.ijse.jewellery.dao.custom.MonthlyReportDAO;
import lk.ijse.jewellery.entity.DailyIncomeReports;
import lk.ijse.jewellery.model.DailyIncomeReportsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyReportBOImpl implements MonthlyReportBO {
    MonthlyReportDAO monthlyReportDAO = (MonthlyReportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MONTHLY_REPORTS);

    @Override
    public ArrayList<DailyIncomeReportsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<DailyIncomeReportsDTO> allReports= new ArrayList<>();
        ArrayList<DailyIncomeReports> all = monthlyReportDAO.getAll();
        for (DailyIncomeReports c : all) {
            allReports.add(new DailyIncomeReportsDTO(c.getDate(), c.getNumberOfOrders(),c.getNumberOfSoldItem(),c.getFinalCost()));
        }
        return allReports;
    }
}
