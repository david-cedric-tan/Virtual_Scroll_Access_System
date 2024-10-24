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
        fullDateString = formattedDate.substring(0, 10) + " 00:00:00" + formattedDate.substring(19, 29);

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
    
}