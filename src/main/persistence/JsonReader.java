package persistence;

import model.Customer;
import model.Staff;
import model.Vehicle;
import model.VehicleInventory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads inventory data from JSON data stored in file
// This class is modeled from sample "JsonSerializationDemo"
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VehicleInventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVehicleInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory data from JSON object and returns it
    private VehicleInventory parseVehicleInventory(JSONObject jsonObject) {
        int invCounter = jsonObject.getInt("InventoryCounter");
        VehicleInventory vehInv = new VehicleInventory();
        addVehicles(vehInv, jsonObject);
        vehInv.inventoryCounter = invCounter;
        return vehInv;
    }

    // MODIFIES: vehInv
    // EFFECTS: parses vehicles from JSON object and adds them to inventory list
    private void addVehicles(VehicleInventory vehInv, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("vehicles");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addVehicle(vehInv, nextThingy);
        }
    }

    // MODIFIES: vehInv
    // EFFECTS: parses vehicle from JSON object and adds it to inventory list
    private void addVehicle(VehicleInventory vehInv, JSONObject jsonObject) {
        int inventoryNum = jsonObject.getInt("InvNum");
        String autoMaker = jsonObject.getString("Maker");
        Staff buyInStaff = getBuyInStaff(jsonObject);
        int buyInPrice = jsonObject.getInt("BuyInPrice");
        Vehicle vehicle = new Vehicle(autoMaker,buyInStaff,inventoryNum,buyInPrice);
        vehicle.autoType = jsonObject.getString("Type");
        vehicle.autoModel = jsonObject.getString("Model");
        vehicle.autoVin = jsonObject.getString("VIN");
        vehicle.autoColour = jsonObject.getString("Colour");
        vehicle.comment = jsonObject.getString("comment");
        vehicle.autoYear = jsonObject.getInt("Year");
        vehicle.url = jsonObject.getString("URL");
        vehicle.autoMileage = jsonObject.getInt("Mileage");
        vehicle.sellExpectedPrice = jsonObject.getInt("ExpectSellPrice");
        vehicle.sellActualPrice = jsonObject.getInt("ActualSellPrice");
        vehicle.seller = getSeller(jsonObject);
        vehicle.customer = getCustomer(jsonObject);
        vehicle.isSold = jsonObject.getBoolean("SoldOrNot");
        vehInv.addNewInventory(vehicle);
    }

    // REQUIRES: Json object
    // EFFECTS: return buy in staff from reading JSON object
    private Staff getBuyInStaff(JSONObject jsonObject) {
        String name = jsonObject.getString("BuyInStaff_name");
        String phone = jsonObject.getString("BuyInStaff_phone");
        String sex = jsonObject.getString("BuyInStaff_sex");
        int num = jsonObject.getInt("BuyInStaff_id");
        Staff tempStaff = new Staff(name,phone,sex,num);
        return tempStaff;
    }

    // REQUIRES: Json object
    // EFFECTS: return seller staff from reading JSON object
    private Staff getSeller(JSONObject jsonObject) {
        String name = jsonObject.getString("seller_name");
        String phone = jsonObject.getString("seller_phone");
        String sex = jsonObject.getString("seller_sex");
        int num = jsonObject.getInt("seller_id");
        Staff tempStaff = new Staff(name,phone,sex,num);
        return tempStaff;
    }

    // REQUIRES: Json object
    // EFFECTS: return customer from reading JSON object
    private Customer getCustomer(JSONObject jsonObject) {
        String name = jsonObject.getString("customer_name");
        String phone = jsonObject.getString("customer_phone");
        String sex = jsonObject.getString("customer_sex");
        Customer tempCus = new Customer(name,phone,sex);
        return tempCus;
    }
}
