 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.registration;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author RC_Student_lab
 */

public class Registration {
    
    static ArrayList<String> sentMessagesList = new ArrayList<>();
    static ArrayList<String> disregardedMessagesList = new ArrayList<>();
    static ArrayList<Message> storedMessagesList = new ArrayList<>();
    static ArrayList<String> messageHashesList = new ArrayList<>();
    static ArrayList<String> messageIDsList = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login reg = new Login();
        MessageStorage storage = new MessageStorage();

        // Registration Phase
        System.out.println("--- REGISTRATION ---");
        System.out.print("Enter your Name: ");
        reg.Name = input.next();
        System.out.print("Enter your Surname: ");
        reg.Surname = input.next();
        System.out.print("Create Username: ");
        reg.registeredUsername = input.next();
        System.out.print("Create Password: ");
        reg.registeredPassword = input.next();
        System.out.print("Enter Cell Phone Number (e.g., +27...): ");
        reg.registeredCellNumber = input.next();
        // Display registration results
        System.out.println(reg.registerUser());
        // Login Phase (only if registration was successful)
        if(!reg.checkUsername() || reg.checkPasswordComplexity() || reg.checkCellPhoneNumber()) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter Username: ");
            reg.loginUsername = input.next();
            System.out.print("Enter Password: ");
            reg.loginPassword = input.next();
            System.out.print("Enter Cell Phone Number: ");
            reg.loginCellNumber = input.next();
            // Display login results
            System.out.println(reg.returnLoginStatus()); 
            
        }
        System.out.println("Welcome to QuickChat.");
        System.out.println("How many messages would you like to send?");
        int total = input.nextInt();
        input.nextLine();
        int count = 0;
        
        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show Recently Sent Messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Enter choice: ");
            
            String menu = input.nextLine();

            switch (menu) {
                case "1":
                    if (count >= total) {
                        System.out.println("Message limit reached.");
                        break;
                    }
                    System.out.print("Enter recipient number (include + country code): ");
                    String recipient = input.nextLine();
                    if (!Message.isValidRecipient(recipient)) {
                        System.out.println("Invalid recipient format.");
                        break;
                    }

                    System.out.print("Enter message (max 250 chars): ");
                    String msg = input.nextLine();
                    if (!Message.isValidMessage(msg)) {
                        System.out.println("Message too long.");
                        break;
                    }

                    Message m = new Message(recipient, msg);
                    System.out.println("Choose message option:");
                    System.out.println("1) Send");
                    System.out.println("2) Disregard");
                    System.out.println("3) Store for later");
                    System.out.print("Enter choice: ");
                    
                    String action = input.nextLine();
                    switch (action) {
                        case "1":
                            m.send();
                            storage.addMessage(m);
                            count++;
                            sentMessagesList.add(m.getMessageText());
                            messageHashesList.add(m.getMessageHash());
                            messageIDsList.add(m.getMessageID());
                            System.out.println("Message sent!\n" + m.getMessageDetails());
                            break;
                        case "2":
                            m.disregard();
                            disregardedMessagesList.add(m.getMessageText());
                            System.out.println("Message disregarded.");
                            break;
                        case "3":
                            m.store();
                            storage.addMessage(m);
                            count++;
                            storedMessagesList.add(m);
                            messageHashesList.add(m.getMessageHash());
                            messageIDsList.add(m.getMessageID());
                            System.out.println("Message stored.\n" + m.getMessageDetails());
                            break;
                        default:
                            System.out.println("Invalid action.");
                    }
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Exiting. Total messages: " + storage.getTotalMessages());
                    storage.saveMessagesToJson("messages.json");
                    return;

                case "4":
                    showStoredMessagesMenu();
                    break;

                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }
    
    private static void showStoredMessagesMenu() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for messages by recipient");
            System.out.println("5) Delete a message using message hash");
            System.out.println("6) Display full report");
            System.out.println("7) Back to main menu");
            System.out.print("Choose an option: ");
            
            String choice = input.nextLine();
            
            switch (choice.toLowerCase()) {
                case "1":
                    displaySendersAndRecipients();
                    break;
                case "2":
                    displayLongestMessage();
                    break;
                case "3":
                    searchByMessageID();
                    break;
                case "4":
                    searchByRecipient();
                    break;
                case "5":
                    deleteByMessageHash();
                    break;
                case "6":
                    displayFullReport();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void displaySendersAndRecipients() {
        System.out.println("\n=== ALL STORED MESSAGES ===");
        if (storedMessagesList.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        for (int i = 0; i < storedMessagesList.size(); i++) {
            Message msg = storedMessagesList.get(i);
            System.out.println("Message " + (i+1) + ":");
            System.out.println("  Recipient: " + msg.getRecipient());
            System.out.println("  Message: " + msg.getMessageText());
            System.out.println("------------------------");
        }
    }
    
    private static void displayLongestMessage() {
        System.out.println("\n=== LONGEST STORED MESSAGE ===");
        if (storedMessagesList.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        Message longest = storedMessagesList.get(0);
        for (Message msg : storedMessagesList) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        
        System.out.println("Message: " + longest.getMessageText());
        System.out.println("Length: " + longest.getMessageText().length() + " characters");
        System.out.println("Recipient: " + longest.getRecipient());
        System.out.println("Message Hash: " + longest.getMessageHash());
    }
    
    private static void searchByMessageID() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter Message ID to search: ");
        String searchID = input.nextLine();
        
        boolean found = false;
        for (int i = 0; i < messageIDsList.size(); i++) {
            if (messageIDsList.get(i).equals(searchID)) {
                System.out.println("\n=== MESSAGE FOUND ===");
                System.out.println("Recipient: " + storedMessagesList.get(i).getRecipient());
                System.out.println("Message: " + storedMessagesList.get(i).getMessageText());
                System.out.println("Message Hash: " + messageHashesList.get(i));
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Message ID not found.");
        }
    }
    
    private static void searchByRecipient() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter recipient phone number: ");
        String recipient = input.nextLine();
        
        System.out.println("\n=== MESSAGES FOR " + recipient + " ===");
        boolean found = false;
        for (Message msg : storedMessagesList) {
            if (msg.getRecipient().equals(recipient)) {
                System.out.println("- " + msg.getMessageText());
                System.out.println("  (Hash: " + msg.getMessageHash() + ")");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }
    
    private static void deleteByMessageHash() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter Message Hash to delete: ");
        String hash = input.nextLine();
        
        boolean found = false;
        for (int i = 0; i < messageHashesList.size(); i++) {
            if (messageHashesList.get(i).equals(hash)) {
                System.out.println("Deleting message: " + storedMessagesList.get(i).getMessageText());
                storedMessagesList.remove(i);
                messageHashesList.remove(i);
                messageIDsList.remove(i);
                System.out.println("Message successfully deleted.");
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Message hash not found.");
        }
    }
    
    private static void displayFullReport() {
        System.out.println("\n=== COMPLETE STORED MESSAGES REPORT ===");
        if (storedMessagesList.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        System.out.println("Total messages: " + storedMessagesList.size());
        System.out.println("========================================");
        
        for (int i = 0; i < storedMessagesList.size(); i++) {
            System.out.println("\nMESSAGE #" + (i+1));
            System.out.println("Message ID: " + messageIDsList.get(i));
            System.out.println("Message Hash: " + messageHashesList.get(i));
            System.out.println("Recipient: " + storedMessagesList.get(i).getRecipient());
            System.out.println("Message: " + storedMessagesList.get(i).getMessageText());
            System.out.println("----------------------------------------");
        }
    }
}