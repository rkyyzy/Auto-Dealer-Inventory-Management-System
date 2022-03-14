package model;

// Represents a customer with detailed information
public class Customer {
    private String name;
    private String phoneNum;
    private String sex;

    // EFFECTS: Customer is given name, phone number and sex
    public Customer(String name, String phoneNum, String sex) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.sex = sex;
    }

    // EFFECTS: return the name of this customer
    public String getName() {
        return this.name;
    }

    // EFFECTS: return the phone number of this customer
    public String getPhoneNum() {
        return this.phoneNum;
    }

    // EFFECTS: return the sex of this customer
    public String getSex() {
        return this.sex;
    }
}
