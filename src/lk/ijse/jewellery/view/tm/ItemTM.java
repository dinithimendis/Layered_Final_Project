package lk.ijse.jewellery.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class ItemTM implements Comparable<ItemTM>{
    private String itemCode;
    private String description;
    private String category;
    private int qty;
    private double unitPrice;
    private String type;

    @Override
    public int compareTo(ItemTM o) {
        return itemCode.compareTo(o.getItemCode());
    }
}
