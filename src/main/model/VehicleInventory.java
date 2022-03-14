package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.Random;

// Represents a vehicle list
public class VehicleInventory implements Writable {
    public LinkedList<Vehicle> vehicleList;
    private static final int SHOWROOM_SIZE = 20;
    public int inventoryCounter;

    // EFFECTS: Initialise vehicleInShowRoom and inventoryCounter
    public VehicleInventory() {
        vehicleList = new LinkedList<>();
        inventoryCounter = 1;
    }

    // EFFECTS: return true if vehicleInShowRoom is full, false if full
    public boolean isFull() {
        if (vehicleList.size() >= SHOWROOM_SIZE) {
            return true;
        }
        return false;
    }

    // EFFECTS: return the number of inventory in the list
    public int getNumberOfInventory() {
        return (vehicleList.size());
    }

    // EFFECTS: return true if inventory number is valid, otherwise return false
    public boolean isInventoryNumAvailable(int inventoryNum) {
        for (Vehicle next : vehicleList) {
            if (next.getInventoryNum() == inventoryNum) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return true if index of linkedlist is valid, otherwise return false
    public boolean isIndexAvailable(int index) {
        if (index + 1 <= vehicleList.size() && index >= 0) {
            return true;
        }
        return false;
    }

    // EFFECTS: return true if number in the showroom is valid, otherwise return false
    public boolean isNumAvailable(int num) {
        if (num > vehicleList.size() || num <= 0) {
            return false;
        }
        return true;
    }

    // REQUIRES: autoMaker, buyInStaff, buyInPrice
    // MODIFIES: this
    // EFFECTS: add a vehicle in vehicleInShowRoom, return inventory number if added successfully, -1 if full
    public int addNewInventory(String autoMaker, Staff buyInStaff, int buyInPrice) {
        if (vehicleList.size() >= SHOWROOM_SIZE) {
            return -1;
        }
        Vehicle tempVehicle = new Vehicle(autoMaker,buyInStaff,inventoryCounter,buyInPrice);
        inventoryCounter++;
        vehicleList.add(tempVehicle);
        return tempVehicle.getInventoryNum();
    }

    // REQUIRES: vehicle
    // MODIFIES: this
    // EFFECTS: add a vehicle in vehicleInShowRoom, return inventory number if added successfully, -1 if full
    public int addNewInventory(Vehicle vehicle) {
        if (vehicleList.size() >= SHOWROOM_SIZE) {
            return -1;
        }
        inventoryCounter++;
        vehicleList.add(vehicle);
        return vehicle.getInventoryNum();
    }

    // REQUIRES: an inventory number
    // MODIFIES: this
    // EFFECTS: remove a vehicle in vehicleInShowRoom that has left showroom,
    // return true if removed successfully, false if failed
    public boolean removeInventory(int inventoryNum) {
        if (!isInventoryNumAvailable(inventoryNum)) {
            return false;
        }
        int targetIndex = 0;
        int counter = 0;
        for (Vehicle next : vehicleList) {
            if (next.getInventoryNum() == inventoryNum) {
                targetIndex = counter;
                vehicleList.remove(targetIndex);
                return true;
            }
            counter++;
        }
        return false;
    }

    // REQUIRES: a valid inventory number
    // EFFECTS: return the corresponding index number in the linkedlist, otherwise return -1
    public int invNumToIndex(int inventoryNum) {
        int counter = 0;
        for (Vehicle next : vehicleList) {
            if (next.getInventoryNum() == inventoryNum) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    // REQUIRES: a valid inventory number
    // EFFECTS: return the corresponding number of vehicle for client side, otherwise return -1
    public int invNumToNum(int inventoryNum) {
        int counter = 1;
        for (Vehicle next : vehicleList) {
            if (next.getInventoryNum() == inventoryNum) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    // REQUIRES: the valid number of vehicle
    // EFFECTS: return the corresponding inventory number of vehicle
    public int numToInvNum(int num) {
        return vehicleList.get(num - 1).getInventoryNum();
    }

    // REQUIRES: a valid inventory number, soldPrice, staff and a customer
    // MODIFIES: this
    // EFFECTS: return true if the designated vehicle is successfully marked as sold,
    // return false if inventory number is valid or the vehicle is not qualified for sale
    public boolean markSoldByInvNum(int inventoryNum, int soldPrice, Staff staff, Customer customer) {
        if (!isInventoryNumAvailable(inventoryNum)) {
            return false;
        }
        int indexNum = invNumToIndex(inventoryNum);
        return (vehicleList.get(indexNum).sellTheVehicle(soldPrice,staff,customer));
    }

    // EFFECTS: return all vehicles in the showroom with showroom number
    public String showInventoryWithNum() {
        if (getNumberOfInventory() == 0) {
            return ("No Cars in Showroom now! Check back later!\n");
        }

        String outputString = "";
        int counter = 1;
        for (Vehicle next : vehicleList) {
            outputString = outputString.concat(Integer.toString(counter) + ". " + next.getShortInfo() + "\n");
            counter++;
        }

        return outputString;
    }

    // EFFECTS: return detail vehicle information in the showroom by its designated showroom number
    public String showDetailInfoByNum(int number) {
        if (!isNumAvailable(number)) {
            return ("please enter correct showroom number!\n");
        }
        return (vehicleList.get(number - 1).getFullInfo() + "\n");
    }

    // EFFECTS: return detail vehicle information in the showroom by its inventory number
    public String showDetailInfoByInvNum(int inventoryNum) {
        int counter = 1;
        for (Vehicle next : vehicleList) {
            if (next.getInventoryNum() == inventoryNum) {
                return (next.getFullInfo() + "\n");
            }
            counter++;
        }
        return ("please enter correct inventory number!\n");
    }

    // EFFECTS: return all vehicle information for staff
    public String staffShowInventoryList() {
        if (getNumberOfInventory() == 0) {
            return ("No Cars in Showroom now!\n");
        }
        String outputString = "";
        int counter = 1;
        for (Vehicle next : vehicleList) {
            outputString = outputString.concat(Integer.toString(counter) + ". " + next.getInfoForStaff() + "\n\n");
            counter++;
        }
        return outputString;
    }

    // MODIFIES: this
    // EFFECTS: add a random demo vehicle in the showroom, return inventory number of new vehicle,
    // return -1 if the showroom is full
    public int addDemoVehicle() {
        if (isFull()) {
            return -1;
        }
        Staff tempStaff = new Staff("Company","NONE","NONE",0);
        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        int randomPrice = rand.nextInt(10000) + 10000;
        int tempInvNum;
        if (randomNum == 0) {
            tempInvNum = addNewInventory("Ford",tempStaff,randomPrice);
            vehicleList.get(invNumToIndex(tempInvNum)).updateAutoModel("Taurus");
        } else {
            tempInvNum = addNewInventory("Dodge",tempStaff,randomPrice);
            vehicleList.get(invNumToIndex(tempInvNum)).updateAutoModel("Charger");
        }
        vehicleList.get(invNumToIndex(tempInvNum)).updateMileage(rand.nextInt(10000));
        vehicleList.get(invNumToIndex(tempInvNum)).updateListPrice(randomPrice + 5000);
        vehicleList.get(invNumToIndex(tempInvNum)).updateColour("WHITE");
        vehicleList.get(invNumToIndex(tempInvNum)).updateURL("empty.jpg");
        vehicleList.get(invNumToIndex(tempInvNum)).updateAutoYear(2019);
        vehicleList.get(invNumToIndex(tempInvNum)).updateAutoType("Sedan");
        updateDemoVin(tempInvNum);
        return tempInvNum;
    }

    private void updateDemoVin(int tempInvNum) {
        try {
            vehicleList.get(invNumToIndex(tempInvNum)).updateVin("DEMO-VEHICLES-VIN");
        } catch (Exception ex) {
            //not expected
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("InventoryCounter", inventoryCounter);
        json.put("vehicles", vehiclesToJson());
        return json;
    }

    // EFFECTS: returns vehicles in this vehicle list as a JSON array
    private JSONArray vehiclesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Vehicle v : vehicleList) {
            jsonArray.put(v.toJson());
        }

        return jsonArray;
    }
}
