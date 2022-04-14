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
import entities.Item;

public class Menu implements IStorage, IController {
	// Variables
	private ArrayList<Item> listOfItems;
	private int noOfItems;
	private int[] noOfItemType;
	private static Menu single_instance = null;
	private Item tempItem;

	// Constructor
	private Menu() {
		noOfItemType = new int[5];
		listOfItems = new ArrayList<Item>();
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

	public void printCat(int type) {
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
		int num;
		for (num = 0; num < noOfItems; num++) {
			tempItem = listOfItems.get(num);
			if (tempItem.getType() == type) {
				printItem(num);
			}
		}
		System.out.println();
	}

	public void printItem(int itemNo) {
		tempItem = listOfItems.get(itemNo);
		System.out.printf("%d", tempItem.getID());
		System.out.printf(" " + tempItem.getName() + " $%.2f\n", tempItem.getPrice());
		System.out.printf(tempItem.getDesc() + "\n");
	}

	public Item getItem(int ID) {
		int num;
		for (num = 0; num < noOfItems; num++) {
			tempItem = listOfItems.get(num);
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
				temp1 = listOfItems.get(b);
				temp0 = listOfItems.get(b - 1);
				if (temp1.getID() >= temp0.getID())
					break;
				else {
					listOfItems.set(b - 1, temp1);
					listOfItems.set(b, temp0);
				}
			}
		}
	}

	public void create(Object entities) {
		Item toBeAdded = (Item) entities;

		noOfItemType[toBeAdded.getType() - 1]++;
		tempItem.setID(toBeAdded.getType() * 100 + noOfItemType[toBeAdded.getType() - 1]);

		noOfItems++;
		listOfItems.add(toBeAdded);

		// storeData();
		sortDB();
		storeData();
		loadData();

	}

	public void read() {

	}

	public void delete(Object entities) {

		Item tempItem = (Item) entities;
		int I, ID;
		Scanner sc = new Scanner(System.in);
		System.out.println("--Removing item--\nItem ID: ");
		ID = sc.nextInt();
		for (I = 0; I < noOfItems; I++) {
			tempItem = listOfItems.get(I);
			if (tempItem.getID() == ID)
				break;
		}
		if (I == noOfItems) {
			System.out.println("Item does not exist\n");
			return;
		}
		int type;
		type = tempItem.getType();
		noOfItems--;
		listOfItems.remove(I);

		for (I = I; I < noOfItems; I++) {
			tempItem = listOfItems.get(I);
			if (tempItem.getType() == type)
				tempItem.setID(tempItem.getID() - 1);
			else
				break;
		}

		// storeData();
		storeData();
		loadData();
	}

	public void update(Object entities, int choice, String value) {
		Item I = (Item) entities;
		switch (choice) {
			case 1:
				I.setName(value);
				break;
			case 2:
				I.setDesc(value);
				break;
			case 3:
				try {
					Float F = Float.parseFloat(value);
					I.setPrice(F);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}

		// storeData();
		storeData();
		loadData();
	}

	public void storeData() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Menu.ser"));
			out.writeInt(listOfItems.size()); // noOfItems
			for (Item item : listOfItems)
				out.writeObject(item);
			System.out.printf("Menu/Itemcontroller: %s Entries Saved.\n", listOfItems.size());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadData() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("Menu.ser"));

			int noOfOrdRecords = ois.readInt();
			System.out.println("Menu/ItemController: " + noOfOrdRecords + " Entries Loaded");
			for (int i = 0; i < noOfOrdRecords; i++) {
				listOfItems.add((Item) ois.readObject());
			}

		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

}
