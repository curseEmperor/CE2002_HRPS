package UI;

import java.util.Scanner;

import Controller.Menu;
import entities.Item;

public class MenuUI extends StandardUI implements ControllerUI {
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
                	readOneDets();
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

        choice = getUserChoice(5);

        System.out.println("Item name: ");
        String itemName = getUserString();

        System.out.println("Item description: ");
        String itemDesc = getUserString();

        System.out.println("Item price: ");
        float itemPrice = sc.nextFloat();

        Item tempItem = new Item(itemName, itemDesc, itemPrice, Menu.getInstance().toType(choice));

        Menu.getInstance().create(tempItem);
        System.out.println("\nItem added!\n");
    }

    public void readOneDets() { // read one Item
    	System.out.println("Item ID: ");
    	Item item = Menu.getInstance().checkExistance(getUserString());
    	if (item == null) System.out.println("Invalid ID");
    	else Menu.getInstance().printItem(item);
    }

    public void update() { // update a single item

        System.out.println("--Editing item--\nItem ID: ");

        String itemID = getUserString();

        Item toBeUpdated = Menu.getInstance().checkExistance(itemID); // check existence
        if (toBeUpdated == null) {
            System.out.println("Item does not exist");
        } else {
            System.out.println("1. ID\n2. Name\n3. Description\n4. Price\n5. Type");
            System.out.println("What do you want to update: ");
            choice = getUserChoice(5);

            System.out.println("Enter the relevant details: ");
            String content = getUserString();

            Menu.getInstance().update(toBeUpdated, choice, content);

            System.out.println("Item edited!\n");
        }

    }

    public void delete() {
        System.out.println("--Removing item--\nItem ID: ");
        String itemID = getUserString();

        Item toBeDeleted = Menu.getInstance().checkExistance(itemID);
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

}
