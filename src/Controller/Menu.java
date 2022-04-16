package Controller;

import java.util.ArrayList; //Import ArrayList class
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.SerializeDB;
import entities.Item;
import Enums.ItemTypes;

public class Menu extends SerializeDB implements IController, IStorage {
	// Variables
	private ArrayList<Item> itemList;
	private static Menu single_instance = null;

	// Constructor
	private Menu() {
		itemList = new ArrayList<Item>();
		cleanID();
	}

	public static Menu getInstance() {
		if (single_instance == null)
			single_instance = new Menu();
		return single_instance;
	}

	// Behaviours
	public void printMenu() {
		System.out.println("Total number of Items: " + itemList.size() + "\n");
		for (ItemTypes itemType : ItemTypes.values()) {
			printCat(itemType);
		}
	}

	public void printCat(ItemTypes itemType) {
		List<Item> types = splitItemByType().get(itemType);
		switch (itemType) {
			case APPETIZER:
				System.out.println("\033[1m=======Appetizers=======\033[0m");
				break;
			case ENTREE:
				System.out.println("\033[1m=========Entree=========\033[0m");
				break;
			case SIDE:
				System.out.println("\033[1m=========Sides=========\033[0m");
				break;
			case DESSERT:
				System.out.println("\033[1m========Desserts========\033[0m");
				break;
			case BEVERAGE:
				System.out.println("\033[1m=======Beverages=======\033[0m");
				break;
			default:
				break;
		}
		System.out.println("Number of items: " + types.size());
		for (Item item : types) {
			printItem(item);
		}
		System.out.println();
	}

	public void printItem(Item item) {
		System.out.format("%s %s\n", item.getID(), item.getName());
		System.out.format("\033[3m%s\033[0m\n", item.getDesc());
		System.out.printf("Price: $%.2f\n\n", item.getPrice());
	}

	public Item checkExistance(String ID) {
		for (Item item : itemList) {
			if (item.getID().compareTo(ID) == 0)
				return item;
		}
		return null;
	}

	public void sortData() {
		int a, b;
		Item temp;
		for (a = 0; a < itemList.size(); a++) {
			for (b = a; b > 0; b--) {
				temp = itemList.get(b);
				if (temp.getID().compareTo(itemList.get(b - 1).getID()) > 0)
					break;
				else {
					itemList.set(b, itemList.get(b - 1));
					itemList.set(b - 1, temp);
				}
			}
		}
	}

	public void create(Object entities) {
		Item toBeAdded = (Item) entities;
		itemList.add(toBeAdded);
		cleanID();
		storeData();
	}

	public void read() {
		for (Item item : itemList) {
			System.out.println(item.getName());
		}
	}

	public void delete(Object entities) {
		Item toBeDeleted = (Item) entities;
		itemList.remove(toBeDeleted);
		cleanID();
		storeData();
	}

	public void update(Object entities, int choice, String value) {
		Item item = (Item) entities;
		switch (choice) {
			case 1: // itemID
				item.setID(value);
				sortData();
				break;
			case 2: // itemName
				item.setName(value);
				break;
			case 3: // itemDesc
				item.setDesc(value);
				break;
			case 4: // itemPrice
				try {
					Float F = Float.parseFloat(value);
					item.setPrice(F);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 5: // itemType
				item.setType(toType(value));
				break;
			default:
				break;
		}
		cleanID();
		storeData();
	}

	public Map<ItemTypes, List<Item>> splitItemByType() {
		Map<ItemTypes, List<Item>> itemByType = new HashMap<>();

		ArrayList<Item> appetizers = new ArrayList<Item>();
		ArrayList<Item> entrees = new ArrayList<Item>();
		ArrayList<Item> sides = new ArrayList<Item>();
		ArrayList<Item> desserts = new ArrayList<Item>();
		ArrayList<Item> beverages = new ArrayList<Item>();

		for (Item item : itemList) {
			if (item.getType() == ItemTypes.APPETIZER)
				appetizers.add(item);
			if (item.getType() == ItemTypes.ENTREE)
				entrees.add(item);
			if (item.getType() == ItemTypes.SIDE)
				sides.add(item);
			if (item.getType() == ItemTypes.DESSERT)
				desserts.add(item);
			if (item.getType() == ItemTypes.BEVERAGE)
				beverages.add(item);
		}

		itemByType.put(ItemTypes.APPETIZER, appetizers);
		itemByType.put(ItemTypes.ENTREE, entrees);
		itemByType.put(ItemTypes.SIDE, sides);
		itemByType.put(ItemTypes.DESSERT, desserts);
		itemByType.put(ItemTypes.BEVERAGE, beverages);

		return itemByType;
	}

	private void cleanID() {
		Map<ItemTypes, List<Item>> itemByType = splitItemByType();
		int count = 1;
		for (Item item : itemByType.get(ItemTypes.APPETIZER))
			item.setID(String.valueOf(100 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.ENTREE))
			item.setID(String.valueOf(200 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.SIDE))
			item.setID(String.valueOf(300 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.DESSERT))
			item.setID(String.valueOf(400 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.BEVERAGE))
			item.setID(String.valueOf(500 + (count++)));
		sortData();
	}

	public ItemTypes toType(String ID) {
		switch (ID.charAt(0)) {
			case '1':
				return ItemTypes.APPETIZER;
			case '2':
				return ItemTypes.ENTREE;
			case '3':
				return ItemTypes.SIDE;
			case '4':
				return ItemTypes.DESSERT;
			case '5':
				return ItemTypes.BEVERAGE;
			default:
				return null;
		}
	}

	public ItemTypes toType(int ID) {
		switch (ID) {
			case 1:
				return ItemTypes.APPETIZER;
			case 2:
				return ItemTypes.ENTREE;
			case 3:
				return ItemTypes.SIDE;
			case 4:
				return ItemTypes.DESSERT;
			case 5:
				return ItemTypes.BEVERAGE;
			default:
				return null;
		}
	}

	public void storeData() {
		super.storeData("Menu.ser", itemList);
	}

	public void loadData() {
		super.loadData("Menu.ser");
	}
}
