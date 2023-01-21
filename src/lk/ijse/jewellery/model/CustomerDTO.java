package lk.ijse.jewellery.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CustomerDTO implements Serializable {
    private String cusId;
    private String title;
    private String cusName;
    private String address;
    private String telNo;
    private String province;
    private String nic;


}
