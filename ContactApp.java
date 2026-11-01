import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class ContactApp {
    private static final Path FILE = Paths.get("data", "contacts.csv");
    private static final Scanner INPUT = new Scanner(System.in);
    private static final List<Contact> CONTACTS = new ArrayList<>();
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static void main(String[] args) {
        loadContacts();
        while (true) {
            System.out.println("\n=== Contact Management App ===");
            System.out.println("1. Add contact\n2. View all contacts\n3. Search contact\n4. Edit contact\n5. Delete contact\n0. Exit");
            String choice = ask("Choose an option: ");
            switch (choice) {
                case "1": addContact(); break;
                case "2": showContacts(CONTACTS); break;
                case "3": searchContacts(); break;
                case "4": editContact(); break;
                case "5": deleteContact(); break;
                case "0": saveContacts(); System.out.println("Contacts saved. Bye!"); return;
                default: System.out.println("Please choose one of the listed options.");
            }
        }
    }

    private static void addContact() {
        String name = ask("Name: ").trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        String phone = ask("10-digit phone: ").trim();
        if (!phone.matches("\\d{10}")) { System.out.println("Phone must contain exactly 10 digits."); return; }
        String email = ask("Email: ").trim();
        if (!EMAIL.matcher(email).matches()) { System.out.println("Please enter a valid email address."); return; }
        CONTACTS.add(new Contact(name, phone, email));
        saveContacts();
        System.out.println("Contact added.");
    }

    private static void searchContacts() {
        String query = ask("Search by name or phone: ").trim().toLowerCase(Locale.ROOT);
        List<Contact> found = new ArrayList<>();
        for (Contact c : CONTACTS) {
            if (c.name.toLowerCase(Locale.ROOT).contains(query) || c.phone.contains(query)) found.add(c);
        }
        showContacts(found);
    }

    private static void editContact() {
        Contact c = findByPhone(ask("Enter the current phone number of the contact: ").trim());
        if (c == null) { System.out.println("Contact not found."); return; }
        String name = ask("New name (Enter to keep " + c.name + "): ").trim();
        String phone = ask("New 10-digit phone (Enter to keep " + c.phone + "): ").trim();
        String email = ask("New email (Enter to keep " + c.email + "): ").trim();
        if (!phone.isEmpty() && !phone.matches("\\d{10}")) { System.out.println("Invalid phone. No changes saved."); return; }
        if (!email.isEmpty() && !EMAIL.matcher(email).matches()) { System.out.println("Invalid email. No changes saved."); return; }
        if (!name.isEmpty()) c.name = name;
        if (!phone.isEmpty()) c.phone = phone;
        if (!email.isEmpty()) c.email = email;
        saveContacts();
        System.out.println("Contact updated.");
    }

    private static void deleteContact() {
        Contact c = findByPhone(ask("Enter the phone number of the contact to delete: ").trim());
        if (c == null) { System.out.println("Contact not found."); return; }
        CONTACTS.remove(c);
        saveContacts();
        System.out.println("Contact deleted.");
    }

    private static Contact findByPhone(String phone) {
        for (Contact c : CONTACTS) if (c.phone.equals(phone)) return c;
        return null;
    }

    private static void showContacts(List<Contact> list) {
        if (list.isEmpty()) { System.out.println("No contacts found."); return; }
        System.out.printf("%-24s %-14s %s%n", "NAME", "PHONE", "EMAIL");
        System.out.println("---------------------------------------------------------------");
        for (Contact c : list) System.out.printf("%-24s %-14s %s%n", c.name, c.phone, c.email);
    }

    private static void loadContacts() {
        try {
            Files.createDirectories(FILE.getParent());
            if (!Files.exists(FILE)) return;
            for (String line : Files.readAllLines(FILE, StandardCharsets.UTF_8)) {
                String[] fields = line.split("\\|", -1);
                if (fields.length == 3) CONTACTS.add(new Contact(unescape(fields[0]), unescape(fields[1]), unescape(fields[2])));
            }
        } catch (IOException e) { System.out.println("Could not read contacts: " + e.getMessage()); }
    }

    private static void saveContacts() {
        try {
            Files.createDirectories(FILE.getParent());
            List<String> lines = new ArrayList<>();
            for (Contact c : CONTACTS) lines.add(escape(c.name) + "|" + escape(c.phone) + "|" + escape(c.email));
            Files.write(FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException e) { System.out.println("Could not save contacts: " + e.getMessage()); }
    }

    private static String escape(String value) { return value.replace("\\", "\\\\").replace("|", "\\|").replace("\n", " "); }
    private static String unescape(String value) { return value.replace("\\|", "|").replace("\\\\", "\\"); }
    private static String ask(String prompt) { System.out.print(prompt); return INPUT.nextLine(); }

    private static class Contact {
        private String name;
        private String phone;
        private String email;
        private Contact(String name, String phone, String email) { this.name = name; this.phone = phone; this.email = email; }
    }
}
