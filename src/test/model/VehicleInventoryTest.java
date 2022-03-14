package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleInventoryTest {
    Staff staffA = new Staff("Lee","111-222-3333","Male",1);
    Customer customerA = new Customer("Bill","222-111-3333","Male");
    VehicleInventory showRoom = new VehicleInventory();
    int bmwInvNum;
    int mercInvNum;

    @BeforeEach
    void runBefore() {
        bmwInvNum = showRoom.addNewInventory("BMW",staffA,30000);
        mercInvNum = showRoom.addNewInventory("Mercedez-Benz",staffA,40000);
    }

    @Test
    void testIsFull() {
        assertFalse(showRoom.isFull());
        assertEquals(3,showRoom.addDemoVehicle());
        for (int i = 0; i < 17; i++) {
            showRoom.addNewInventory("VW",staffA,5000);
        }
        assertTrue(showRoom.isFull());
        assertEquals(-1,showRoom.addDemoVehicle());
    }

    @Test
    void testIsInventoryNumAvailable() {
        assertTrue(showRoom.isInventoryNumAvailable(bmwInvNum));
        assertFalse(showRoom.isInventoryNumAvailable(0));
    }

    @Test
    void testInvNumToIndex() {
        assertEquals(0,showRoom.invNumToIndex(1));
        assertEquals(-1,showRoom.invNumToIndex(0));
    }

    @Test
    void testInvNumToNum() {
        assertEquals(1,showRoom.invNumToNum(1));
        assertEquals(-1,showRoom.invNumToNum(0));
    }

    @Test
    void testIsIndexAvailable() {
        assertTrue(showRoom.isIndexAvailable(0));
        assertFalse(showRoom.isIndexAvailable(3));
    }

    @Test
    void testIsNumAvailable() {
        assertTrue(showRoom.isNumAvailable(1));
        assertFalse(showRoom.isNumAvailable(3));
        assertFalse(showRoom.isNumAvailable(0));
    }

    @Test
    void testAddNewInventory() {
        assertEquals(3,showRoom.addNewInventory("VW",staffA,5000));
        for (int i = 0; i < 17; i++) {
            showRoom.addNewInventory("VW",staffA,5000);
        }
        assertEquals(-1,showRoom.addNewInventory("VW",staffA,5000));
    }

    @Test
    void testRemoveInventory() {
        assertTrue(showRoom.removeInventory(bmwInvNum));
        assertFalse(showRoom.removeInventory(-1));
    }

    @Test
    void testMarkSoldByInvNum() {
        assertFalse(showRoom.markSoldByInvNum(bmwInvNum,40000,staffA,customerA));
        showRoom.vehicleList.get(0).updateAutoModel("M3");
        showRoom.vehicleList.get(0).updateAutoType("Coupe");
        showRoom.vehicleList.get(0).updateAutoYear(2012);
        showRoom.vehicleList.get(0).updateColour("WHITE");
        showRoom.vehicleList.get(0).updateListPrice(40000);
        showRoom.vehicleList.get(0).updateMileage(90000);
        try {
            showRoom.vehicleList.get(0).updateVin("WBSWD93559PY43232");
        } catch (Exception ex) {
            //not expected
        }
        assertTrue(showRoom.markSoldByInvNum(bmwInvNum,40000,staffA,customerA));
    }

    @Test
    void showInventoryWithNum() {
        String expectOutput = "1. A BMW is on the way to showroom!\n2. A Mercedez-Benz is on the way to showroom!\n";
        assertEquals(expectOutput,showRoom.showInventoryWithNum());
    }

    @Test
    void showDetailInfoByNum() {
        String expectOutput1 = "A BMW is on the way to showroom!\n";
        String expectOutput2 = "please enter correct showroom number!\n";
        assertEquals(expectOutput1,showRoom.showDetailInfoByNum(showRoom.invNumToNum(bmwInvNum)));
        assertEquals(expectOutput2,showRoom.showDetailInfoByNum(-1));;
    }

    @Test
    void testShowDetailInfoByInvNum() {
        String expectOutput1 = "A BMW is on the way to showroom!\n";
        String expectOutput2 = "please enter correct inventory number!\n";
        assertEquals(expectOutput1,showRoom.showDetailInfoByInvNum(bmwInvNum));
        assertEquals(expectOutput2,showRoom.showDetailInfoByInvNum(-1));;
    }

    @Test
    void testStaffShowInventoryList() {
        String expectOutput1 = "1. Inventory Number: 1" + "\n"
                + "Year: 0" + "\n"
                + "Maker: BMW" + "\n"
                + "Model: "  + "\n"
                + "Type: " + "\n"
                + "Color: " + "\n"
                + "URL: " + "\n"
                + "Mileage: 0kms" + "\n"
                + "Vin: " + "\n"
                + "Comment: " + "\n"
                + "Buy in Staff: Lee" + "\n"
                + "Buy in Price: 30000" + "\n"
                + "Expected Price: 0" + "\n"
                + "Status: Car is not sold!" + "\n\n"
                + "2. Inventory Number: 2" + "\n"
                + "Year: 0" + "\n"
                + "Maker: Mercedez-Benz" + "\n"
                + "Model: "  + "\n"
                + "Type: " + "\n"
                + "Color: " + "\n"
                + "URL: " + "\n"
                + "Mileage: 0kms" + "\n"
                + "Vin: " + "\n"
                + "Comment: " + "\n"
                + "Buy in Staff: Lee" + "\n"
                + "Buy in Price: 40000" + "\n"
                + "Expected Price: 0" + "\n"
                + "Status: Car is not sold!" + "\n\n";

        String expectOutput2 = "No Cars in Showroom now!\n";
        assertEquals(expectOutput1,showRoom.staffShowInventoryList());
        showRoom.removeInventory(bmwInvNum);
        showRoom.removeInventory(mercInvNum);
        assertEquals(expectOutput2,showRoom.staffShowInventoryList());;
    }
}