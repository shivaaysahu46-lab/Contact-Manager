import java.util.*;

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("Name: %-15s | Phone: %-12s | Email: %s", name, phone, email);
    }
}


public class ContactManager {
    
    private static ArrayList<Contact> contacts = new ArrayList<>();
    
    
    private static LinkedList<String> recentHistory = new LinkedList<>();
    private static final int MAX_HISTORY = 5;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getValidIntInput("Choose an option (1-6): ");

            switch (choice) {
                case 1 -> addContact();
                case 2 -> viewAllContacts();
                case 3 -> searchContact();
                case 4 -> deleteContact();
                case 5 -> viewRecentHistory();
                case 6 -> {
                    System.out.println("Exiting Contact Manager. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== CONTACT LIST MANAGER ===");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. View Recent Activity (Last 5)");
        System.out.println("6. Exit");
    }

    

    private static void addContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        contacts.add(new Contact(name, phone, email));
        logActivity("Added contact: " + name);
        System.out.println("Contact added successfully!");
    }

    private static void viewAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Your contact list is empty.");
            return;
        }
        System.out.println("\n--- All Contacts (" + contacts.size() + ") ---");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
    }

    
    private static void searchContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to search.");
            return;
        }

        System.out.print("Enter name or phone number to search: ");
        String query = scanner.nextLine().trim().toLowerCase();
        logActivity("Searched for: \"" + query + "\"");

        boolean found = false;
        System.out.println("\n--- Search Results ---");

    
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(query) || c.getPhone().contains(query)) {
                System.out.println(c);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching contacts found.");
        }
    }

    private static void deleteContact() {
        viewAllContacts();
        if (contacts.isEmpty()) return;

        int index = getValidIntInput("Enter the contact number to delete (0 to cancel): ");
        if (index == 0) return;

        if (index >= 1 && index <= contacts.size()) {
            Contact removed = contacts.remove(index - 1);
            logActivity("Deleted contact: " + removed.getName());
            System.out.println("Deleted: " + removed.getName());
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    

    private static void logActivity(String action) {
    
        recentHistory.addFirst(action);

    
        if (recentHistory.size() > MAX_HISTORY) {
            recentHistory.removeLast();
        }
    }

    private static void viewRecentHistory() {
        if (recentHistory.isEmpty()) {
            System.out.println("No recent activity yet.");
            return;
        }
        System.out.println("\n--- Recent Activity (Newest First) ---");
        for (String log : recentHistory) {
            System.out.println("- " + log);
        }
    }

    
    private static int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
