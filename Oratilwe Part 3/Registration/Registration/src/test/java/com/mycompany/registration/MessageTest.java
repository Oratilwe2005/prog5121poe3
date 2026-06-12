package com.mycompany.registration;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */

public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    public void setUp() {
        Message.resetMessageCounter();
        message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }
    
    @Test
    public void testIsValidRecipient_Success() {
        assertTrue(Message.isValidRecipient("+27718693002"));
        System.out.println("isValidRecipient - Success");
    }
    
    @Test
    public void testIsValidRecipient_Failure_NoInternationalCode() {
        assertFalse(Message.isValidRecipient("0718693002"));
        System.out.println("isValidRecipient - Failure (No International Code)");
    }
    
    @Test
    public void testIsValidRecipient_Failure_TooLong() {
        assertFalse(Message.isValidRecipient("+27718693002123"));
        System.out.println("isValidRecipient - Failure (Too Long)");
    }
    
    @Test
    public void testIsValidRecipient_Failure_Null() {
        assertFalse(Message.isValidRecipient(null));
        System.out.println("isValidRecipient - Failure (Null)");
    }
    
    @Test
    public void testIsValidMessage_Success() {
        assertTrue(Message.isValidMessage("This is a short message."));
        System.out.println("isValidMessage - Success");
    }
    
    @Test
    public void testIsValidMessage_Success_Boundary250() {
        String message250 = "A".repeat(250);
        assertTrue(Message.isValidMessage(message250));
        System.out.println("isValidMessage - Success (Boundary 250)");
    }
    
    @Test
    public void testIsValidMessage_Failure_TooLong() {
        String message251 = "A".repeat(251);
        assertFalse(Message.isValidMessage(message251));
        System.out.println("isValidMessage - Failure (Too Long)");
    }
    
    @Test
    public void testIsValidMessage_Failure_Null() {
        assertFalse(Message.isValidMessage(null));
        System.out.println("isValidMessage - Failure (Null)");
    }
    
    @Test
    public void testMessageCreation_MessageID_and_Hash() {
        assertEquals("0000000001", message.getMessageID());
        assertNotNull(message.getMessageHash());
        assertTrue(message.getMessageHash().length() > 0);
        System.out.println("Message Creation - MessageID and Hash");
    }
    
    @Test
    public void testSend() {
        message.send();
        assertTrue(message.isSent());
        assertFalse(message.isStored());
        System.out.println("send");
    }
    
    @Test
    public void testStore() {
        message.store();
        assertTrue(message.isStored());
        assertFalse(message.isSent());
        System.out.println("store");
    }
    
    @Test
    public void testDisregard() {
        message.disregard();
        assertFalse(message.isSent());
        assertFalse(message.isStored());
        System.out.println("disregard");
    }
    
    @Test
    public void testGetMessageDetails() {
        String details = message.getMessageDetails();
        assertTrue(details.contains("MessageID:"));
        assertTrue(details.contains("Message Hash:"));
        assertTrue(details.contains("Recipient:"));
        assertTrue(details.contains("Message:"));
        System.out.println("getMessageDetails");
    }
    
    @Test
    public void testToJSON() {
        JSONObject json = message.toJSON();
        assertNotNull(json);
        assertTrue(json.has("MessageID"));
        assertTrue(json.has("Recipient"));
        assertTrue(json.has("Message"));
        assertTrue(json.has("Hash"));
        assertTrue(json.has("Sent"));
        assertTrue(json.has("Stored"));
        System.out.println("toJSON");
    }
    
    @Test
    public void testMessageHashFormat() {
        String hash = message.getMessageHash();
        assertTrue(hash.matches("\\d{2}:\\d+:.*"));
        System.out.println("Message Hash Format - Verified");
    }
}