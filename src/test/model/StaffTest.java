package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StaffTest {
    Staff s1 = new Staff("Irene Engel","202-298-4000", "Female",1);

    @Test
    void testGetName() {
        assertEquals("Irene Engel",s1.getName());
    }

    @Test
    void testGetPhoneNum() {
        assertEquals("202-298-4000",s1.getPhoneNum());
    }

    @Test
    void testGetSex() {
        assertEquals("Female",s1.getSex());
    }

    @Test
    void testGetStaffNum() {
        assertEquals(1,s1.getStaffNum());
    }
}