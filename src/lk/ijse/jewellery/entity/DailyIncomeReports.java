package lk.ijse.jewellery.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class DailyIncomeReports {
    private String date;
    private int numberOfOrders;
    private int numberOfSoldItem;
    private double finalCost;

    public DailyIncomeReports(String date, int numberOfSoldItem) {
        this.date = date;
        this.numberOfSoldItem = numberOfSoldItem;
    }
}
