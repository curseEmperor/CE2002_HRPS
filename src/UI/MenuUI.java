package UI;

import java.util.Scanner;

import Controller.Menu;
import Entity.Item;

public class MenuUI extends StandardUI implements ControllerUI {
    private static MenuUI instance = null;
    Scanner sc;
    int choice, qSize;

    private MenuUI() {
        sc = new Scanner(System.in);
    }

    /**
     * @return MenuUI
     */
    public static MenuUI getInstance() {
        if (instance == null)
            instance = new MenuUI();
        return instance;
    }

    /**
     * @return int
     */
    public int showSelection() {
        System.out.println("Menu options avaiable: ");
        System.out.println("1) Create / Add Items");
        System.out.println("2) View Item info");
        System.out.println("3) Update Item info");
        System.out.println("4) Delete Item");
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

        System.out.println("--Adding item--\n"
                + "1) Appetizer\n"
                + "2) Entree\n"
                + "3) Side\n"
                + "4) Dessert\n"
                + "5) Beverage\n"
                + "Item type: ");

        choice = getUserChoice(5);

        System.out.println("Item name: ");
        String itemName = getUserString();

        System.out.println("Item description: ");
        String itemDesc = getUserString();

        System.out.println("Item price: ");
        float itemPrice = sc.nextFloat();
        while (itemPrice < 0.0) {
            System.out.println("Enter Positive number: ");
            itemPrice = sc.nextFloat();
        }

        Item tempItem = new Item(itemName, itemDesc, itemPrice, Menu.getInstance().toType(choice));

        Menu.getInstance().create(tempItem);
        System.out.println("\nItem added!\n");
    }

    /**
     * @param null
     */
    public void readOneDets() { // read one Item
        System.out.println("Item ID: ");
        Item item = Menu.getInstance().checkExistance(getUserString());
        if (item == null)
            System.out.println("Invalid ID");
        else
            Menu.getInstance().printItem(item);
    }

    /**
     * @param null
     */
    public void update() { // update a single item

        System.out.println("--Editing item--\nItem ID: ");

        String itemID = getUserString();

        Item toBeUpdated = Menu.getInstance().checkExistance(itemID); // check existence
        if (toBeUpdated == null) {
            System.out.println("Item does not exist");
        } else {
            Menu.getInstance().printItem(toBeUpdated);
            System.out.println(
                    "1) ID\n"
                            + "2) Name\n"
                            + "3) Description\n"
                            + "4) Price\n"
                            + "5) Type\n"
                            + "6) Cancel Update");
            System.out.println("What do you want to update: ");
            choice = getUserChoice(5);
            if (choice == 6)
                return;
            String content;
            if (choice != 5) {
                System.out.println("Enter the relevant details: ");
                content = getUserString();
            } else {
                System.out.println(
                        "1) Appetizer\n"
                                + "2) Entree\n"
                                + "3) Side\n"
                                + "4) Dessert\n"
                                + "5) Beverage\n"
                                + "Enter type: ");
                content = String.valueOf(getUserChoice(5));
            }

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
