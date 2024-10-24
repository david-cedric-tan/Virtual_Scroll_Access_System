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
    

    @BeforeEach
    void setUpStreams() {
        sm = new ScrollManager();

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
}