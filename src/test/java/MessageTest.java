package com.quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    void reset() {
        Message.resetTotalMessages();
    }

    @Test
    void testMessageIDGenerated() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(m.checkMessageID(), "Message ID must be <= 10 characters");
        assertNotNull(m.getMessageID());
    }

    @Test
    void testRecipientCellCorrectlyFormatted() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    void testRecipientCellIncorrectlyFormatted() {
        Message m = new Message(0, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", m.checkRecipientCell());
    }

    @Test
    void testMessageHashForHiMike() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", m.getMessageHash(), "Hash for Test Case 1 must be 00:0:HITONIGHT");
    }

    @Test
    void testMessageHashForHiKeegan() {
        Message m = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        String hash = m.getMessageHash();
        assertTrue(hash.matches("\\d{2}:1:HIPAYMENT"), "Hash should match <2 digits>:1:HIPAYMENT but was " + hash);
    }

    @Test
    void testMessageHashLoopForRemainder() {
        String[] texts = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Hello there",
            "One"
        };
        for (int i = 0; i < texts.length; i++) {
            Message m = new Message(i, "+27718693002", texts[i]);
            String hash = m.getMessageHash();
            String[] parts = hash.split(":");
            assertEquals(3, parts.length, "Hash must have 3 parts: " + hash);
            assertEquals(String.valueOf(i), parts[1], "Message number part must match");
            assertTrue(parts[2].matches("[A-Z]+"), "Last part must be uppercase letters: " + hash);
        }
    }

    @Test
    void testSendMessageIncrementsTotal() {
        Message m1 = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent", m1.sentMessage());

        Message m2 = new Message(1, "+27718693002", "Hi Keegan, did you receive the payment?");
        assertEquals("Message successfully sent", m2.sentMessage());

        assertEquals(2, Message.returnTotalMessages());
    }

    @Test
    void testStoreMessageReturnsSuccess() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored", m.storeMessage());
    }

    @Test
    void testDiscardMessageInstruction() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Press 0 to delete the message", m.discardMessage());
    }

    @Test
    void testPrintMessagesOrder() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String out = m.printMessages();
        int idIdx = out.indexOf("Message ID:");
        int hashIdx = out.indexOf("Message Hash:");
        int recIdx = out.indexOf("Recipient:");
        int msgIdx = out.indexOf("Message:");
        assertTrue(idIdx < hashIdx && hashIdx < recIdx && recIdx < msgIdx, "Print order must be ID, Hash, Recipient, Message");
    }
}
