package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    Staff buyer = new Staff("Lee","111-222-3333","Male",1);
    Staff seller = new Staff("Kuma","111-222-3333","Male",1);
    Customer customerA = new Customer("Bill","222-111-3333","Male");
    Vehicle mercedez = new Vehicle("Mercedez-Benz",buyer,1,65000);
    Vehicle bmw = new Vehicle("BMW",buyer,2,30000);

    @BeforeEach
    void runBefore() {
        mercedez.updateAutoModel("C63");
        bmw.updateAutoModel("M3");

        mercedez.updateAutoType("Sedan");
        bmw.updateAutoType("Coupe");

        mercedez.updateAutoYear(2016);
        bmw.updateAutoYear(2012);

        mercedez.updateColour("BLACK");
        bmw.updateColour("WHITE");

        mercedez.updateListPrice(80000);
        bmw.updateListPrice(40000);

        mercedez.updateMileage(40000);
        bmw.updateMileage(90000);

        try {
            mercedez.updateVin("55SWF8GB6GU151563");
        } catch (Exception ex) {
            //not expected
            fail();
        }

        try {
            bmw.updateVin("WBSWD93559PY43232");
        } catch (Exception ex) {
            //not expected
            fail();
        }

        bmw.addComment("Most highway miles, good condition, garage-kept");
    }

    @Test
    void testGetShortInfo(){
        String shortInfoForMerc = "2016 Mercedez-Benz C63 Sedan Color:BLACK Price:80000";
        Vehicle volkswagon = new Vehicle("Volkswagon",buyer,3,20000);
        String shortInfoForVw = "A Volkswagon is on the way to showroom!";
        String shortInfoForSoldMerc = "2016 Mercedez-Benz C63 Sedan Color:BLACK (SOLD)";

        assertEquals(shortInfoForMerc,mercedez.getShortInfo());
        assertEquals(shortInfoForVw,volkswagon.getShortInfo());
        mercedez.sellTheVehicle(78000,seller,customerA);
        assertEquals(shortInfoForSoldMerc,mercedez.getShortInfo());
    }

    @Test
    void testGetFullInfo(){
        String fullInfoForBmw = "Year: 2012\n"
                + "Maker: BMW\n"
                + "Model: M3\n"
                + "Type: Coupe\n"
                + "Color: WHITE\n"
                + "URL: " + "\n"
                + "Mileage: 90000kms\n"
                + "Vin: WBSWD93559PY43232\n"
                + "Price: 40000\n"
                + "Comment: Most highway miles, good condition, garage-kept";
        Vehicle volkswagon = new Vehicle("Volkswagon",buyer,3,20000);
        String fullInfoForVw = "A Volkswagon is on the way to showroom!";
        String fullInfoForSoldBmw = "Year: 2012\n"
                + "Maker: BMW\n"
                + "Model: M3\n"
                + "Type: Coupe\n"
                + "Color: WHITE\n"
                + "URL: " + "\n"
                + "Mileage: 90000kms\n"
                + "Vin: WBSWD93559PY43232\n"
                + "Comment: Most highway miles, good condition, garage-kept\n"
                + "*It is already sold!*";
        assertEquals(fullInfoForBmw,bmw.getFullInfo());
        assertEquals(fullInfoForVw,volkswagon.getFullInfo());
        bmw.sellTheVehicle(40000,seller,customerA);
        assertEquals(fullInfoForSoldBmw,bmw.getFullInfo());
    }

    @Test
    void testGetInfoForStaff(){
        String InfoForBmw = "Inventory Number: 2\n"
                + "Year: 2012\n"
                + "Maker: BMW\n"
                + "Model: M3\n"
                + "Type: Coupe\n"
                + "Color: WHITE\n"
                + "URL: " + "\n"
                + "Mileage: 90000kms\n"
                + "Vin: WBSWD93559PY43232\n"
                + "Comment: Most highway miles, good condition, garage-kept\n"
                + "Buy in Staff: Lee\n"
                + "Buy in Price: 30000\n"
                + "Expected Price: 40000\n"
                + "Status: Car is not sold!";
        Vehicle volkswagon = new Vehicle("Volkswagon",buyer,3,20000);
        String InfoForVw = "Inventory Number: 3\n"
                + "Year: 0\n"
                + "Maker: Volkswagon\n"
                + "Model: \n"
                + "Type: \n"
                + "Color: \n"
                + "URL: " + "\n"
                + "Mileage: 0kms\n"
                + "Vin: \n"
                + "Comment: \n"
                + "Buy in Staff: Lee\n"
                + "Buy in Price: 20000\n"
                + "Expected Price: 0\n"
                + "Status: Car is not sold!";
        String InfoForSoldBmw = "Inventory Number: 2\n"
                + "Year: 2012\n"
                + "Maker: BMW\n"
                + "Model: M3\n"
                + "Type: Coupe\n"
                + "Color: WHITE\n"
                + "URL: " + "\n"
                + "Mileage: 90000kms\n"
                + "Vin: WBSWD93559PY43232\n"
                + "Comment: Most highway miles, good condition, garage-kept\n"
                + "Buy in Staff: Lee\n"
                + "Buy in Price: 30000\n"
                + "Expected Price: 40000\n"
                + "Actual Sold Price: 40000\n"
                + "Customer: Bill\n"
                + "Seller: Kuma\n"
                + "Status: Car is sold!";
        assertEquals(InfoForBmw,bmw.getInfoForStaff());
        assertEquals(InfoForVw,volkswagon.getInfoForStaff());
        bmw.sellTheVehicle(40000,seller,customerA);
        assertEquals(InfoForSoldBmw,bmw.getInfoForStaff());
    }

    @Test
    void testSellVehicle() {
        assertTrue(mercedez.sellTheVehicle(78000,seller,customerA));
        Vehicle volkswagon = new Vehicle("Volkswagon",buyer,3,20000);
        assertFalse(volkswagon.sellTheVehicle(25000,seller,customerA));
    }

    @Test
    void testUpdateVinException() {
        try {
            bmw.updateVin("WBS");
        } catch (Exception ex) {
            //expected
            System.out.println(ex.getMessage());
        }

        assertEquals("WBSWD93559PY43232",bmw.autoVin);

        try {
            bmw.updateVin("01234567891234567");
        } catch (Exception ex) {
            //not expected
            fail();
        }

        assertEquals("01234567891234567",bmw.autoVin);
    }

    @Test
    void testGetInventoryNum() {
        assertEquals(1,mercedez.getInventoryNum());
    }

    @Test
    void testAllUpdateMethod() {
        Vehicle toyota = new Vehicle("toyota",buyer,3,60000);
        toyota.updateAutoMaker("BMW");
        toyota.updateAutoModel("Z4");
        toyota.updateAutoType("Roadster");
        toyota.updateAutoYear(2019);
        toyota.updateColour("WHITE");
        toyota.updateListPrice(75000);
        toyota.updateMileage(5000);
        try {
            toyota.updateVin("WBAHF3C59KWW15329");
        } catch (Exception ex) {
            //not expected
            fail();
        }
        toyota.addComment("Almost brand new 2019 BMW z4 demo car");
        String InfoForFakeToyota = "Inventory Number: 3\n"
                + "Year: 2019\n"
                + "Maker: BMW\n"
                + "Model: Z4\n"
                + "Type: Roadster\n"
                + "Color: WHITE\n"
                + "URL: " + "\n"
                + "Mileage: 5000kms\n"
                + "Vin: WBAHF3C59KWW15329\n"
                + "Comment: Almost brand new 2019 BMW z4 demo car\n"
                + "Buy in Staff: Lee\n"
                + "Buy in Price: 60000\n"
                + "Expected Price: 75000\n"
                + "Status: Car is not sold!";
        assertEquals(InfoForFakeToyota,toyota.getInfoForStaff());
    }

    @Test
    void testIsReadyForExhibit() {
        assertTrue(mercedez.isReadyForExhibit());
        Vehicle volkswagon = new Vehicle("Volkswagon",buyer,3,20000);
        assertFalse(volkswagon.isReadyForExhibit());
    }
}