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
        CUSTOMER,EMPLOYEE,ITEM,SUPPLIER,PO
        //,IR
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
           /* case IR:
                return new IncomeReportsBOImpl();*/
            default:
                return null;
        }
    }
}
