package lk.ijse.jewellery.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class OrderDetailsTM implements Comparable<OrderDetailsTM>{
    private String orderId;
    private String itemCode;
    private double OrderQty;
    private double totalAmount;
    private double discount;
    @Override
    public int compareTo(OrderDetailsTM o) {
        return orderId.compareTo(o.getOrderId());
    }
}
