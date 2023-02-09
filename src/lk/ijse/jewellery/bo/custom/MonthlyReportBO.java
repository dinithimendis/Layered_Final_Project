package lk.ijse.jewellery.bo.custom;

import lk.ijse.jewellery.bo.SuperBO;
import lk.ijse.jewellery.model.DailyIncomeReportsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MonthlyReportBO extends SuperBO {
    public ArrayList<DailyIncomeReportsDTO> getAll() throws SQLException, ClassNotFoundException;
}
