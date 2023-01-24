package lk.ijse.jewellery.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Item {
    private String itemCode;
    private String description;
    private String category;
    private int qty;
    private double unitPrice;
    private String type;
}
