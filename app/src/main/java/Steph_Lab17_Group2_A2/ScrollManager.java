package Steph_Lab17_Group2_A2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// ScrollManager class to manage scroll operations
public class ScrollManager {

    // A Map to store scrolls (ID -> Scroll object)
    private Map<String, Scroll> scrolls = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void SetScanner(Scanner scan){
        this.scanner = scan;
    }

    // Scroll class to represent a Scroll object
    // Scroll class to represent a Scroll object
    public class Scroll {
        private String id;
        private String name;
        private File binaryFile;
        private String uploaderID;
        private Date uploadDate;

        public Scroll(String id, String name, File binaryFile, String uploaderID, Date uploadDate) {
            this.id = id;
            this.name = name;
            this.binaryFile = binaryFile;
            this.uploaderID = uploaderID;
            this.uploadDate = uploadDate;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUploaderID(){
            return this.uploaderID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public File getBinaryFile() {
            return binaryFile;
        }

        public Date getUploadDate(){
            return this.uploadDate;
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

        //Enter user ID
        System.out.println("Enter your user ID: ");
        String uploaderID = scanner.nextLine();

        //Get current date
        Date uploadDate = new Date();
        
        // Add scroll to the library
        Scroll newScroll = new Scroll(id, name, binaryFile, uploaderID, uploadDate);
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


    // Method to search for scrolls based on various criteria
    public void searchScrolls() {
        System.out.println("Choose a search filter:");
        System.out.println("1. Search by Scroll ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Uploader ID");
        System.out.println("4. Search by Upload Date");
    
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        List<Scroll> results = new ArrayList<>();
    
        switch (choice) {
            case 1:
                System.out.println("Enter Scroll ID:");
                String searchId = scanner.nextLine();
                results = filterById(searchId);
                break;
    
            case 2:
                System.out.println("Enter Scroll Name:");
                String searchName = scanner.nextLine();
                results = filterByName(searchName);
                break;
    
            case 3:
                System.out.println("Enter Uploader ID:");
                String searchUploaderId = scanner.nextLine();
                results = filterByUploaderId(searchUploaderId);
                break;
    
            case 4:
                System.out.println("Enter Upload Date (dd-MM-yyyy):"); // Changed to dd-MM-yyyy
                String searchDate = scanner.nextLine();
                results = filterByUploadDate(searchDate);
                break;
    
            default:
                System.out.println("Invalid choice.");
                return; // Exit the method for invalid choice
        }
    
        // Displaying the results
        if (results.isEmpty()) {
            System.out.println("No scrolls found matching the criteria.");
        } else {
            System.out.printf("%-10s %-20s %-30s %-15s %-15s%n", "Scroll ID", "Scroll Name", "Binary File Path", "Uploader ID", "Upload Date");
            System.out.println("----------------------------------------------------------------------------------------------------");
    
            for (Scroll scroll : results) {
                System.out.printf("%-10s %-20s %-30s %-15s %-15s%n",
                        scroll.getId(),
                        scroll.getName(),
                        scroll.getBinaryFile().getPath(),
                        scroll.getUploaderID(),
                        new SimpleDateFormat("dd-MM-yyyy").format(scroll.getUploadDate())); // Format for display
            }
        }
    
        // Asking the user if they want to download a scroll
        System.out.println("\nWould you like to download a scroll? (yes/no)");
        String response = scanner.nextLine().toLowerCase();
    
        if (response.equals("yes")) {
            System.out.println("Enter the Scroll ID you want to download:");
            String scrollId = scanner.nextLine();
    
            Scroll scrollToDownload = scrolls.get(scrollId);
    
            if (scrollToDownload != null) {
                // Simulating the download process (you can add actual file operations here if needed)
                System.out.println("Downloading Scroll: " + scrollToDownload.getName());
                System.out.println("File downloaded to: " + scrollToDownload.getBinaryFile().getPath());
            } else {
                System.out.println("Scroll with ID " + scrollId + " not found.");
            }
        } else {
            System.out.println("Returning to menu.");
        }
    }
    


    private List<Scroll> filterById(String id) {
        List<Scroll> results = new ArrayList<>();
        Scroll scroll = scrolls.get(id);
        if (scroll != null) {
            results.add(scroll);
        }
        return results;
    }

    private List<Scroll> filterByName(String name) {
        List<Scroll> results = new ArrayList<>();
        for (Scroll scroll : scrolls.values()) {
            if (scroll.getName().equalsIgnoreCase(name)) {
                results.add(scroll);
            }
        }
        return results;
    }

    private List<Scroll> filterByUploaderId(String uploaderId) {
        List<Scroll> results = new ArrayList<>();
        for (Scroll scroll : scrolls.values()) {
            if (scroll.getUploaderID().equalsIgnoreCase(uploaderId)) {
                results.add(scroll);
            }
        }
        return results;
    }

    public List<Scroll> filterByUploadDate(String date) {
        List<Scroll> filteredScrolls = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        try {
            // Parse the input date string to a Date object
            Date searchDate = format.parse(date);
            
            // Set time to 00:00:00 for both dates to compare only the date
            searchDate = setToMidnight(searchDate);

            for (Scroll scroll : scrolls.values()) {
                Date uploadDate = scroll.getUploadDate();
                // Set the upload date to midnight for comparison
                uploadDate = setToMidnight(uploadDate);
                
                // Debugging output to check dates
                System.out.println("Comparing Upload Date: " + uploadDate + " with Search Date: " + searchDate);
                
                // Compare the upload date directly
                if (uploadDate.compareTo(searchDate) == 0) {
                    filteredScrolls.add(scroll);
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
        }
        
        return filteredScrolls;
    }

    // Helper method to set a Date object to midnight (00:00:00)
    private Date setToMidnight(Date date) {
        // Calendar to manipulate date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
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

    // Method to list all scrolls in a table format
    public void listScrolls() {
        if (scrolls.isEmpty()) {
            System.out.println("No scrolls available.");
            return;
        }

        // Displaying the scrolls in table format
        System.out.printf("%-10s %-20s %-30s%n", "Scroll ID", "Scroll Name", "Binary File Path");
        System.out.println("--------------------------------------------------------------");

        for (Scroll scroll : scrolls.values()) {
            System.out.printf("%-10s %-20s %-30s%n", 
                scroll.getId(), 
                scroll.getName(), 
                scroll.getBinaryFile().getPath());
        }

        // Asking the user if they want to download a scroll
        System.out.println("\nWould you like to download a scroll? (yes/no)");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("yes")) {
            System.out.println("Enter the Scroll ID you want to download:");
            String scrollId = scanner.nextLine();

            Scroll scrollToDownload = scrolls.get(scrollId);

            if (scrollToDownload != null) {
                // Simulating the download process (you can add actual file operations here if needed)
                System.out.println("Downloading Scroll: " + scrollToDownload.getName());
                System.out.println("File downloaded to: " + scrollToDownload.getBinaryFile().getPath());
            } else {
                System.out.println("Scroll with ID " + scrollId + " not found.");
            }
        } else {
            System.out.println("Returning to menu.");
        }
    }
}


    