package com.quickchat;

import java.util.regex.Pattern;

public class Login {
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellPhone;
    private String firstName;
    private String lastName;

    public String registerUser(String username, String password, String cellPhone, String firstName, String lastName) {
        String userMsg = checkUserName(username);
        String passMsg = checkPasswordComplexity(password);
        String phoneMsg = checkCellPhoneNumber(cellPhone);

        if (userMsg.equals("Username successfully captured.") && passMsg.equals("Password successfully captured.") && phoneMsg.equals("Cell phone number successfully captured.")) {
            this.registeredUsername = username;
            this.registeredPassword = password;
            this.registeredCellPhone = cellPhone;
            this.firstName = firstName;
            this.lastName = lastName;
            return "User registered successfully.";
        }
        return userMsg + "\n" + passMsg + "\n" + phoneMsg;
    }

    public boolean loginUser(String username, String password) {
        return username != null && password != null && username.equals(registeredUsername) && password.equals(registeredPassword);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public String checkUserName(String username) {
        if (username != null && username.contains("_") && username.length() <= 5) {
            return "Username successfully captured.";
        }
        return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    }

    public String checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        if (hasUpper && hasDigit && hasSpecial) {
            return "Password successfully captured.";
        }
        return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    }

    public String checkCellPhoneNumber(String cellPhone) {
        if (cellPhone == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        Pattern pattern = Pattern.compile("^\\+\\d{10,13}$");
        if (pattern.matcher(cellPhone).matches()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
}