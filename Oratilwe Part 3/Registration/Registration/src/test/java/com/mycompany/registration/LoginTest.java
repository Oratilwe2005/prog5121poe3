/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LoginTest {

    private Login login;
 /**
     * Test of checkUsername method.
     */
    @Test
    public void testCheckUsername() {
        Login instance = new Login();
        
        // Test Data: Correct format
        instance.registeredUsername = "kyl_1";
        assertTrue(instance.checkUsername(), "Username correctly formatted should return true");
        
        // Test Data: Incorrect format
        instance.registeredUsername = "kyle!!!!!!!";
        assertFalse(instance.checkUsername(), "Username incorrectly formatted should return false");
    }

    /**
     * Test of checkPasswordComplexity method.
     */
    @Test
    public void testCheckPasswordComplexity() {
       Login instance = new Login();
        
        // Test Data: Password meets complexity
        instance.registeredPassword = "Ch&&sec@ke99!";
        assertTrue(instance.checkPasswordComplexity(), "Complex password should return true");
        
        // Test Data: Password does not meet complexity
        instance.registeredPassword = "password";
        assertFalse(instance.checkPasswordComplexity(), "Simple password should return false");
    }

    /**
     * Test of checkCellPhoneNumber method.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        Login instance = new Login();
        
        // Test Data: Correct cell number
        instance.registeredCellNumber = "+27838968976";
        assertTrue(instance.checkCellPhoneNumber(), "Cell number with + should return true");
        
        // Test Data: Incorrect cell number
        instance.registeredCellNumber = "08966553";
        assertFalse(instance.checkCellPhoneNumber(), "Cell number without + should return false");
    }

    /**
     * Test of loginUser method.
     */
    @Test
    public void testLoginUser() {
        Login instance = new Login();
        
        // Setup registration
        instance.registeredUsername = "kyl_1";
        instance.registeredPassword = "Ch&&sec@ke99!";
        instance.registeredCellNumber = "+27838968976";
        
        // Success Case
        instance.loginUsername = "kyl_1";
        instance.loginPassword = "Ch&&sec@ke99!";
        instance.loginCellNumber = "+27838968976";
        assertTrue(instance.loginUser(), "Matching credentials should return true");
        
        // Failure Case
        instance.loginUsername = "wrong_user";
        assertFalse(instance.loginUser(), "Non-matching credentials should return false");
    }

    /**
     * Test of returnLoginStatus method.
     * Verifies the formatted welcome message.
     */
    @Test
    public void testReturnLoginStatus() {
        Login instance = new Login();
        instance.Name = "Kyle";
        instance.Surname = "Smith";
        
        // Set matching credentials for success
        instance.registeredUsername = "kyl_1";
        instance.loginUsername = "kyl_1";
        instance.registeredPassword = "Ch&&sec@ke99!";
        instance.loginPassword = "Ch&&sec@ke99!";
        instance.registeredCellNumber = "+27838968976";
        instance.loginCellNumber = "+27838968976";
        
        String expected = "Successful login\nWelcome Kyle Smith, it is great to see you again.";
        assertEquals(expected, instance.returnLoginStatus());
    }
}