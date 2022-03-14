package persistence;

import model.Staff;
import model.Vehicle;
import model.VehicleInventory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This class is modeled from sample "JsonSerializationDemo"
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            VehicleInventory vehInv = new VehicleInventory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            VehicleInventory vehInv = new VehicleInventory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json");
            writer.open();
            writer.write(vehInv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            vehInv = reader.read();
            assertEquals(0, vehInv.vehicleList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralInventory() {
        try {
            VehicleInventory vehInv = new VehicleInventory();
            Staff wong = new Staff("Wong","","",1);
            Staff lee = new Staff("Lee","","",2);
            vehInv.addNewInventory("VW",wong,500);
            vehInv.addNewInventory("Benz",lee,500);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralInventory.json");
            writer.open();
            writer.write(vehInv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralInventory.json");
            vehInv = reader.read();
            List<Vehicle> vehicles = vehInv.vehicleList;
            assertEquals(2, vehInv.vehicleList.size());
            checkVehicle("VW","Wong",vehInv.vehicleList.get(0));
            checkVehicle("Benz","Lee",vehInv.vehicleList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}