package src;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ContactApp {
//    Calling from the .txt file
    private static final Scanner scanner = new Scanner(System.in);
    protected static final List<contact> Contacts = new ArrayList<>();
    private static final Path fileInsert = Path.of("src", "contact.txt");

    public static void main(String[] args) {
// to run all the apps together
        validateFile();
        loadContacts();
        runApp();

    }
    public static void runApp() {
        boolean running = true;

        while (running) {
            System.out.println("1. View contacts.\n2. Add a new contact.\n3. Search a contact by name.\n4. Delete an existing contact.\n5. Exit.\nEnter an option (1, 2, 3, 4 or 5):\n");

            // Add the switch statement here
            int Answer = scanner.nextInt();
            scanner.nextLine();
            switch (Answer) {
                case 1 -> AllContacts();
                case 2 -> addContacts();
                case 3 -> searchContact();
                case 4 -> deleteContact();
                default -> {
                    if (Answer == 5) {
                        running = false;
                    } else {
                        System.out.println("Please enter a valid menu option");
                    }

                }
            }if (running) {
                System.out.println("continue...? (y/n)");
                if (scanner.nextLine().equalsIgnoreCase("n")) {
                    running = false;
                }
            }
            if (!running) System.out.println("Have a nice day!");
        }
    }


    public  static String formatNumber(String num) {
        String fnumber = num;
        if (num.length() == 7) {
            fnumber = num.replaceFirst("(\\d{3})(\\d+)", "$1-$2");
        } else if (num.length() == 10) {
            fnumber = num.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        } else if (num.length() > 10) {
            fnumber = "+" + num.substring(0, num.length() - 10) + " " + num.substring(num.length() - 10).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        }
        return fnumber;
    }


    public static void deleteContact(){
        contact toRemove = null;
        System.out.println("Enter a name of contact to delete.");
        String isName = scanner.nextLine();
        for (contact c : Contacts){
            if (c.getName().equalsIgnoreCase(isName)){
                toRemove = c;
            }
        }
        if (toRemove == null){
            System.out.println("invalid name");
        } else {
            Contacts.remove(toRemove);
            System.out.println("contact deleted");
            saveContacts();
        }

    }

    public static void searchContact(){
        boolean isSuccessful = false;
        System.out.println("Enter name of contact to search");
        String theName = scanner.nextLine();
        for (contact c : Contacts) {
            if (c.getName().equalsIgnoreCase(theName)) {
//                System.out.printf();
                isSuccessful = true;
                break;
            }
        }
        if (!isSuccessful) System.out.println("Invalid name");
    }
    public static void AllContacts(){
        if (Contacts.size() >= 1)contactFormatter();
        else System.out.println("No Contacts found....");
    }

    private static void validateFile() {
        if (!Files.exists(fileInsert)) {
            try {
                Files.createFile(fileInsert);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static void loadContacts() {
        Contacts.clear();
        List<String> newList = new ArrayList<>();
        try {
            newList = Files.readAllLines(fileInsert);
        } catch (IOException iox) {
            iox.printStackTrace();
        }
        if (newList.size() >= 1) {
            for (String s : newList) {
                String[] arr = s.split("◈");
                contact c = new contact(arr[0], arr[1]);
                Contacts.add(c);
            }
        }
    }

    public static void saveContacts() {
        List<String> contactString = new ArrayList<>();
        for (contact c : Contacts) {
            String s = c.getName() + "◈" + c.getNumber();
            contactString.add(s);
        }
        try {
            Files.write(fileInsert, contactString);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static boolean validatePhoneNumber(String str)
    {
        if(str.length() < 7 || str.length() > 13)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
    public static boolean validateName(String str)
    {
        if (str.length() == 0 || str.length() > 20)
            return false;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '◈')
                return false;
        }
        return true;
    }
    public static void addContacts(){
    String outName = null;
    String outNumber = null;
    while (outName == null){
        System.out.println("Enter a Name.....");
        String uName = scanner.nextLine();
        if (!validateName(uName)) {
            System.out.println("Please enter a valid name");
        } else {
            outName = uName;
        }
    }
        while (outNumber == null){
            System.out.println("Enter Number...");
            String uNumber = scanner.nextLine();
            if (!validatePhoneNumber(uNumber)) {
                System.out.println("Please enter a valid phone number");
            }else{
                outNumber = uNumber;
            }
        }
        boolean passable = true;
        for(contact c : Contacts){
            if(c.getName().equalsIgnoreCase(outName)){
                passable = false;
                System.out.println("Name already exists in contacts! please try again!");
            }
            if(c.getNumber().equals(outNumber)){
                passable = false;
                System.out.println("Number already exists in contacts! please try again!");
            }
        }
        if(passable) {
            contact c = new contact(outName, outNumber);
            System.out.println("Contact for added!");
            Contacts.add(c);
            saveContacts();
        }
    }

    public static void contactFormatter() {
        System.out.println("┌───────────────────┬───────────────────┐");
        System.out.printf("│%-19s│%-19s│%n", "Name","Phone Number");
        System.out.println("┝━━━━━━━━━━━━━━━━━━━┿━━━━━━━━━━━━━━━━━━━┥");
        for (contact c : Contacts) {
            System.out.printf("│%s%-19s%s│%s%-19s%s│%n", "| ", c.getName(), " |", "| ", formatNumber(c.getNumber()), " |");

        }
        System.out.println("└───────────────────┴───────────────────┘");
    }


}

