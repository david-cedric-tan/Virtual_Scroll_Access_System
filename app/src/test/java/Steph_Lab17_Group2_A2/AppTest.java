package Steph_Lab17_Group2_A2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Steph_Lab17_Group2_A2.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeEach
    void setUpStreams() {

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
    }

    //@AfterAll
    // void cleanUpAfterAllTests() {

    //     //clean old data
    //     String userInput = "luke\n";
    //     ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
    //     Scanner scanner = new Scanner(input);
    //     App.deleteUserProfile(scanner);

    //     userInput = "obiwan\n";
    //     input = new ByteArrayInputStream(userInput.getBytes());
    //     scanner = new Scanner(input);
    //     App.deleteUserProfile(scanner);
    // }

    // POSITIVE CASES
    @Test
    void testLogin() {
        String userInput = "admin\nadmin\n12\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.login(scanner);

        // Check the expected output
        String expectedOutput = "Enter username:\nEnter password:\nLogin successful! Welcome admin.\n====Admin Portal====\n1. View User Profiles\n2. Add User Profiles\n3. Delete User Profiles\n4. Update User Profiles\n5. View Stats\n6. View Scrolls\n7. Add Scrolls\n8. Remove Scrolls\n9. Edit Scrolls\n10. Search Scrolls\n11. Spectator Mode\n12: Exit\nExiting Admin Management.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testLoginInvalidPassword() {
        String userInput = "admin\nadminx\n2\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.login(scanner);

        // Check the expected output
        String expectedOutput = "Enter username:\nEnter password:\nInvalid username or password.\n1. Try again\n2. Go back to main menu";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


    // Test login
    @Test
    void testRegister() {
        String userInput = "luke\nluke\nluke skywalker\n0412891189\nluke@gmail.com\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        int id = App.getNextIdKey();
        App.register(scanner);

        // Check the expected output
        String expectedOutput = "Please enter your username:\nPlease enter your password:\nEnter your full name:\nEnter your phone number:\nEnter your email:\nRegistration successful! Your ID key is: " + Integer.toString(id) + ". You can now log in.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    // Test add User profiles
    @Test
    void testAddUserProfile() {
        String userInput = "obiwan\nobiwan\n0412323232\nobliwan@gmail.com\n11\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.addUserProfile(scanner);

        // Check the expected output
        String expectedOutput = "Enter username:\nEnter password:\nEnter full name:\nEnter phone number:\nEnter email:\nUser added successfully.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    // Test update User profiles
    @Test
    void testUpdateUserProfile1() {
        String userInput = "1\nlukey\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.updateUserProfile("luke",scanner);

        // Check the expected output
        String expectedOutput = "Update Profile for luke\n1. Change Password\n2. Update Phone Number\n3. Update Email\n4. Change Unique ID\n5. Back\nEnter new password:\nPassword updated.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testUpdateUserProfile2() {
        String userInput = "2\n0416232222\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.updateUserProfile("luke",scanner);

        // Check the expected output
        String expectedOutput = "Update Profile for luke\n1. Change Password\n2. Update Phone Number\n3. Update Email\n4. Change Unique ID\n5. Back\nEnter new phone number:\nPhone number updated.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testUpdateUserProfile3() {
        String userInput = "3\nlukey@gmail.com\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.updateUserProfile("luke",scanner);

        // Check the expected output
        String expectedOutput = "Update Profile for luke\n1. Change Password\n2. Update Phone Number\n3. Update Email\n4. Change Unique ID\n5. Back\nEnter new email:\nEmail updated.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


    // Test delete User profiles
    @Test
    void testDeleteUserProfile() {
        String userInput = "luke\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.deleteUserProfile(scanner);

        // Check the expected output
        String expectedOutput = "Enter the username of the user to delete:\nUser deleted successfully.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testDeleteUserProfileNotFound() {
        String userInput = "lukeye\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.deleteUserProfile(scanner);

        // Check the expected output
        String expectedOutput = "Enter the username of the user to delete:\nUser not found.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


    // Test update User profile Admin
    @Test
    void testUpdateUserProfileAdmin1() {
        String userInput = "yoda\n1\nyodanew\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.updateUserProfileAdmin(scanner);

        // Check the expected output
        String expectedOutput = "Enter the username of the user to update:\n1. Change Password\n2. Update Phone Number\n3. Update Email\n4. Update User ID\n5. Back.\nEnter new password:\nPassword updated.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testUpdateUserProfileAdmin2() {
        String userInput = "yoda\n2\n04333333333\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.updateUserProfileAdmin(scanner);

        // Check the expected output
        String expectedOutput = "Enter the username of the user to update:\n1. Change Password\n2. Update Phone Number\n3. Update Email\n4. Update User ID\n5. Back.\nEnter new phone number:\nPhone number updated.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testUpdateUserProfileAdmin3() {
        String userInput = "yoda\n3\nyodanew@gmail.com\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.updateUserProfileAdmin(scanner);

        // Check the expected output
        String expectedOutput = "Enter the username of the user to update:\n1. Change Password\n2. Update Phone Number\n3. Update Email\n4. Update User ID\n5. Back.\nEnter new email:\nEmail updated.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testViewUserProfiles() {
        App.viewUserProfiles();

        // Check the expected output
        String expectedOutput = "==== User Profiles ====ID: 1Username: adminFull Name: adminPhone: adminEmail: admin---------------------ID: 2Username: yodaFull Name: yoda the toadPhone: 04333333333Email: yodanew@gmail.com---------------------ID: 3Username: 4Full Name: 4Phone: 4Email: 44---------------------ID: 4Username: obiwanFull Name: 0412323232Phone: obliwan@gmail.comEmail: 11---------------------ID: 5Username: lukeFull Name: luke skywalkerPhone: 0412891189Email: luke@gmail.com---------------------";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testViewStats() {
        App.viewStats();

        // Check the expected output
        String expectedOutput = "Total number of users: 4";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testSpectatorMode1() {
        String userInput = "11\n13\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.spectatorMode(scanner);

        // Check the expected output
        String expectedOutput = "====Spectator Mode====\n1. View as Guest\n2. View as User\n3. Return to Admin Portal\nInvalid choice. Please select a valid option.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testSpectatorMode2() {
        String userInput = "22\nyoda\n13\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.spectatorMode(scanner);

        // Check the expected output
        String expectedOutput = "====Spectator Mode====\n1. View as Guest\n2. View as User\n3. Return to Admin Portal\nInvalid choice. Please select a valid option.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testSpectatorMode3() {
        String userInput = "33\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.spectatorMode(scanner);

        // Check the expected output
        String expectedOutput = "====Spectator Mode====\n1. View as Guest\n2. View as User\n3. Return to Admin Portal\nInvalid choice. Please select a valid option.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    
    @Test
    void testGuestPortal() {
        String userInput = "2\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.guestPortal(scanner);

        // Check the expected output
        String expectedOutput = "====Guest Portal====\nLimited access granted.\n1. View Scrolls\n2. Exit to Main Menu\nExiting Guest Portal.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testAdminPortal() {
        String userInput = "12\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        App.adminPortal(scanner);

        // Check the expected output
        String expectedOutput = "====Admin Portal====\n1. View User Profiles\n2. Add User Profiles\n3. Delete User Profiles\n4. Update User Profiles\n5. View Stats\n6. View Scrolls\n7. Add Scrolls\n8. Remove Scrolls\n9. Edit Scrolls\n10. Search Scrolls\n11. Spectator Mode\n12: Exit\nExiting Admin Management.";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


}