package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList; //Import ArrayList class
import entities.Item;

public class Menu implements IStorage, IController {
	// Variables
<<<<<<< HEAD
	private ArrayList<Item> itemList;
=======
	private ArrayList<Item> listOfItems;
	private int noOfItems;
	private int[] noOfItemType;
>>>>>>> parent of 9618ceb (Update Menu.java)
	private static Menu single_instance = null;

	// Constructor
	private Menu() {
<<<<<<< HEAD
		itemList = new ArrayList<Item>();
=======
		noOfItemType = new int[5];
		listOfItems = new ArrayList<Item>();
>>>>>>> parent of 9618ceb (Update Menu.java)
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
		System.out.println("Number of Items: " + itemList.size());
		for (ItemTypes itemType : ItemTypes.values()) {
			printCat(itemType);
		}
	}

<<<<<<< HEAD
	public void printCat(ItemTypes itemType) {
		List<Item> types = splitItemByType().get(itemType);
		switch (itemType) {
			case APPETIZER:
=======
	public void printCat(int type) {
		switch (type) {
			case 1:
>>>>>>> parent of 9618ceb (Update Menu.java)
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
<<<<<<< HEAD
		System.out.println("Number of items: " + types.size());
		for (Item item : types) {
			printItem(item);
=======
		int num;
		for (num = 0; num < noOfItems; num++) {
			tempItem = listOfItems.get(num);
			if (tempItem.getType() == type) {
				printItem(num);
			}
>>>>>>> parent of 9618ceb (Update Menu.java)
		}
		System.out.println();
	}

	public void printItem(int itemNo) {
		tempItem = listOfItems.get(itemNo);
		System.out.printf("%d", tempItem.getID());
		System.out.printf(" " + tempItem.getName() + " $%.2f\n", tempItem.getPrice());
		System.out.printf(tempItem.getDesc() + "\n");
	}

<<<<<<< HEAD
	public Item checkExistance(String ID) {
		for (Item item : itemList) {
			item.getID().equals(ID);
			return item;
=======
	public Item getItem(int ID) {
		int num;
		for (num = 0; num < noOfItems; num++) {
			tempItem = listOfItems.get(num);
			if (tempItem.getID() == ID)
				break;
		}
		if (num == noOfItems) {
			return null;
>>>>>>> parent of 9618ceb (Update Menu.java)
		}
		return null;
	}

	public void sortData() {
		int a,b;
		Item temp;
		for (a = 0; a < itemList.size(); a++) {
			for (b = a; b > 0; b--) {
<<<<<<< HEAD
				temp = itemList.get(b);
				if (temp.getID().compareTo(itemList.get(b-1).getID()) > 0)
					break;
				else {
					itemList.set(b, itemList.get(b-1));
					itemList.set(b-1, temp);
=======
				temp1 = listOfItems.get(b);
				temp0 = listOfItems.get(b - 1);
				if (temp1.getID() >= temp0.getID())
					break;
				else {
					listOfItems.set(b - 1, temp1);
					listOfItems.set(b, temp0);
>>>>>>> parent of 9618ceb (Update Menu.java)
				}
			}
		}
	}

	public void create(Object entities) {
		Item toBeAdded = (Item) entities;
<<<<<<< HEAD
		itemList.add(toBeAdded);
		cleanID();
		sortData();
=======

		noOfItemType[toBeAdded.getType() - 1]++;
		tempItem.setID(toBeAdded.getType() * 100 + noOfItemType[toBeAdded.getType() - 1]);

		noOfItems++;
		listOfItems.add(toBeAdded);

		// storeData();
		sortDB();
>>>>>>> parent of 9618ceb (Update Menu.java)
		storeData();
		loadData();

	}

	public void read() {

	}

	public void delete(Object entities) {
<<<<<<< HEAD
		Item toBeDeleted = (Item) entities;
		itemList.remove(toBeDeleted);
<<<<<<< Updated upstream
		// cleanID
=======
		cleanID();
>>>>>>> Stashed changes
=======

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
>>>>>>> parent of 9618ceb (Update Menu.java)
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

	// TODO: to be sub by loadData
	/*public void loadData() {
		String cut;
		int start, end;
		noOfItems = 0;
		listOfItems.clear();
		for (start = 0; start < 5; start++) {
			noOfItemType[start] = 0;
		}
		try {
			File currentDir = new File("");
			File itemList = new File(
					currentDir.getAbsolutePath() + File.separator + "src" + File.separator + "itemList.txt");
			Scanner myReader = new Scanner(itemList);
			String data = myReader.nextLine();
			data = myReader.nextLine();
			while (myReader.hasNextLine()) {
				tempItem = new Item();
				data = myReader.nextLine();
				// itemID
				start = 0;
				end = data.indexOf(";", start);
				cut = data.substring(start, end);
				tempItem.setID(Integer.parseInt(cut));
				// itemName
				start = end + 1;
				end = data.indexOf(";", start);
				cut = data.substring(start, end);
				tempItem.setName(cut);
				// itemDesc
				start = end + 1;
				end = data.indexOf(";", start);
				cut = data.substring(start, end);
				tempItem.setDesc(cut);
				// itemPrice
				start = end + 1;
				end = data.indexOf(";", start);
				cut = data.substring(start, end);
				tempItem.setPrice(Float.parseFloat(cut));
				// itemType
				start = end + 1;
				end = data.indexOf(";", start);
				cut = data.substring(start, end);
				tempItem.setType(Integer.parseInt(cut));
				noOfItemType[tempItem.getType() - 1]++;
				listOfItems.add(tempItem);
				noOfItems++;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred...");
			e.printStackTrace();
		}
	}*/

	// TODO: to be sub by storeData
	/*public void storeData() {
		int I;
		File currentDir = new File("");
		File newFile = new File(
				currentDir.getAbsolutePath() + File.separator + "src" + File.separator + "itemListSave.txt");
		File oldFile = new File(
				currentDir.getAbsolutePath() + File.separator + "src" + File.separator + "itemList.txt");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {
			newFile.createNewFile();
			Writer myWriter = new FileWriter(newFile);
			myWriter.write("//itemID;itemName;itemDesc;itemPrice;itemType\n");
			myWriter.write("//Type: 0=NIL, 1=Appetizers, 2=Entrees, 3=Sides, 4=Desserts, 5=Drinks\n");
			for (I = 0; I < noOfItems; I++) {
				tempItem = listOfItems.get(I);
				myWriter.write(String.valueOf(tempItem.getID()) + ";");
				myWriter.write(tempItem.getName() + ";");
				myWriter.write(tempItem.getDesc() + ";");
				myWriter.write(formatter.format(tempItem.getPrice()) + ";");
				myWriter.write(String.valueOf(tempItem.getType()) + ";\n");
			}
			myWriter.close();
			oldFile.delete();
			newFile.renameTo(oldFile);
		} catch (Exception e) {
			System.out.println("Error saving...");
			e.printStackTrace();
		}
	}*/

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
<<<<<<< HEAD

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
<<<<<<< Updated upstream
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
=======
		for (Item item : itemByType.get(ItemTypes.APPETIZER)) item.setID(String.valueOf(100+(count++)));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.ENTREE)) item.setID(String.valueOf(200+(count++)));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.SIDE)) item.setID(String.valueOf(300+(count++)));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.DESSERT)) item.setID(String.valueOf(400+(count++)));
		count = 0;
		for (Item item : itemByType.get(ItemTypes.BEVERAGE)) item.setID(String.valueOf(500+(count++)));
>>>>>>> Stashed changes
	}
=======
>>>>>>> parent of 9618ceb (Update Menu.java)

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
}
