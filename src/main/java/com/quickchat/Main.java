package com.quickchat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== QuickChat Registration ===");
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Username (e.g. ky_1): ");
        String username = scanner.nextLine().trim();
        System.out.print("Password (8+, capital, digit, special): ");
        String password = scanner.nextLine().trim();
        System.out.print("Cell phone (+27...): ");
        String cell = scanner.nextLine().trim();

        System.out.println(login.registerUser(username, password, cell, firstName, lastName));

        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String inUser = scanner.nextLine().trim();
        System.out.print("Password: ");
        String inPass = scanner.nextLine().trim();

        if (login.loginUser(inUser, inPass)) {
            System.out.println(login.returnLoginStatus(inUser, inPass));
            new MessageManager(scanner).run();
        } else {
            System.out.println("Login failed. Cannot send messages.");
        }
        scanner.close();
    }
}