package lk.ijse.jewellery.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class OrderTM implements Comparable<OrderTM>{
    private String orderId;
    private String cusId;
    private String OrderDate;
    private String OrderTime;

    @Override
    public int compareTo(OrderTM o) {
        return orderId.compareTo(o.getOrderId());
    }
}
