package lk.ijse.jewellery.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class SupplierTM implements Comparable<SupplierTM>{
    private String supId;
    private String name;
    private String nic;
    private String address;
    private String telNo;
    private String companyName;

    @Override
    public int compareTo(SupplierTM o) {
        return supId.compareTo(o.getSupId());
    }

}
