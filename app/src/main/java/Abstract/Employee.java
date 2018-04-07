package Abstract;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 3/23/2018.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */

public class Employee {

    /**
     * ID : 2
     * id_employee : 1
     * name : test name
     * email : elma@yahoo.com
     * mobile : 212121212121
     * address : sadasdasda dasd
     * salary :
     * rank :
     * pic :
     * w_date :
     * b_date :
     */

    private String ID;
    private String id_employee;
    private String name;
    private String email;
    private String mobile;
    private String address;
    private String salary;
    private String rank;
    private String pic;
    private String w_date;
    private String b_date;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    private String section;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setW_date(String w_date) {
        this.w_date = w_date;
    }

    public void setB_date(String b_date) {
        this.b_date = b_date;
    }

    public String getID() {
        return ID;
    }

    public String getId_employee() {
        return id_employee;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getSalary() {
        return salary;
    }

    public String getRank() {
        return rank;
    }

    public String getPic() {
        return pic;
    }

    public String getW_date() {
        return w_date;
    }

    public String getB_date() {
        return b_date;
    }
}
