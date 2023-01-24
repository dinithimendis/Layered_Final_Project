package lk.ijse.jewellery.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class EmployeeTM implements Comparable<EmployeeTM>{
        private String empId;
        private String name;
        private String nic;
        private double salary;
        private String telNo;
        private String address;
        private String jobRole;

        @Override
        public int compareTo(EmployeeTM o) {
                return empId.compareTo(o.getEmpId());
        }

}
