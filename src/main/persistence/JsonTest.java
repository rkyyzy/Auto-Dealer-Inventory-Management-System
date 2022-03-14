package persistence;

import model.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

// This class is modeled from sample "JsonSerializationDemo"
public class JsonTest {
    protected void checkVehicle(String maker, String buyInStaffName, Vehicle vehicle) {
        assertEquals(maker,vehicle.autoMaker);
        assertEquals(buyInStaffName,vehicle.buyInStaff.getName());
    }
}
