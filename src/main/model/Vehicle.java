package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a inventory vehicle with detailed information
public class Vehicle implements Writable {
    public static final int SIZE_VIN = 17;
    public final int inventoryNum;
    public String autoType;
    public String autoMaker;
    public String autoModel;
    public String autoVin;
    public String autoColour;
    public String comment;
    public int autoYear;
    public int autoMileage;
    public final int buyInPrice;
    public int sellExpectedPrice;
    public int sellActualPrice;
    public final Staff buyInStaff;
    public Staff seller;
    public Customer customer;
    public boolean isSold;
    public String url;

    // EFFECTS: provide basic information: maker, inventory number, buy in price
    // and buyer of newly-coming vehicle inventory
    public Vehicle(String autoMaker, Staff buyInStaff, int inventoryNum, int buyInPrice) {
        this.autoMaker = autoMaker;
        this.buyInPrice = buyInPrice;
        this.buyInStaff = buyInStaff;
        this.customer = new Customer("NONE","","");
        this.seller = new Staff("NONE","","",-1);
        this.inventoryNum = inventoryNum;
        this.autoType = "";
        this.autoModel = "";
        this.autoVin = "";
        this.autoColour = "";
        this.autoYear = 0;
        this.url = "";
        this.autoMileage = 0;
        this.sellExpectedPrice = 0;
        this.sellActualPrice = 0;
        this.comment = "";
        this.isSold = false;
    }

    // EFFECTS: return ture if all info other than comment is filled, otherwise return false
    public boolean isReadyForExhibit() {
        if (autoType.isEmpty() || autoModel.isEmpty() || autoVin.isEmpty()
                || autoColour.isEmpty() || autoYear == 0 || autoMileage == 0 || sellExpectedPrice == 0) {
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: update the type of this vehicle
    public void updateAutoType(String autoType) {
        this.autoType = autoType;
    }

    // MODIFIES: this
    // EFFECTS: update the maker of this vehicle
    public void updateAutoMaker(String autoMaker) {
        this.autoMaker = autoMaker;
    }

    // MODIFIES: this
    // EFFECTS: update the url of this vehicle
    public void updateURL(String url) {
        this.url = url;
    }

    // MODIFIES: this
    // EFFECTS: update the model of this vehicle
    public void updateAutoModel(String autoModel) {
        this.autoModel = autoModel;
    }

    // MODIFIES: this
    // EFFECTS: update the colour of this vehicle
    public void updateColour(String autoColour) {
        this.autoColour = autoColour;
    }

    // MODIFIES: this
    // EFFECTS: update the year of this vehicle
    public void updateAutoYear(int autoYear) {
        this.autoYear = autoYear;
    }

    // MODIFIES: this
    // EFFECTS: update the mileage of this vehicle in kilometers
    public void updateMileage(int autoMileage) {
        this.autoMileage = autoMileage;
    }

    // MODIFIES: this
    // EFFECTS: update the expected price of this vehicle
    public void updateListPrice(int sellExpectedPrice) {
        this.sellExpectedPrice = sellExpectedPrice;
    }

    // MODIFIES: this
    // EFFECTS: update the colour of this vehicle
    public void addComment(String comment) {
        this.comment = comment;
    }

    // MODIFIES: this
    // EFFECTS: update the 17-character VIN number of this vehicle
    public void updateVin(String autoVin) throws Exception {
        if (autoVin.length() != SIZE_VIN) {
            throw new Exception("Length of VIN incorrect!");
        }
        this.autoVin = autoVin;
    }

    // REQUIRES: car is ready for exhibit and is not sold
    // MODIFIES: this
    // EFFECTS: return true if vehicle is ready for exhibit and not sold, and update the isSold; otherwise return false
    public boolean sellTheVehicle(int price, Staff seller, Customer customer) {
        if (isReadyForExhibit() && !isSold) {
            this.sellActualPrice = price;
            this.seller = seller;
            this.customer = customer;
            this.isSold = true;
            return true;
        }
        return false;
    }

    // EFFECTS: return the inventory number of this vehicle
    public int getInventoryNum() {
        return inventoryNum;
    }

    // EFFECTS: return the short information for client of this vehicle
    public String getShortInfo() {
        if (!isReadyForExhibit()) {
            return ("A " + autoMaker + " is on the way to showroom!");
        }

        if (!isSold) {
            return Integer.toString(autoYear) + " "
                    + autoMaker + " "
                    + autoModel + " "
                    + autoType + " "
                    + "Color:" + autoColour + " "
                    + "Price:" + Integer.toString(sellExpectedPrice);
        }
        return Integer.toString(autoYear) + " "
                + autoMaker + " "
                + autoModel + " "
                + autoType + " "
                + "Color:" + autoColour + " "
                + "(SOLD)";

    }

    // EFFECTS: return the full information for client of this vehicle
    public String getFullInfo() {
        if (!isReadyForExhibit()) {
            return ("A " + autoMaker + " is on the way to showroom!");
        }

        if (!isSold) {
            return getNotSoldString();
        }
        return ("Year: " + Integer.toString(autoYear) + "\n" + "Maker: " + autoMaker + "\n"
                + "Model: " + autoModel + "\n" + "Type: " + autoType + "\n"
                + "Color: " + autoColour + "\n" + "URL: " + url + "\n"
                + "Mileage: " + autoMileage + "kms" + "\n"
                + "Vin: " + autoVin + "\n"
                + "Comment: " + comment + "\n"
                + "*It is already sold!*");
    }

    private String getNotSoldString() {
        return ("Year: " + Integer.toString(autoYear) + "\n" + "Maker: " + autoMaker + "\n"
                + "Model: " + autoModel + "\n" + "Type: " + autoType + "\n"
                + "Color: " + autoColour + "\n" + "URL: " + url + "\n"
                + "Mileage: " + autoMileage + "kms" + "\n"
                + "Vin: " + autoVin + "\n"
                + "Price: " + Integer.toString(sellExpectedPrice) + "\n"
                + "Comment: " + comment);
    }

    // EFFECTS: return the information for staff of this vehicle
    public String getInfoForStaff() {
        String returnText = "Inventory Number: " + Integer.toString(inventoryNum) + "\n"
                + "Year: " + Integer.toString(autoYear) + "\n"
                + "Maker: " + autoMaker + "\n"
                + "Model: " + autoModel + "\n"
                + "Type: " + autoType + "\n"
                + "Color: " + autoColour + "\n"
                + "URL: " + url + "\n"
                + "Mileage: " + autoMileage + "kms" + "\n"
                + "Vin: " + autoVin + "\n"
                + "Comment: " + comment + "\n"
                + "Buy in Staff: " + buyInStaff.getName() + "\n"
                + "Buy in Price: " + Integer.toString(buyInPrice) + "\n"
                + "Expected Price: " + Integer.toString(sellExpectedPrice) + "\n";

        if (!isSold) {
            returnText = returnText + "Status: Car is not sold!";
        } else {
            returnText = returnText + "Actual Sold Price: " + Integer.toString(sellActualPrice) + "\n"
                    + "Customer: " + customer.getName() + "\n"
                    + "Seller: " + seller.getName() + "\n"
                    + "Status: Car is sold!";
        }
        return returnText;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("InvNum", inventoryNum);
        json.put("Type", autoType);
        json.put("Maker", autoMaker);
        json.put("Model", autoModel);
        json.put("VIN", autoVin);
        json.put("Colour", autoColour);
        json.put("comment", comment);
        json.put("Year", autoYear);
        json.put("URL", url);
        json.put("Mileage", autoMileage);
        json.put("BuyInPrice", buyInPrice);
        json.put("ExpectSellPrice", sellExpectedPrice);
        json.put("ActualSellPrice", sellActualPrice);
        buyInStaffToJson(json);
        sellerToJson(json);
        customerToJson(json);
        json.put("SoldOrNot", isSold);
        return json;
    }

    // REQUIRES: Json object
    // EFFECTS: put buy in staff info into Json object
    private void buyInStaffToJson(JSONObject json) {
        json.put("BuyInStaff_name", buyInStaff.getName());
        json.put("BuyInStaff_phone", buyInStaff.getPhoneNum());
        json.put("BuyInStaff_sex", buyInStaff.getSex());
        json.put("BuyInStaff_id", buyInStaff.getStaffNum());
    }

    // REQUIRES: Json object
    // EFFECTS: put seller staff info into Json object
    private void sellerToJson(JSONObject json) {
        json.put("seller_name", seller.getName());
        json.put("seller_phone", seller.getPhoneNum());
        json.put("seller_sex", seller.getSex());
        json.put("seller_id", seller.getStaffNum());
    }

    // REQUIRES: Json object
    // EFFECTS: put customer info into Json object
    private void customerToJson(JSONObject json) {
        json.put("customer_name", customer.getName());
        json.put("customer_phone", customer.getPhoneNum());
        json.put("customer_sex", customer.getSex());
    }
}
