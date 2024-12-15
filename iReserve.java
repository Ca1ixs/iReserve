import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract class Inventory {
    private String type;

    Inventory(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract int getStock(int index);

    public abstract void reduceStock(int index);
}

class Books extends Inventory {
    private static int[] bookStock = { 10, 11, 3, 15, 6, 24, 5, 21, 15, 22, 12, 23, 28, 27, 6, 28, 4, 8, 25, 12, 11, 22,
            22, 1, 1, 21, 13, 5, 11, 6, 2, 14 };

    public Books() {
        super("Books");
    }

    @Override
    public int getStock(int index) {
        return bookStock[index];
    }

    @Override
    public void reduceStock(int index) {
        if (bookStock[index] > 0) {
            bookStock[index]--;
        }
    }

    public static int getBookStock(int index) {
        return bookStock[index];
    }

    public static void reduceBookStock(int index) {
        if (bookStock[index] > 0) {
            bookStock[index]--;
        }
    }
}

class Uniforms extends Inventory {
    private static int[][] unifStock = {
            { 5, 5, 5, 4, 4 }, // Uniform Polo
            { 5, 5, 5, 4, 4 }, // Uniform Blouse
            { 5, 5, 5, 4, 4 }, // PE Top
            { 5, 5, 5, 5, 5 }, // Pants
            { 6, 6, 6, 7, 7 }, // Skirt
            { 6, 6, 6, 6, 6 } // PE Pants
    };

    public Uniforms() {
        super("Uniforms");
    }

    @Override
    public int getStock(int index) {
        int totalStock = 0;
        for (int sizeStock : unifStock[index]) {
            totalStock += sizeStock;
        }
        return totalStock;
    }

    @Override
    public void reduceStock(int index) {
        throw new UnsupportedOperationException("Use reduceStock with sizeIndex for uniforms.");
    }

    public static int getUnifStock(int index) {
        int totalStock = 0;
        for (int sizeStock : unifStock[index]) {
            totalStock += sizeStock;
        }
        return totalStock;
    }

    public static int getSizeStock(int uniformIndex, int sizeIndex) {
        return unifStock[uniformIndex][sizeIndex];
    }

    public static void reduceUnifStock(int uniformIndex, int sizeIndex) {
        if (unifStock[uniformIndex][sizeIndex] > 0) {
            unifStock[uniformIndex][sizeIndex]--;
        }
    }
}

class Items extends Inventory {
    private static int[] itemStock = { 14, 15, 20 };

    public Items() {
        super("Items");
    }

    @Override
    public int getStock(int index) {
        return itemStock[index];
    }

    @Override
    public void reduceStock(int index) {
        if (itemStock[index] > 0) {
            itemStock[index]--;
        }
    }

    public static int getItemStock(int index) {
        return itemStock[index];
    }

    public static void reduceItemStock(int index) {
        if (itemStock[index] > 0) {
            itemStock[index]--;
        }
    }
}

public class iReserve {
    private static String generateReferenceNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder referenceNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 11; i++) {
            int index = random.nextInt(characters.length());
            referenceNumber.append(characters.charAt(index));
        }
        return referenceNumber.toString();
    }

    static class Accounts {
        String username;
        String name;
        String studNo;
        String course;
        String email;
        String phone;
        String password;
        List<String> purchaseHistory;

        Accounts(String username, String name, String studNo, String course, String email, String phone,
                String password) {
            this.username = username;
            this.name = name;
            this.studNo = studNo;
            this.course = course;
            this.email = email;
            this.phone = phone;
            this.password = password;
            this.purchaseHistory = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        LinkedList<Accounts> accounts = new LinkedList<>();
        Scanner input = new Scanner(System.in);
        int choice = 0;

        Books books = new Books();
        Items items = new Items();
        Uniforms uniforms = new Uniforms();

        do {
            try {
                System.out.println("+==============================+");
                System.out.println("       Welcome to IReserve");
                System.out.println("+==============================+");
                System.out.println("          (1) Sign-In");
                System.out.println("          (2) Sign-Up");
                System.out.println("          (3) Exit");
                System.out.println("+==============================+");
                System.out.print("Enter Choice: ");
                choice = input.nextInt();
                input.nextLine();

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number (1, 2, or 3).");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    signIn(accounts, input, books, items, uniforms);
                    break;
                case 2:
                    signUp(accounts, input);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input, try again.");
            }
        } while (choice != 3);

        input.close();
    }

    private static void signIn(LinkedList<Accounts> accounts, Scanner input, Books books, Items items,
            Uniforms uniforms) {
        boolean signedIn = false;
        int remainAttempts = 3;
        Accounts currentAccount = null;

        while (remainAttempts > 0) {
            System.out.print("Enter Username: ");
            String userInput = input.nextLine();
            System.out.print("Enter Password: ");
            String passwordInput = input.nextLine();

            for (Accounts account : accounts) {
                if (account.username.equals(userInput) && account.password.equals(passwordInput)) {
                    System.out.println("Sign-In successful! Welcome, " + account.name + "!");
                    signedIn = true;
                    currentAccount = account;
                    break;
                }
            }

            if (signedIn) {
                signedInMenu(currentAccount, input, books, items, uniforms);
                break;
            } else {
                remainAttempts--;
                System.out.println("Invalid username or password. Remaining attempts: " + remainAttempts);
            }
        }

        if (!signedIn) {
            System.out.println("Too many attempts. Returning to main menu.");
        }
    }

    private static void signUp(LinkedList<Accounts> accounts, Scanner input) {
        System.out.print("Student No.: ");
        String studNo = input.nextLine();
        for (Accounts account : accounts) {
            if (account.studNo.equals(studNo)) {
                System.out.println("An account with this Student Number already exists.");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = input.nextLine();
        System.out.print("Student Course: ");
        String course = input.nextLine();
        System.out.print("Enter Email: ");
        String email = input.nextLine();
        for (Accounts account : accounts) {
            if (account.email.equals(email)) {
                System.out.println("An account with this Email already exists.");
                return;
            }
        }

        System.out.print("Enter Phone No.: ");
        String phone = input.nextLine();
        System.out.print("Enter Username: ");
        String user = input.nextLine();
        for (Accounts account : accounts) {
            if (account.username.equals(user)) {
                System.out.println("An account with this Username already exists.");
                return;
            }
        }

        System.out.print("Enter Password: ");
        String pass = input.nextLine();
        System.out.print("Re-Enter Password: ");
        String rePass = input.nextLine();
        if (rePass.equals(pass)) {
            System.out.println("Signup successful!");
            accounts.add(new Accounts(user, name, studNo, course, email, phone, pass));
        } else {
            System.out.println("Password Mismatch. Signup failed.");
        }
    }

    private static void signedInMenu(Accounts currentAccount, Scanner input, Books books, Items items,
            Uniforms uniforms) {
        boolean signedIn = true;

        while (signedIn) {
            System.out.println("+==============================+");
            System.out.println("       Welcome, " + currentAccount.name + "!");
            System.out.println("           (1) Reserve Now");
            System.out.println("           (2) History");
            System.out.println("           (3) Sign-Out");
            System.out.println("+==============================+");
            System.out.print("Enter Choice: ");
            try {
                int menu = input.nextInt();
                input.nextLine();
                switch (menu) {
                    case 1:
                        handleMiscellaneousMenu(input, currentAccount);
                        break;
                    case 2:
                        displayPurchaseHistory(currentAccount);
                        break;
                    case 3:
                        signedIn = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
    }

    private static void handleMiscellaneousMenu(Scanner input, Accounts currentAccount) {
        while (true) {
            System.out.println("================================");
            System.out.println("         List of Items");
            System.out.println("================================");
            System.out.println("        (1) Books");
            System.out.println("        (2) Uniform");
            System.out.println("        (3) NSTP Items");
            System.out.println("        (4) Back to Main Menu");
            System.out.print("Enter Choice: ");
            int misc = input.nextInt();
            switch (misc) {
                case 1:
                    books(input, currentAccount);
                    break;
                case 2:
                    uniform(input, currentAccount);
                    break;
                case 3:
                    otherItems(input, currentAccount);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void uniform(Scanner miscInput, Accounts currentAccount) {
        while (true) {
            System.out.println("================================");
            System.out.println("        List of Uniforms");
            System.out.println("================================");
            System.out.println("        (1) Uniform Top");
            System.out.println("        (2) Uniform Bottom");
            System.out.println("        (3) Back");
            System.out.print("Enter Choice: ");
            try {
                int unifChoice = miscInput.nextInt();
                switch (unifChoice) {
                    case 1:
                        while (true) {
                            System.out.println("================================");
                            System.out.println("       Uniform Top List");
                            System.out.println("(1) Uniform Polo (Boys)");
                            System.out.println("(2) Uniform Blouse (Girls)");
                            System.out.println("(3) PE Top");
                            System.out.println("(4) Back");
                            System.out.print("Select choice: ");
                            int unifTop = miscInput.nextInt();
                            switch (unifTop) {
                                case 1:
                                    if (Uniforms.getUnifStock(0) > 0) {
                                        displayUniformStock("Uniform Polo (Boys)", 0, 515, miscInput, currentAccount);
                                    } else {
                                        System.out.println("Sorry, Polo is out of Stock!");
                                    }
                                    break;
                                case 2:
                                    if (Uniforms.getUnifStock(1) > 0) {
                                        displayUniformStock("Uniform Blouse (Girls)", 1, 525, miscInput,
                                                currentAccount);
                                    } else {
                                        System.out.println("Sorry, Blouse is out of Stock!");
                                    }
                                    break;
                                case 3:
                                    if (Uniforms.getUnifStock(2) > 0) {
                                        displayUniformStock("PE Top", 2, 355, miscInput, currentAccount);
                                    } else {
                                        System.out.println("Sorry, PE Top is out of Stock!");
                                    }
                                    break;
                                case 4:
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                            if (unifTop == 4)
                                break;
                        }
                        break;

                    case 2:
                        while (true) {
                            System.out.println("================================");
                            System.out.println("       Uniform Bottom List");
                            System.out.println("(1) Uniform Pants (Boys)");
                            System.out.println("(2) Uniform Skirt (Girls)");
                            System.out.println("(3) PE Pants");
                            System.out.println("(4) Back");
                            System.out.print("Select choice: ");
                            int unifBottom = miscInput.nextInt();
                            switch (unifBottom) {
                                case 1:
                                    if (Uniforms.getUnifStock(3) > 0) {
                                        displayUniformStock("Uniform Pants (Boys)", 3, 530, miscInput, currentAccount);
                                    } else {
                                        System.out.println("Sorry, Pants are out of Stock!");
                                    }
                                    break;
                                case 2:
                                    if (Uniforms.getUnifStock(4) > 0) {
                                        displayUniformStock("Uniform Skirt (Girls)", 4, 425, miscInput, currentAccount);
                                    } else {
                                        System.out.println("Sorry, Skirt is out of Stock!");
                                    }
                                    break;
                                case 3:
                                    if (Uniforms.getUnifStock(5) > 0) {
                                        displayUniformStock("PE Pants", 5, 450, miscInput, currentAccount);
                                    } else {
                                        System.out.println("Sorry, PE Pants are out of Stock!");
                                    }
                                    break;
                                case 4:
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                            if (unifBottom == 4)
                                break;
                        }
                        break;

                    case 3:
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number (1, 2, or 3).");
                miscInput.nextLine();
            }
        }
    }

    private static void displayUniformStock(String uniformName, int unifIndex, int price, Scanner miscInput,
            Accounts currentAccount) {
        System.out.println("================================");
        System.out.println(uniformName + " - Total Stock: " + Uniforms.getUnifStock(unifIndex));
        System.out.println("Sizes:");
        String[] sizes = { "XS", "S", "M", "L", "XL" };
        for (int i = 0; i < sizes.length; i++) {
            System.out.println(sizes[i] + ": " + Uniforms.getSizeStock(unifIndex, i));
        }
        System.out.println("Price: " + price);
        System.out.print("Input size: ");
        String size = miscInput.next();
        unifProcess(currentAccount, uniformName, size, unifIndex);
    }

    private static void unifProcess(Accounts currentAccount, String uniform, String size, int unifIndex) {
        Scanner input = new Scanner(System.in);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String DateTime = currentDateTime.format(formatter);

        String[] sizes = { "XS", "S", "M", "L", "XL" };
        int sizeIndex = Arrays.asList(sizes).indexOf(size.toUpperCase());

        if (sizeIndex == -1) {
            System.out.println("Invalid size input!");
            return;
        }

        System.out.print("Do you confirm this reservation? (Y/N): ");
        char yn = input.next().charAt(0);
        if (yn == 'Y' || yn == 'y') {
            if (Uniforms.getSizeStock(unifIndex, sizeIndex) > 0) {
                Uniforms.reduceUnifStock(unifIndex, sizeIndex);
                String referenceNumber = generateReferenceNumber();
                String receipt = "================================\n" +
                        "RECEIPT\n" +
                        "Reference No: " + referenceNumber + "\n" +
                        "Name: " + currentAccount.name + "\n" +
                        "Student No: " + currentAccount.studNo + "\n" +
                        "Course: " + currentAccount.course + "\n" +
                        "Uniform: " + uniform + "\n" +
                        "Size: " + size.toUpperCase() +
                        "\nDate and time: " + DateTime +
                        "\n================================\n";
                System.out.println(receipt);
                currentAccount.purchaseHistory.add(receipt);
            } else {
                System.out.println("Sorry, " + size.toUpperCase() + " size is out of stock for " + uniform + ".");
            }
        } else {
            System.out.println("No reservation made.");
        }
    }

    private static void books(Scanner miscInput, Accounts currentAccount) {
        while (true) {
            System.out.println("");
            System.out.println("================================");
            System.out.println("        List of Books");
            System.out.println("================================");
            System.out.println("Book Code: 6479 - Stock: " + Books.getBookStock(0)
                    + " - AE5: International Business and Trade - Book Price: 500");
            System.out.println("Book Code: 6480 - Stock: " + Books.getBookStock(1)
                    + " - AKAD-KOLAB: Tuon sa Kolaboratibong Pagsulat sa Filipino - Book Price: 275");
            System.out.println("Book Code: 6609 - Stock: " + Books.getBookStock(2)
                    + " - BA-ECO101: Basics of Microeconomics - Book Price: 300");
            System.out.println("Book Code: 6612 - Stock: " + Books.getBookStock(3)
                    + " - BA-MGT101: Introduction to Human Resource Management Revised Edition - Book Price: 350");
            System.out.println("Book Code: 6486 - Stock: " + Books.getBookStock(4)
                    + " - A-MGT103: Total Quality Management: An Introduction - Book Price: 375");
            System.out.println("Book Code: 6494 - Stock: " + Books.getBookStock(5)
                    + " - Cal01: Differential Calculus - Book Price: 500");
            System.out.println("Book Code: 6620 - Stock: " + Books.getBookStock(6)
                    + " - CPE102: Programming Logic & Design Using Dev C++ - Book Price: 450");
            System.out.println("Book Code: 6499 - Stock: " + Books.getBookStock(7)
                    + " - DE: Engineering Differential Equations - Book Price: 500");
            System.out.println("Book Code: 6625 - Stock: " + Books.getBookStock(8)
                    + " - ELSCIENCES: Earth and Life Science - Book Price: 495");
            System.out.println("Book Code: 6508 - Stock: " + Books.getBookStock(9)
                    + " - EMF: Engineering Mathematics Formula - Book Price: 150");
            System.out.println("================================");
            System.out.println("       Enter the Book Code.");
            System.out.println("           (0) Back");
            System.out.println("================================");
            System.out.print("Enter Choice: ");

            try {
                int bookChoice = miscInput.nextInt();
                switch (bookChoice) {
                    case 6479:
                        processPurchase(currentAccount, "AE5: International Business and Trade", 6479, 500, 0);
                        break;
                    case 6480:
                        processPurchase(currentAccount, "AKAD-KOLAB: Tuon sa Kolaboratibong Pagsulat sa Filipino", 6480,
                                275, 1);
                        break;
                    case 6609:
                        processPurchase(currentAccount, "BA-ECO101: Basics of Microeconomics", 6609, 300, 2);
                        break;
                    case 6612:
                        processPurchase(currentAccount,
                                "BA-MGT101: Introduction to Human Resource Management Revised Edition", 6612, 350, 3);
                        break;
                    case 6486:
                        processPurchase(currentAccount, "A-MGT103: Total Quality Management: An Introduction", 6486,
                                375, 4);
                        break;
                    case 6494:
                        processPurchase(currentAccount, "Cal01: Differential Calculus", 6494, 500, 5);
                        break;
                    case 6620:
                        processPurchase(currentAccount, "CPE102: Programming Logic & Design Using Dev C++", 6620, 450,
                                6);
                        break;
                    case 6499:
                        processPurchase(currentAccount, "DE: Engineering Differential Equations", 6499, 500, 7);
                        break;
                    case 6625:
                        processPurchase(currentAccount, "ELSCIENCES: Earth and Life Science", 6625, 495, 8);
                        break;
                    case 6508:
                        processPurchase(currentAccount, "EMF: Engineering Mathematics Formula", 6508, 150, 9);
                        break;
                    case 0:
                        return; 
                    default:
                        System.out.println("Invalid input. Please enter a valid Book Code or 0 to go back.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                miscInput.nextLine(); 
            }
        }
    }

    private static void processPurchase(Accounts currentAccount, String bookName, int bookNumber, int bookPrice,
            int bookIndex) {
        Scanner input = new Scanner(System.in);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = currentDateTime.format(formatter);

        System.out.print("Do you confirm this reservation? (Y/N): ");
        char yn = input.next().charAt(0);

        if (yn == 'Y' || yn == 'y') {
            Books.reduceBookStock(bookIndex);
            String referenceNumber = generateReferenceNumber();
            String receipt = "================================\n" +
                    "RECEIPT\n" +
                    "Reference No: " + referenceNumber + "\n" +
                    "Name: " + currentAccount.name + "\n" +
                    "Student No: " + currentAccount.studNo + "\n" +
                    "Course: " + currentAccount.course + "\n" +
                    "Book: " + bookName + "\n" +
                    "Book No: " + bookNumber +
                    "\nBook Price: " + bookPrice +
                    "\nDate and Time: " + dateTime +
                    "\n================================\n";
            System.out.println(receipt);
            currentAccount.purchaseHistory.add(receipt);
        } else {
            System.out.println("No reservation made.");
        }
    }

    private static void otherItems(Scanner miscInput, Accounts currentAccount) {
        while (true) {
            System.out.println("================================");
            System.out.println("      List of Other Items");
            System.out.println("================================");
            System.out.println("     (1) NSTP Bandage - Stock: " + Items.getItemStock(0));
            System.out.println("     (2) NSTP Uniform - Stock: " + Items.getItemStock(1));
            System.out.println("     (3) Garison Belt - Stock: " + Items.getItemStock(2));
            System.out.println("     (4) Back");
            System.out.println("================================");
            System.out.print("Enter Choice: ");

            try {
                int otherChoice = miscInput.nextInt();
                switch (otherChoice) {
                    case 1:
                        if (Items.getItemStock(0) > 0) {
                            otherItemsProcess(currentAccount, "NSTP Bandage", 80, 0);
                        } else {
                            System.out.println("Sorry, NSTP Bandage is out of stock!");
                        }
                        break;
                    case 2:
                        if (Items.getItemStock(1) > 0) {
                            otherItemsProcess(currentAccount, "NSTP Uniform", 250, 1);
                        } else {
                            System.out.println("Sorry, NSTP Uniform is out of stock!");
                        }
                        break;
                    case 3:
                        if (Items.getItemStock(2) > 0) {
                            otherItemsProcess(currentAccount, "Garison Belt", 70, 2);
                        } else {
                            System.out.println("Sorry, Garison Belt is out of stock!");
                        }
                        break;
                    case 4:
                        return; 
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                miscInput.nextLine(); 
            }
        }
    }

    private static void otherItemsProcess(Accounts currentAccount, String itemName, int price, int index) {
        Scanner input = new Scanner(System.in);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = currentDateTime.format(formatter);

        System.out.print("Do you confirm this reservation? (Y/N): ");
        char yn = input.next().charAt(0);

        if (yn == 'Y' || yn == 'y') {
            Items.reduceItemStock(index);
            String referenceNumber = generateReferenceNumber();
            String receipt = "================================\n" +
                    "RECEIPT\n" +
                    "Reference No: " + referenceNumber + "\n" +
                    "Name: " + currentAccount.name + "\n" +
                    "Student No: " + currentAccount.studNo + "\n" +
                    "Course: " + currentAccount.course + "\n" +
                    "Item: " + itemName + "\n" +
                    "Price: " + price +
                    "\nDate and time: " + dateTime +
                    "\n================================\n";
            System.out.println(receipt);
            currentAccount.purchaseHistory.add(receipt);
        } else {
            System.out.println("No reservation made.");
        }
    }

    private static void displayPurchaseHistory(Accounts currentAccount) {
        System.out.println("Purchase History:");
        if (currentAccount.purchaseHistory.isEmpty()) {
            System.out.println("No reservations made.");
        } else {
            for (String receipt : currentAccount.purchaseHistory) {
                System.out.println(receipt);
            }
        }
    }
}
