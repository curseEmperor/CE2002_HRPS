package Controller;

import java.io.File; //Import the File class
import java.io.FileInputStream;
import java.io.FileNotFoundException; //Import this class to handle errors
import java.io.FileOutputStream;
import java.io.FileWriter; //Import the Writer class to create and write files
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer; //Import the Writer class to create and write files
import java.text.DecimalFormat; //Import this class to set float number format when converting to string
import java.text.NumberFormat; //Import this class to set float number format when converting to string
import java.util.Scanner; //Import the Scanner class to read text files
import java.util.ArrayList; //Import ArrayList class
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entities.Item;
import Enums.ItemTypes;

public class Menu implements IStorage, IController {
	// Variables
	private ArrayList<Item> itemList;
	private int noOfItems;
	private int[] noOfItemType;
	private static Menu single_instance = null;
	private Item tempItem;

	// Constructor
	private Menu() {
		noOfItemType = new int[5];
		itemList = new ArrayList<Item>();
		loadData();
		sortDB();
	}

	public static Menu getInstance() {
		if (single_instance == null)
			single_instance = new Menu();
		return single_instance;
	}

	// Behaviours
	public void printMenu() {
		int num;
		System.out.println("Number of Items: " + String.valueOf(noOfItems));
		for (num = 1; num <= 5; num++) {
			System.out.println("Number of Items: " +
					String.valueOf(noOfItemType[num - 1]));
			printCat(num);
		}
	}

	public void printCat(ItemTypes itemType) {
		switch (type) {
			case 1:
				System.out.println("--Appetizers--");
				break;
			case 2:
				System.out.println("--Entrees--");
				break;
			case 3:
				System.out.println("--Sides--");
				break;
			case 4:
				System.out.println("--Desserts--");
				break;
			case 5:
				System.out.println("--Beverages--");
				break;
			default:
				break;
		}
		for (num = 0; num < noOfItems; num++) {
			tempItem = itemList.get(num);
			if (tempItem.getType() == itemType) {
				printItem(num);
			}
		}
		System.out.println();
	}

	public void printItem(Item item) {
		System.out.printf("%d", item.getID());
		System.out.printf(" " + item.getName() + " $%.2f\n", item.getPrice());
		System.out.printf(item.getDesc() + "\n");
	}

	public Item checkExistance(int ID) {
		int num;
		for (num = 0; num < noOfItems; num++) {
			tempItem = itemList.get(num);
			if (tempItem.getID() == ID)
				break;
		}
		if (num == noOfItems) {
			return null;
		}
		return tempItem;
	}

	public void sortDB() {
		int a, b;
		Item temp0, temp1;
		for (a = 0; a < noOfItems; a++) {
			for (b = a; b > 0; b--) {
				temp1 = itemList.get(b);
				temp0 = itemList.get(b - 1);
				if (temp1.getID() >= temp0.getID())
					break;
				else {
					itemList.set(b - 1, temp1);
					itemList.set(b, temp0);
				}
			}
		}
	}

	public void create(Object entities) {
		Item toBeAdded = (Item) entities;

		noOfItemType[toBeAdded.getType() - 1]++;
		tempItem.setID(toBeAdded.getType() * 100 + noOfItemType[toBeAdded.getType() - 1]);

		noOfItems++;
		itemList.add(toBeAdded);

		// storeData();
		sortDB();
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
		// cleanID
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
			ois = new ObjectInputStream(new FileInputStream("Item.ser"));

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
		int count = 0;
		for (Item item : itemByType.get(ItemTypes.APPETIZER))
			item.setID(100 + (count++));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.ENTREE))
			item.setID(200 + (count++));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.SIDE))
			item.setID(300 + (count++));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.DESSERT))
			item.setID(400 + (count++));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.BEVERAGE))
			item.setID(500 + (count++));
	}

}
