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
        runApp();

    }
public static void runApp(){
        boolean running = true;

        while (running){
            System.out.println("1. View contacts.\n2. Add a new contact.\n3. Search a contact by name.\n4. Delete an existing contact.\n5. Exit.\nEnter an option (1, 2, 3, 4 or 5):\n");
//        Function for call which options
            int Answer = scanner.nextInt();
            scanner.nextLine();
                            case 1 -> AllContacts();
                            case 2 -> addContacts();
                            case 3 -> searchContact();
                            case 4 -> deleteContact();
            if (Answer == 5) {
                running = false;
            } else {
                System.out.println("Please enter a valid menu option");
            }
        }
//        Add a continue if user is done or not
    if (running) {
        System.out.println("%continue...?");
        if (scanner.nextLine().equalsIgnoreCase("n")){
            running = false;
        }
    }

    if(running) System.out.println("Have a nice day!");

}

    public static void deleteContact(){
        contact toRemove = null;
        System.out.println("Enter a name of contact to delete.");
        String isName = scanner.nextLine();
        for (contact c : Contacts){
            toRemove = c;
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
    public static void displayContacts(){
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
        for(Contact c : contacts){
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
            Contact c = new Contact(outName, outNumber);
            System.out.printf("%sContact for %s added!%s%n", , outName, RESET);
            contacts.add(c);
            saveContacts();
        }
    }

    public static void contactFormatter() {
        System.out.println("┌───────────────────┬───────────────────┐");
        System.out.printf("│%-19s│%-19s│%n", "Name","Phone Number");
        System.out.println("┝━━━━━━━━━━━━━━━━━━━┿━━━━━━━━━━━━━━━━━━━┥");
        for (contact c : Contacts) {
            System.out.println("│%s%-19s%s│%s%-19s%s│%n");
        }
        System.out.println("└───────────────────┴───────────────────┘");
    }


}
    }
}
