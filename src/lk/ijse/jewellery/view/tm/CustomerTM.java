package lk.ijse.jewellery.view.tm;

public class CustomerTM implements Comparable<CustomerTM> {
    private String cusId;
    private String title;
    private String cusName;
    private String address;
    private String telNo;
    private String province;
    private String nic;

    public CustomerTM() {
    }

    public CustomerTM(String cusId, String title, String cusName, String address, String telNo, String province, String nic) {
        this.cusId = cusId;
        this.title = title;
        this.cusName = cusName;
        this.address = address;
        this.telNo = telNo;
        this.province = province;
        this.nic = nic;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "cusId='" + cusId + '\'' +
                ", title='" + title + '\'' +
                ", cusName='" + cusName + '\'' +
                ", address='" + address + '\'' +
                ", telNo='" + telNo + '\'' +
                ", province='" + province + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }

    @Override
    public int compareTo(CustomerTM o) {
        return cusId.compareTo(o.getCusId());
    }
}

