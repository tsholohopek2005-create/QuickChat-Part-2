package com.quickchat;

import java.util.ArrayList;
import java.util.Scanner;

public class MessageManager {
    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final Scanner scanner;

    // This is the constructor the error is complaining about!
    public MessageManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("Welcome to QuickChat.");
        System.out.print("How many messages do you wish to enter? ");
        int numMessages;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Exiting.");
            return;
        }

        boolean running = true;
        int entered = 0;

        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    if (entered >= numMessages) {
                        System.out.println("You have already entered the maximum number of messages (" + numMessages + ").");
                        break;
                    }
                    handleSendMessage(++entered, numMessages);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2 or 3.");
            }
        }
        System.out.println("\n--- Summary ---");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }

    private void handleSendMessage(int index, int max) {
        System.out.println("\nMessage " + index + " of " + max);
        System.out.print("Enter recipient number (e.g. +27718693002): ");
        String recipient = scanner.nextLine().trim();

        System.out.print("Enter message (max 250 chars): ");
        String text = scanner.nextLine().trim();

        if (text.length() > 250) {
            System.out.println("Message exceeds 250 characters; please reduce the size.");
            return;
        }

        Message msg = new Message(index - 1, recipient, text);

        String recipientCheck = msg.checkRecipientCell();
        if (!recipientCheck.startsWith("Cell phone number successfully")) {
            System.out.println(recipientCheck);
            return;
        }

        System.out.println("\n" + msg.printMessages());
        System.out.println("\nChoose an option:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Choice: ");

        String action = scanner.nextLine().trim();
        switch (action) {
            case "1":
                System.out.println(msg.sentMessage());
                sentMessages.add(msg);
                break;
            case "2":
                System.out.println(msg.discardMessage());
                break;
            case "3":
                System.out.println(msg.storeMessage());
                break;
            default:
                System.out.println("Invalid option. Message not sent.");
        }
    }
}