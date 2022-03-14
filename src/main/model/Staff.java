package model;

// Represents a staff member with detailed information
public class Staff {
    private String name;
    private String phoneNum;
    private String sex;
    private int staffNum;

    // EFFECTS: Staff is given name, phone number, sex and staff number
    public Staff(String name, String phoneNum, String sex, int staffNum) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.sex = sex;
        this.staffNum = staffNum;
    }

    // EFFECTS: return the name of this staff
    public String getName() {
        return this.name;
    }

    // EFFECTS: return the phone number of this staff
    public String getPhoneNum() {
        return this.phoneNum;
    }

    // EFFECTS: return the sex of this staff
    public String getSex() {
        return this.sex;
    }

    // EFFECTS: return the staff number of this staff
    public int getStaffNum() {
        return this.staffNum;
    }
}
