package persistence;


import model.Vehicle;
import model.VehicleInventory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This class is modeled from sample "JsonSerializationDemo"
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            VehicleInventory vehInv = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            VehicleInventory vehInv = reader.read();
            assertEquals(0, vehInv.getNumberOfInventory());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralInventory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralInventory.json");
        try {
            VehicleInventory vehInv = reader.read();
            List<Vehicle> vehicles = vehInv.vehicleList;
            assertEquals(2, vehInv.vehicleList.size());
            checkVehicle("VW","Wong",vehInv.vehicleList.get(0));
            checkVehicle("Benz","Lee",vehInv.vehicleList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}