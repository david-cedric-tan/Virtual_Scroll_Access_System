package Steph_Lab17_Group2_A2;

import java.util.*;
import java.io.*;

public class App {
    private static final String USERS_FILE = "users.txt"; // User profiles
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD_HASHED = Encryption.doMD5Hashing("admin");
    private static ScrollManager scrollManager = new ScrollManager(); // Manages Scroll functions

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Please select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Guest");
            System.out.println("4. Exit");

            int loginChoice = scanner.nextInt();
            scanner.nextLine();

            switch (loginChoice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    guestPortal(scanner);
                    break;
                case 4:
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

    // Login function
    private static void login(Scanner scanner) {
        boolean loginSuccess = false;
        while (!loginSuccess) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            String hashedPassword = Encryption.doMD5Hashing(password);

            if (validateCredentials(username, hashedPassword)) {
                if (isAdmin(username, hashedPassword)) {
                    System.out.println("Login successful! Welcome admin.");
                    adminPortal(scanner);
                } else {
                    System.out.println("Login successful! Welcome " + username);
                    userPortal(username);
                }
                loginSuccess = true;
            } else {
                System.out.println("Invalid username or password.");
                System.out.println("1. Try again");
                System.out.println("2. Go back to main menu");

                int retryChoice = scanner.nextInt();
                scanner.nextLine();
                if (retryChoice == 2) break;
            }
        }
    }

    // Register function with auto-generated ID key
    private static void register(Scanner scanner) {
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();

        if (isUsernameTaken(username)) {
            System.out.println("Username already exists. Please try again.");
        } else {
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();
            System.out.println("Enter your full name:");
            String fullName = scanner.nextLine();
            System.out.println("Enter your phone number:");
            String phoneNumber = scanner.nextLine();
            System.out.println("Enter your email:");
            String email = scanner.nextLine();

            String hashedPassword = Encryption.doMD5Hashing(password);
            int idKey = getNextIdKey(); // Auto-generate ID key
            saveNewUser(username, hashedPassword, fullName, phoneNumber, email, idKey);
            System.out.println("Registration successful! Your ID key is: " + idKey + ". You can now log in.");
        }
    }

    // User Portal
    private static void userPortal(String username) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("====User Portal====");
            System.out.println("1. Update Profile");
            System.out.println("2. View Scrolls");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    updateUserProfile(username, scanner);
                    break;
                case 2:
                    scrollManager.listScrolls();
                    break;
                case 3:
                    System.out.println("Exiting User Portal.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Update user profile (password, phone, email)
    private static void updateUserProfile(String username, Scanner scanner) {
        System.out.println("Update Profile for " + username);
        System.out.println("1. Change Password");
        System.out.println("2. Update Phone Number");
        System.out.println("3. Update Email");
        System.out.println("4. Back");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter new password:");
                String newPassword = scanner.nextLine();
                String hashedPassword = Encryption.doMD5Hashing(newPassword);
                updateUserField(username, 1, hashedPassword); // Update password in the file
                System.out.println("Password updated.");
                break;
            case 2:
                System.out.println("Enter new phone number:");
                String newPhone = scanner.nextLine();
                updateUserField(username, 3, newPhone);
                System.out.println("Phone number updated.");
                break;
            case 3:
                System.out.println("Enter new email:");
                String newEmail = scanner.nextLine();
                updateUserField(username, 4, newEmail);
                System.out.println("Email updated.");
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void adminPortal(Scanner scanner) {
        boolean keepRunning = true; // Flag to control loop execution
    
        while (keepRunning) {
            System.out.println("====Admin Portal====");
            System.out.println("1. View User Profiles");
            System.out.println("2. Add User Profiles");
            System.out.println("3. Delete User Profiles");
            System.out.println("4. Update User Profiles");
            System.out.println("5. View Stats");
            System.out.println("6. View Scrolls"); // Uses ScrollManager
            System.out.println("7. Add Scrolls");   // Uses ScrollManager
            System.out.println("8. Remove Scrolls");// Uses ScrollManager
            System.out.println("9. Edit Scrolls");  // Uses ScrollManager
            System.out.println("10: Exit");
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()
    
            switch (choice) {
                case 1:
                    viewUserProfiles();
                    break;
                case 2:
                    addUserProfile(scanner);
                    break;
                case 3:
                    deleteUserProfile(scanner);
                    break;
                case 4:
                    updateUserProfileAdmin(scanner);
                    break;
                case 5:
                    viewStats();
                    break;
                case 6:
                    scrollManager.listScrolls();
                    break;
                case 7:
                    scrollManager.addScroll();
                    break;
                case 8:
                    scrollManager.removeScroll();
                    break;
                case 9:
                    scrollManager.editScroll();
                    break;
                case 10:
                    System.out.println("Exiting Admin Management.");
                    keepRunning = false; // Exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
    
    // Guest Portal
    private static void guestPortal(Scanner scanner) {
        boolean isGuestRunning = true;

        while (isGuestRunning) {
            System.out.println("====Guest Portal====");
            System.out.println("Limited access granted.");
            System.out.println("1. View Scrolls");
            System.out.println("2. Exit to Main Menu");

            int guestChoice = scanner.nextInt();
            scanner.nextLine();

            switch(guestChoice) {
                case 1: 
                    System.out.println("====Viewing Available Scrolls (Guest Access)====");
                    scrollManager.listScrolls(); // Guests can only view, no download/upload
                    break;
                case 2: 
                    System.out.println("Exiting Guest Portal.");
                    isGuestRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option");
            }
        }
    }

    // Check if username is taken
    private static boolean isUsernameTaken(String username) {
        return checkFieldExists(username, 0); // Check username in column 0
    }

    // Validate credentials
    private static boolean validateCredentials(String username, String hashedPassword) {
        return checkCredentials(username, hashedPassword);
    }

    // Save new user with detailed profile
    private static void saveNewUser(String username, String hashedPassword, String fullName, String phone, String email, int idKey) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + ":" + hashedPassword + ":" + fullName + ":" + phone + ":" + email + ":" + idKey);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to user file.");
        }
    }

    //Admin functionality 1: View User Profiles
    private static void viewUserProfiles() {
        System.out.println("==== User Profiles ====");
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                System.out.println("ID: " + userDetails[5]);
                System.out.println("Username: " + userDetails[0]);
                System.out.println("Full Name: " + userDetails[2]);
                System.out.println("Phone: " + userDetails[3]);
                System.out.println("Email: " + userDetails[4]);
                System.out.println("---------------------");
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
    }
    
    //Admin functionality 2: Add User Profiles
    private static void addUserProfile(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        if (isUsernameTaken(username)) {
            System.out.println("Username already exists.");
            return;
        }
    
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter full name:");
        String fullName = scanner.nextLine();
        System.out.println("Enter phone number:");
        String phone = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
    
        String hashedPassword = Encryption.doMD5Hashing(password);
        int idKey = getNextIdKey();
        saveNewUser(username, hashedPassword, fullName, phone, email, idKey);
        System.out.println("User added successfully.");
    }
    
    //Admin functionality 3: Delete User Profiles
    private static void deleteUserProfile(Scanner scanner) {
        System.out.println("Enter the username of the user to delete:");
        String usernameToDelete = scanner.nextLine();
    
        List<String> users = new ArrayList<>();
        boolean userDeleted = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (!userDetails[0].equals(usernameToDelete)) {
                    users.add(line);
                } else {
                    userDeleted = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
    
        if (userDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
                for (String user : users) {
                    writer.write(user);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to user file.");
            }
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    //Admin functionality 4: Update User Profile
    private static void updateUserProfileAdmin(Scanner scanner) {
        System.out.println("Enter the username of the user to update:");
        String username = scanner.nextLine();
    
        if (!isUsernameTaken(username)) {
            System.out.println("User not found.");
            return;
        }
    
        System.out.println("1. Change Password");
        System.out.println("2. Update Phone Number");
        System.out.println("3. Update Email");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        switch (choice) {
            case 1:
                System.out.println("Enter new password:");
                String newPassword = scanner.nextLine();
                String hashedPassword = Encryption.doMD5Hashing(newPassword);
                updateUserField(username, 1, hashedPassword);
                System.out.println("Password updated.");
                break;
            case 2:
                System.out.println("Enter new phone number:");
                String newPhone = scanner.nextLine();
                updateUserField(username, 3, newPhone);
                System.out.println("Phone number updated.");
                break;
            case 3:
                System.out.println("Enter new email:");
                String newEmail = scanner.nextLine();
                updateUserField(username, 4, newEmail);
                System.out.println("Email updated.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    //Admin functionality 5: View Stats
    private static void viewStats() {
        int userCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            while (reader.readLine() != null) {
                userCount++;
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        System.out.println("Total number of users: " + userCount);
    }
    
    
    

    // Update a specific field in the user's profile
    private static void updateUserField(String username, int fieldIndex, String newValue) {
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails[0].equals(username)) {
                    userDetails[fieldIndex] = newValue;
                    users.add(String.join(":", userDetails));
                } else {
                    users.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }

        // Write updated data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to user file.");
        }
    }

    // Helper method to get the next available ID key
    private static int getNextIdKey() {
        int highestId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                int idKey = Integer.parseInt(userDetails[5]); // ID key is the 6th field
                if (idKey > highestId) {
                    highestId = idKey;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return highestId + 1; // Return the next available ID key
    }

    // Helper methods to check if fields exist
    private static boolean checkFieldExists(String value, int fieldIndex) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails[fieldIndex].equals(value)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false;
    }

    // Validate username and password by checking the file
    private static boolean checkCredentials(String username, String hashedPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails[0].equals(username) && userDetails[1].equals(hashedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false;
    }

    // Check if user is admin
    private static boolean isAdmin(String username, String hashedPassword) {
        return username.equals(ADMIN_USERNAME) && hashedPassword.equals(ADMIN_PASSWORD_HASHED);
    }
}
