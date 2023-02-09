package lk.ijse.jewellery.bo;

import lk.ijse.jewellery.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){

        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,EMPLOYEE,ITEM,SUPPLIER,PO,INCOME_REPORT,MONTHLY_REPORT
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
           case PO:
               return new PlaceOrderBOImpl();
            case INCOME_REPORT:
                return new DailyIncomeReportsBOImpl();
            case MONTHLY_REPORT:
                return new MonthlyReportBOImpl();
            default:
                return null;
        }
    }
}
