package lk.ijse.jewellery.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class DailyIncomeReportsDTO {
    private String date;
    private int numberOfOrders;
    private int numberOfSoldItem;
    private double finalCost;

    public DailyIncomeReportsDTO(String date, int numberOfSoldItem) {
        this.date = date;
        this.numberOfSoldItem = numberOfSoldItem;
    }

}
