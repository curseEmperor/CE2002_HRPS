package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList; //Import ArrayList class
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entities.Item;
import Enums.ItemTypes;

public class Menu implements IStorage, IController {
	// Variables
	private ArrayList<Item> itemList;
	private static Menu single_instance = null;

	// Constructor
	private Menu() {
		itemList = new ArrayList<Item>();
		loadData();
		sortData();
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
				System.out.println("--Appetizers--");
				break;
			case ENTREE:
				System.out.println("--Entrees--");
				break;
			case SIDE:
				System.out.println("--Sides--");
				break;
			case DESSERT:
				System.out.println("--Desserts--");
				break;
			case BEVERAGE:
				System.out.println("--Beverages--");
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
		System.out.printf(item.getID() + " " + item.getName() + " $%.2f\n", item.getPrice());
		System.out.printf(item.getDesc() + "\n");
	}

	public Item checkExistance(String ID) {
		for (Item item : itemList) {
			item.getID().equals(ID);
			return item;
		}
		return null;
	}

	public void sortData() {
		int a,b;
		Item temp;
		for (a = 0; a < itemList.size(); a++) {
			for (b = a; b > 0; b--) {
				temp = itemList.get(b);
				if (temp.getID().compareTo(itemList.get(b-1).getID()) > 0)
					break;
				else {
					itemList.set(b, itemList.get(b-1));
					itemList.set(b-1, temp);
				}
			}
		}
	}

	public void create(Object entities) {
		Item toBeAdded = (Item) entities;
		itemList.add(toBeAdded);
		cleanID();
		sortData();
		storeData();
		loadData();

	}

	public void read() {
		for (Item item : itemList) {
			System.out.println(item);
		}
	}

	public void delete(Object entities) {
		Item toBeDeleted = (Item) entities;
		itemList.remove(toBeDeleted);
		cleanID();
		storeData();
		loadData();
	}

	public void update(Object entities, int choice, String value) {
		Item I = (Item) entities;
		switch (choice) {
			case 1: // itemID
				I.setName(value);
				break;
			case 2: // itemName
				I.setDesc(value);
				break;
			case 3: // itemDesc
				try {
					Float F = Float.parseFloat(value);
					I.setPrice(F);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 4: // itemPrice
				break;
			case 5: // itemType
				break;
			default:
				break;
		}
		storeData();
		loadData();
	}

	public void storeData() {
		try {
			//File menu = new File("Menu.ser");
			//menu.delete();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Menu.ser"));
			out.writeInt(itemList.size()); // noOfItems
			for (Item item : itemList)
				out.writeObject(item);
			System.out.printf("Menu/Itemcontroller: %s Entries Saved.\n", itemList.size());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadData() {
		ObjectInputStream ois;
		try {
			itemList.clear();
			ois = new ObjectInputStream(new FileInputStream("Menu.ser"));

			int noOfOrdRecords = ois.readInt();
			System.out.println("Menu/ItemController: " + noOfOrdRecords + " Entries Loaded");
			for (int i = 0; i < noOfOrdRecords; i++) {
				itemList.add((Item) ois.readObject());
			}

		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
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
		for (Item item : itemByType.get(ItemTypes.APPETIZER)) item.setID(String.valueOf(100+(count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.ENTREE)) item.setID(String.valueOf(200+(count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.SIDE)) item.setID(String.valueOf(300+(count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.DESSERT)) item.setID(String.valueOf(400+(count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.BEVERAGE)) item.setID(String.valueOf(500+(count++)));
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
}
