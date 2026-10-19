package com.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    void testUsernameValid() {
        Login l = new Login();
        assertEquals("Username successfully captured.", l.checkUserName("ky_1"));
    }

    @Test
    void testUsernameInvalidNoUnderscore() {
        Login l = new Login();
        assertTrue(l.checkUserName("kyle").contains("not correctly formatted"));
    }

    @Test
    void testPasswordValid() {
        Login l = new Login();
        assertEquals("Password successfully captured.", l.checkPasswordComplexity("Ch&nge9!"));
    }

    @Test
    void testCellPhoneValid() {
        Login l = new Login();
        assertEquals("Cell phone number successfully captured.", l.checkCellPhoneNumber("+27718693002"));
    }

    @Test
    void testCellPhoneInvalidNoPlus() {
        Login l = new Login();
        assertTrue(l.checkCellPhoneNumber("08575975889").contains("incorrectly formatted"));
    }

    @Test
    void testLoginSuccessAndStatus() {
        Login l = new Login();
        l.registerUser("ky_1", "Ch&nge9!", "+27718693002", "Kyle", "Smith");
        assertTrue(l.loginUser("ky_1", "Ch&nge9!"));
        assertEquals("Welcome Kyle Smith, it is great to see you again.", l.returnLoginStatus("ky_1", "Ch&nge9!"));
    }
}