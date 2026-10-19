package com.quickchat;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessages = 0;
    private static final Random RANDOM = new Random();
    private static final String JSON_FILE = "messages.json";

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public static String generateMessageID() {
        long id = (long) (RANDOM.nextDouble() * 99_999_999L) + 1_000_000L;
        return "00" + String.format("%08d", id).substring(0, 8);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 13 && Pattern.matches("^\\+\\d+$", recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String cleaned = messageText.trim().replaceAll("\\s+", " ");
        String[] words = cleaned.split(" ");
        String firstWord = words[0].replaceAll("[^A-Za-z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z]", "");
        return firstTwo + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String sentMessage() {
        totalMessages++;
        return "Message successfully sent";
    }

    public String discardMessage() {
        return "Press 0 to delete the message";
    }

    public String storeMessage() {
        JSONObject obj = toJSON();
        try (FileWriter fw = new FileWriter(JSON_FILE, true)) {
            fw.write(obj.toString() + System.lineSeparator());
        } catch (IOException e) {
            return "Failed to store message: " + e.getMessage();
        }
        return "Message successfully stored";
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: " + recipient + "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static void resetTotalMessages() {
        totalMessages = 0;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("messageID", messageID);
        obj.put("messageNumber", messageNumber);
        obj.put("recipient", recipient);
        obj.put("messageText", messageText);
        obj.put("messageHash", messageHash);
        return obj;
    }
    
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
}