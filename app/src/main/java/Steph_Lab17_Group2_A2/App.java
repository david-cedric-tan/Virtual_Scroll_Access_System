package Steph_Lab17_Group2_A2;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class App {
    // Simulated user database (username -> password)
    private static Map<String, String> users = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println(Encryption.doMD5Hashing("1234"));
        Scanner scanner = new Scanner(System.in);

        // Add a default user for testing login
        users.put("admin", "admin123");

        // Login Portal
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Guest");

        int loginChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over from nextInt()

        // Switch case for handling login, register, and guest options
        switch (loginChoice) {
            case 1:
                // Login case
                System.out.println("Enter username:");
                String username = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();

                if (users.containsKey(username) && users.get(username).equals(password)) {
                    System.out.println("Login successful! Welcome " + username);
                } else {
                    System.out.println("Invalid username or password.");
                }
                break;

            case 2:
                // Register case
                System.out.println("Enter a new username:");
                String newUsername = scanner.nextLine();
                if (users.containsKey(newUsername)) {
                    System.out.println("Username already exists. Please try again.");
                } else {
                    System.out.println("Enter a new password:");
                    String newPassword = scanner.nextLine();
                    users.put(newUsername, newPassword);
                    System.out.println("Registration successful! You can now log in.");
                }
                break;

            case 3:
                // Guest case
                System.out.println("Proceeding as Guest. Limited access granted.");
                //provide restricted access features for guests here
                break;

            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }

        scanner.close();
    }
}
