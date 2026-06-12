/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 *
 * @author RC_Student_lab
 */

public class MessageStorageTest {
    
    private ArrayList<Message> testMessages;
    private ArrayList<String> messageIDs;
    private ArrayList<String> messageHashes;
    
    @BeforeEach
    public void setUp() {
        testMessages = new ArrayList<>();
        messageIDs = new ArrayList<>();
        messageHashes = new ArrayList<>();
        
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.send();
        testMessages.add(msg1);
        messageIDs.add(msg1.getMessageID());
        messageHashes.add(msg1.getMessageHash());
        
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.store();
        testMessages.add(msg2);
        messageIDs.add(msg2.getMessageID());
        messageHashes.add(msg2.getMessageHash());
        
        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        msg3.disregard();
        testMessages.add(msg3);
        
        Message msg4 = new Message("0838884567", "It is dinner time !");
        msg4.send();
        testMessages.add(msg4);
        messageIDs.add(msg4.getMessageID());
        messageHashes.add(msg4.getMessageHash());
        
        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.");
        msg5.store();
        testMessages.add(msg5);
        messageIDs.add(msg5.getMessageID());
        messageHashes.add(msg5.getMessageHash());
    }
    
    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayList<String> sentMessages = new ArrayList<>();
        
        for (Message msg : testMessages) {
            if (msg.isSent()) {
                sentMessages.add(msg.getMessageText());
            }
        }
        
        assertEquals(2, sentMessages.size());
        assertTrue(sentMessages.contains("Did you get the cake?"));
        assertTrue(sentMessages.contains("It is dinner time !"));
    }
    
    @Test
    public void testDisplayLongestMessage() {
        String longestMessage = "";
        int longestLength = 0;
        
        for (Message msg : testMessages) {
            if (msg.getMessageText().length() > longestLength) {
                longestLength = msg.getMessageText().length();
                longestMessage = msg.getMessageText();
            }
        }
        
        assertEquals("Where are you? You are late! I have asked you to be on time.", longestMessage);
    }
    
    @Test
    public void testSearchByMessageID() {
        String targetID = messageIDs.get(3);
        String foundRecipient = null;
        String foundMessage = null;
        
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(targetID)) {
                foundRecipient = testMessages.get(i).getRecipient();
                foundMessage = testMessages.get(i).getMessageText();
                break;
            }
        }
        
        assertEquals("0838884567", foundRecipient);
        assertEquals("It is dinner time !", foundMessage);
    }
    
    @Test
    public void testSearchByRecipient() {
        String targetRecipient = "+27838884567";
        ArrayList<String> foundMessages = new ArrayList<>();
        
        for (Message msg : testMessages) {
            if (msg.getRecipient().equals(targetRecipient)) {
                foundMessages.add(msg.getMessageText());
            }
        }
        
        assertEquals(2, foundMessages.size());
        assertTrue(foundMessages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(foundMessages.contains("Ok, I am leaving without you."));
    }
    
    @Test
    public void testDeleteByMessageHash() {
        ArrayList<String> hashesCopy = new ArrayList<>(messageHashes);
        String hashToDelete = messageHashes.get(1);
        
        int originalSize = hashesCopy.size();
        hashesCopy.removeIf(h -> h.equals(hashToDelete));
        
        assertEquals(originalSize - 1, hashesCopy.size());
        assertFalse(hashesCopy.contains(hashToDelete));
    }
    
    @Test
    public void testDisplayReport() {
        ArrayList<Message> storedOrSent = new ArrayList<>();
        
        for (Message msg : testMessages) {
            if (msg.isStored() || msg.isSent()) {
                storedOrSent.add(msg);
            }
        }
        
        assertEquals(4, storedOrSent.size());
    }
}