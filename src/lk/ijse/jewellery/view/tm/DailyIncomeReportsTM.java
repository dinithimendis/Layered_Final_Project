package lk.ijse.jewellery.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class DailyIncomeReportsTM implements Comparable<DailyIncomeReportsTM>{
    private String date;
    private int numberOfOrders;
    private int numberOfSoldItem;
    private double finalCost;

    public DailyIncomeReportsTM(String date, int numberOfSoldItem) {
        this.date = date;
        this.numberOfSoldItem = numberOfSoldItem;
    }
    @Override
    public int compareTo(DailyIncomeReportsTM o) {
        return date.compareTo(o.getDate());
    }

}
