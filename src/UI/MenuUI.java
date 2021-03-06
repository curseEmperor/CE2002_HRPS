package UI;

import java.util.Scanner;

import Controller.Menu;
import entities.Item;

public class MenuUI {
    private static MenuUI instance = null;
    Scanner sc;
    int choice, qSize;

    private MenuUI() {
        sc = new Scanner(System.in);
    }

    public static MenuUI getInstance() {
        if (instance == null)
            instance = new MenuUI();
        return instance;
    }

    public int showSelection() {
        System.out.println(" Menu options avaiable: ");
        System.out.println("1) Create / Add Items");
        System.out.println("2) Read");
        System.out.println("3) Update");
        System.out.println("4) Delete");
        System.out.println("5) Display Full Catalog");
        System.out.println("6) Return to MainUI");

        return 6;
    }

    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    read();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    catalog();
                    break;
                case 6:
                    break;
            }
        } while (choice < qSize);
    }

    public void create() {

        System.out.println("--Adding item--\n1. Appetizer\n2. Entree\n3. Side\n4. Dessert\n5. Beverage\nItem type: ");

        int itemType = getUserChoice(5);

        System.out.println("Item name: ");
        String itemName = getUserString();

        System.out.println("Item description: ");
        String itemDesc = getUserString();

        System.out.println("Item price: ");
        float itemPrice = sc.nextFloat();

        Item tempItem = new Item(itemName, itemDesc, itemPrice, itemType);

        Menu.getInstance().create(tempItem);
        System.out.println("\nItem added!\n");
    }

    public void read() { // read one Item
    	Item item;
    	System.out.println("Item ID: ");
    	item = Menu.getInstance().checkExistance(getUserString());
    	Menu.getInstance().printItem(item);
    }

    public void update() { // update a single item

        System.out.println("--Editing item--\nItem ID: ");

        int itemID = sc.nextInt();
        sc.nextLine();

        Item toBeUpdated = Menu.getInstance().getItem(itemID); // check existence
        if (toBeUpdated == null) {
            System.out.println("Item does not exist");
        } else {
            System.out.println("What do you want to update: ");
            System.out.println("1. Name\n2. Description\n3. Price");
            choice = getUserChoice(3);

            System.out.println("Enter the relevant details:");
            String content = getUserString();

            Menu.getInstance().update(toBeUpdated, choice, content);

            // System.out.println(toBeUpdated);
            System.out.println("Item edited!\n");

        }

    }

    public void delete() {
        System.out.println("--Removing item--\nItem ID: ");
        int itemID = sc.nextInt();
        sc.nextLine();

        Item toBeDeleted = Menu.getInstance().getItem(itemID);
        if (toBeDeleted == null) {
            System.out.println("Item does not exist");
        } else {
            Menu.getInstance().delete(toBeDeleted);

            System.out.println("Item is removed.");
        }
    }

    public void catalog() { // view the whole Menu with all the items catagoried
    	Menu.getInstance().printMenu();
    }

    private int getUserChoice(int n) {

        do {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice <= 0 || choice > n)
                    System.out.println("Please input values between 1 to " + n + " only!");
                else
                    break;
            } else {
                System.out.println("Please input only integers!");
                sc.next();
            }
        } while (choice <= 0 || choice > n);

        return choice;
    }

    private String getUserString() {
        String input = sc.nextLine().toUpperCase();
        return input;
    }
}
