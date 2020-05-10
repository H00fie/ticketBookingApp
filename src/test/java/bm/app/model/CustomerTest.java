package bm.app.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerTest {

    @Test
    public void testObjectCreation(){
        Customer customer = new Customer(
                6,
                "testName",
                "testEmail",
                "testTicketType",
                6);
        assertEquals(6, customer.getId());
        assertEquals("testName", customer.getName());
        assertEquals("testEmail", customer.getEmail());
        assertEquals("testTicketType", customer.getTicketType());
        assertEquals(6, customer.getSeatnumber());
    }
}
