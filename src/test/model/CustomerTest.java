package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer c1 = new Customer("William Blazkowicz","111-222-3333", "Male");

    @Test
    void testGetName() {
        assertEquals("William Blazkowicz",c1.getName());
    }

    @Test
    void testGetPhoneNum() {
        assertEquals("111-222-3333",c1.getPhoneNum());
    }

    @Test
    void testGetSex() {
        assertEquals("Male",c1.getSex());
    }
}