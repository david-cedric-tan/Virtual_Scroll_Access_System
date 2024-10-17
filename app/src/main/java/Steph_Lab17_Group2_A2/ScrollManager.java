package Steph_Lab17_Group2_A2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// ScrollManager class to manage scroll operations
public class ScrollManager {

    // A Map to store scrolls (ID -> Scroll object)
    private Map<String, Scroll> scrolls = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // Scroll class to represent a Scroll object
    public class Scroll {
        private String id;
        private String name;
        private File binaryFile;

        public Scroll(String id, String name, File binaryFile) {
            this.id = id;
            this.name = name;
            this.binaryFile = binaryFile;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public File getBinaryFile() {
            return binaryFile;
        }

        public void setBinaryFile(File binaryFile) {
            this.binaryFile = binaryFile;
        }

        @Override
        public String toString() {
            return "Scroll [ID=" + id + ", Name=" + name + "]";
        }
    }

    // Method to add new scroll
    public void addScroll() {
        System.out.println("Enter Scroll Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Scroll ID:");
        String id = scanner.nextLine();

        // Uploading binary file (simulating binary upload)
        System.out.println("Enter the path of the binary file:");
        String filePath = scanner.nextLine();
        File binaryFile = new File(filePath);

        if (!binaryFile.exists()) {
            System.out.println("File not found. Please try again.");
            return;
        }

        // Add scroll to the library
        Scroll newScroll = new Scroll(id, name, binaryFile);
        scrolls.put(id, newScroll);
        System.out.println("Scroll added successfully.");
    }

    // Method to edit a scroll
    public void editScroll() {
        System.out.println("Enter Scroll ID to edit:");
        String id = scanner.nextLine();

        Scroll scroll = scrolls.get(id);
        if (scroll == null) {
            System.out.println("Scroll not found.");
            return;
        }

        System.out.println("Editing Scroll: " + scroll.getName());
        System.out.println("1. Edit Name");
        System.out.println("2. Edit Binary File");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Enter new Scroll Name:");
                String newName = scanner.nextLine();
                scroll.setName(newName);
                System.out.println("Scroll name updated.");
                break;

            case 2:
                System.out.println("Enter the path of the new binary file:");
                String newFilePath = scanner.nextLine();
                File newBinaryFile = new File(newFilePath);
                if (!newBinaryFile.exists()) {
                    System.out.println("File not found. Please try again.");
                } else {
                    scroll.setBinaryFile(newBinaryFile);
                    System.out.println("Binary file updated.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to remove a scroll
    public void removeScroll() {
        System.out.println("Enter Scroll ID to remove:");
        String id = scanner.nextLine();

        if (scrolls.remove(id) != null) {
            System.out.println("Scroll removed successfully.");
        } else {
            System.out.println("Scroll not found.");
        }
    }

    // Method to list all scrolls
    public void listScrolls() {
        System.out.println(scrolls);
        if (scrolls.isEmpty()) {
            System.out.println("No scrolls available.");
            return;
        }

        System.out.println("Available Scrolls:");
        for (Scroll scroll : scrolls.values()) {
            System.out.println(scroll);
        }
    }

    // // Method to display the scroll management menu
    // public void scrollMenu() {
    //     while (true) {
    //         System.out.println("\n--- Scroll Management Menu ---");
    //         System.out.println("1. Add New Scroll");
    //         System.out.println("2. Edit Existing Scroll");
    //         System.out.println("3. Remove Scroll");
    //         System.out.println("4. List All Scrolls");
    //         System.out.println("5. Exit");
    //         System.out.print("Please select an option: ");

    //         int choice = scanner.nextInt();
    //         scanner.nextLine(); // Consume the newline character after nextInt()

    //         switch (choice) {
    //             case 1:
    //                 // Add new scroll
    //                 addScroll();

    //                 // test adding new file: app/bin/main/Steph_Lab17_Group2_A2/test.bin
    //                 break;
    //             case 2:
    //                 // Edit existing scroll
    //                 editScroll();
    //                 break;
    //             case 3:
    //                 // Remove a scroll
    //                 removeScroll();
    //                 break;
    //             case 4:
    //                 // List all scrolls
    //                 listScrolls();
    //                 break;
    //             case 5:
    //                 // Exit the menu
    //                 System.out.println("Exiting Scroll Management.");
    //                 return; // Exit the method to go back to the main program
    //             default:
    //                 System.out.println("Invalid choice. Please select a valid option.");
    //         }
    //     }
    // }

}
