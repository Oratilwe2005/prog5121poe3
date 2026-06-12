/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.registration;

import org.json.JSONObject;

/**
 *
 * @author RC_Student_lab
 */

public class Message {
    private static int messageCounter = 0;
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private boolean sent;
    private boolean stored;

    public Message(String recipient, String messageText) {
        messageCounter++;
        this.messageID = String.format("%010d", messageCounter);
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(messageCounter, messageText);
        this.sent = false;
        this.stored = false;
    }

    public static boolean isValidRecipient(String recipient) {
        return recipient != null && recipient.startsWith("+") && recipient.length() <= 13;
    }

    public static boolean isValidMessage(String message) {
        return message != null && message.length() <= 250;
    }

    private String createMessageHash(int msgNumber, String message) {
        String idPart = this.messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        String combinedWords = firstWord + lastWord;
        return (idPart + ":" + msgNumber + ":" + combinedWords).toUpperCase();
    }

    public void send() {
        this.sent = true;
        this.stored = false;
    }

    public void store() {
        this.stored = true;
        this.sent = false;
    }

    public void disregard() {
        this.sent = false;
        this.stored = false;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", messageID);
        obj.put("Recipient", recipient);
        obj.put("Message", messageText);
        obj.put("Hash", messageHash);
        obj.put("Sent", sent);
        obj.put("Stored", stored);
        return obj;
    }

    public String getMessageDetails() {
        return "MessageID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipient + "\n" +
                "Message: " + messageText;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public boolean isSent() {
        return sent;
    }

    public boolean isStored() {
        return stored;
    }

    public static void resetMessageCounter() {
        messageCounter = 0;
    }
}