package Steph_Lab17_Group2_A2;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    private static final String USERS_FILE = "users.txt"; // User credentials
    private static final String ADMIN_USERNAME = "admin"; // Admin credentials
    private static final String ADMIN_PASSWORD_HASHED = Encryption.doMD5Hashing("admin");
    private static ScrollManager scrollManager = new ScrollManager(); // Manages Scroll functions

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;

        while (isRunning) {
            // Login Portal
            System.out.println("Please select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Guest");
            System.out.println("4. Exit");

            int loginChoice = scanner.nextInt();
            scanner.nextLine();

            switch (loginChoice) {
                case 1:
                    // Login case
                    boolean loginSuccess = false;
                    while (!loginSuccess) {
                        System.out.println("Enter username:");
                        String username = scanner.nextLine();
                        System.out.println("Enter password:");
                        String password = scanner.nextLine();

                        // Hash
                        String hashedPassword = Encryption.doMD5Hashing(password);

                        if (validateCredentials(username, hashedPassword)) {
                            if (isAdmin(username, hashedPassword)) {
                                System.out.println("Login successful! Welcome admin.");
                                adminPortal();
                            } else {
                                System.out.println("Login successful! Welcome " + username);
                                userPortal(username);
                            }
                            loginSuccess = true; // Exit the login loop upon success
                            isRunning = false;
                        } else {
                            System.out.println("Invalid username or password.");
                            System.out.println("Would you like to try again or go back to the main menu?");
                            System.out.println("1. Try again");
                            System.out.println("2. Go back to the main menu");

                            int retryChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (retryChoice == 2) {
                                break; // go back to main menu
                            }
                        }
                    }

                    // Logged in

                    break;

                case 2:
                    // Register case
                    System.out.println("Please enter your username:");
                    String newUsername = scanner.nextLine();
                    if (isUsernameTaken(newUsername)) {
                        System.out.println("Username already exists. Please try again.");
                    } else {
                        System.out.println("Please enter your password:");
                        String newPassword = scanner.nextLine();
                        // Hash the password and save the new user credentials to the file
                        String hashedPassword = Encryption.doMD5Hashing(newPassword);
                        saveNewUser(newUsername, hashedPassword);
                        System.out.println("Registration successful! You can now log in.");
                    }
                    break;

                case 3:
                    // Guest case
                    System.out.println("Proceeding as Guest. Limited access granted.");
                    guestPortal();
                    break;

                case 4:
                    // Exit case
                    System.out.println("Exiting the system. Goodbye!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }

        scanner.close();
    }

    // Admin Portal
    private static void adminPortal() {
        while (true) {
            System.out.println("====Admin Portal====");
            System.out.println("1. View User Profiles");
            System.out.println("2. Add User Profiles");
            System.out.println("3. Delete User Profiles");
            System.out.println("4. Update User Profiles");
            System.out.println("5. View Stats");
            System.out.println("6. View Scrolls"); // Uses ScrollManager
            System.out.println("7. Add Scrolls"); // Uses ScrollManager
            System.out.println("8. Remove Scrolls"); // Uses ScrollManager
            System.out.println("9: Exit");

            Scanner scanner = new Scanner(System.in);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()

            switch (choice) {
                case 6:
                    // List all scrolls
                    scrollManager.listScrolls();
                    break;
                case 7:
                    // Add new scroll
                    scrollManager.addScroll();
                    
                    // test adding new file: app/bin/main/Steph_Lab17_Group2_A2/scroll.bin
                    break;
                case 8:
                    // Remove a scroll
                    scrollManager.removeScroll();
                    break;
                case 9:
                    // Exit the menu
                    System.out.println("Exiting Admin Management.");
                    return; // Exit the method to go back to the main program
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // User Portal
    private static void userPortal(String username) {
        System.out.println("====User Portal====");
        System.out.println("1. Update User Profiles");
        System.out.println("2. View Scrolls");
    }

    // Guest Portal
    private static void guestPortal() {
        System.out.println("You are in the Guest Portal.");
        // Guest-specific actions go here
    }

    // Check if the username is the admin and if the password matches the admin's
    // hashed password
    private static boolean isAdmin(String username, String hashedPassword) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD_HASHED.equals(hashedPassword);
    }

    // Check if a username already exists in the file
    private static boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails[0].equals(username)) {
                    return true; // Username found
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false; // Username not found
    }

    // Validate username and password by checking the file
    private static boolean validateCredentials(String username, String hashedPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails[0].equals(username) && userDetails[1].equals(hashedPassword)) {
                    return true; // Credentials match!
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false; // Credentials matchn't
    }

    // Save a new user's username and hashed password to the file
    private static void saveNewUser(String username, String hashedPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + ":" + hashedPassword);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to user file.");
        }
    }
}
