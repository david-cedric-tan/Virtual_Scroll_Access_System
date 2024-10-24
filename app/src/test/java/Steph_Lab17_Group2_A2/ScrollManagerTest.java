package Steph_Lab17_Group2_A2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Steph_Lab17_Group2_A2.ScrollManager;
import Steph_Lab17_Group2_A2.ScrollManager.Scroll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class ScrollManagerTest {
    private ScrollManager sm;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private String dateString; //dd-MM-yyyy
    private String monthString; //MMM dd
    private String yearString; //yyyy
    private String fullDateString; // EEE MMM dd HH:mm:ss zzz yyyy


    @BeforeEach
    void setUpStreams() {
        sm = new ScrollManager();
        // add one scroll as default
        String userInput = "scroll1\n1\ntest.bin\nyoda\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.addScroll();

        // get current date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateString = formatter.format(date);

        formatter = new SimpleDateFormat("MMM dd");
        monthString = formatter.format(date);

        formatter = new SimpleDateFormat("yyyy");
        yearString = formatter.format(date);

        formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // use to remove hh:mm:ss and replaced with 00:00:00
        String formattedDate = formatter.format(date);
        fullDateString = formattedDate.substring(0, 11) + "00:00:00" + formattedDate.substring(19);

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
    }

    // Test add scroll
    @Test
    void testAddScroll() {
        String userInput = "scroll2\n2\ntest.bin\nyoda\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.addScroll();

        // Check the expected output
        String expectedOutput = "Enter Scroll Name:\nEnter Scroll ID:\nEnter the path of the binary file:\nEnter your user ID:\n Scroll added successfully.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testAddScrollFileNotFound() {
        String userInput = "scroll2\n2\nnotfound.bin\nyoda\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.addScroll();

        // Check the expected o utput
        String expectedOutput = "Enter Scroll Name:\nEnter Scroll ID:\nEnter the path of the binary file:\nFile not found. Please try again.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    // Test edit scroll
    @Test
    void testEditScrollName() {
        String userInput = "1\n1\nscroll1a\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.editScroll();

        String expectedOutput = "Enter Scroll ID to edit:\nEditing Scroll: scroll1\n1. Edit Name\n2. Edit Binary File\nEnter new Scroll Name:\nScroll name updated.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testEditScrollFile() {
        String userInput = "1\n2\ntest.bin\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.editScroll();

        // Check the expected output
        String expectedOutput = "Enter Scroll ID to edit:\nEditing Scroll: scroll1\n1. Edit Name\n2. Edit Binary File\nEnter the path of the new binary file:\nBinary file updated.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }

    @Test
    void testEditScrollFileNotFound() {
        String userInput = "1\n2\nnotfound.bin\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.editScroll();

        // Check the expected output
        String expectedOutput = "Enter Scroll ID to edit:\nEditing Scroll: scroll1\n1. Edit Name\n2. Edit Binary File\nEnter the path of the new binary file:\nFile not found. Please try again.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


    // Test search scroll
    @Test
    void testSearchScrollByID() {
        String userInput = "1\n1\n1\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.searchScrolls();

        String expectedOutput = "Choose a search filter:\n1. Search by Scroll ID\n2. Search by Name\n3. Search by Uploader ID\n4. Search by Upload Date\nEnter Scroll ID:\n" +
                "Scroll ID  Scroll Name          Binary File Path               Uploader ID     Upload Date    \n" +
                "----------------------------------------------------------------------------------------------------\n" +
                "1          scroll1              test.bin                       yoda            " + dateString + "     \n" +
                "Would you like to download a scroll? (yes/no)\nReturning to menu.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testSearchScrollByName() {
        String userInput = "2\nscroll1\n1\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.searchScrolls();

        String expectedOutput = "Choose a search filter:\n1. Search by Scroll ID\n2. Search by Name\n3. Search by Uploader ID\n4. Search by Upload Date\nEnter Scroll Name:\n" +
                "Scroll ID  Scroll Name          Binary File Path               Uploader ID     Upload Date    \n" +
                "----------------------------------------------------------------------------------------------------\n" +
                "1          scroll1              test.bin                       yoda            " + dateString + "     \n" +
                "Would you like to download a scroll? (yes/no)\nReturning to menu.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testSearchScrollByUploadID() {
        String userInput = "3\nyoda\n1\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.searchScrolls();

        String expectedOutput = "Choose a search filter:\n1. Search by Scroll ID\n2. Search by Name\n3. Search by Uploader ID\n4. Search by Upload Date\nEnter Uploader ID:\n" +
                "Scroll ID  Scroll Name          Binary File Path               Uploader ID     Upload Date    \n" +
                "----------------------------------------------------------------------------------------------------\n" +
                "1          scroll1              test.bin                       yoda            " + dateString + "     \n" +
                "Would you like to download a scroll? (yes/no)\nReturning to menu.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testSearchScrollByUploadDate() {
        String userInput = "4\n" + dateString + "\n1\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.searchScrolls();

        String expectedOutput = "Choose a search filter:\n1. Search by Scroll ID\n2. Search by Name\n3. Search by Uploader ID\n4. Search by Upload Date\nEnter Upload Date (dd-MM-yyyy):\n" +
                "Comparing Upload Date: " + fullDateString + " with Search Date: " + fullDateString + "\n" +
                "Scroll ID  Scroll Name          Binary File Path               Uploader ID     Upload Date    \n" +
                "----------------------------------------------------------------------------------------------------\n" +
                "1          scroll1              test.bin                       yoda            " + dateString + "     \n" +
                "Would you like to download a scroll? (yes/no)\nReturning to menu.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


    // Test remove scroll
    @Test
    void testRemoveScroll() {
        String userInput = "1\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.removeScroll();

        // Check the expected output
        String expectedOutput = "Enter Scroll ID to remove:\nScroll removed successfully.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testRemoveScrollNotFound() {
        String userInput = "6\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.removeScroll();

        // Check the expected output
        String expectedOutput = "Enter Scroll ID to remove:\nScroll not found.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }


    // Test list scroll
    @Test
    void testListScrolls() {
        String userInput = "no\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.listScrolls();

        // Check the expected output
        String expectedOutput = "Scroll ID  Scroll Name          Binary File Path              \n" +
                "--------------------------------------------------------------\n" +
                "1          scroll1              test.bin                      \n" +
                "Would you like to download a scroll? (yes/no)\nReturning to menu.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
    @Test
    void testListScrollsEmpty() {
        String userInput = "1\n";
        ByteArrayInputStream input = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.removeScroll();

        userInput = "no\n";
        input = new ByteArrayInputStream(userInput.getBytes());
        scanner = new Scanner(input);
        sm.SetScanner(scanner);
        sm.listScrolls();

        // Check the expected output
        String expectedOutput =  "Enter Scroll ID to remove:\nScroll removed successfully.\n" +
                "No scrolls available.\n";
        assertEquals(expectedOutput.replace("\r\n", "\n").trim().replace("\n",""), outContent.toString().replace("\r\n", "\n").trim().replace("\n",""), "passed");
    }
}
