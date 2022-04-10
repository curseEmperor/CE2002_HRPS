package Controller;

import java.io.File; //Import the File class
import java.io.FileNotFoundException; //Import this class to handle errors
import java.io.FileWriter; //Import the Writer class to create and write files
import java.io.Writer; //Import the Writer class to create and write files
import java.text.DecimalFormat; //Import this class to set float number format when converting to string
import java.text.NumberFormat; //Import this class to set float number format when converting to string
import java.util.Scanner; //Import the Scanner class to read text files
import java.util.ArrayList; //Import ArrayList class
import entities.Item;
import Controller.DataBase;

public class Menu implements DataBase {
	//Variables
	private ArrayList<Item> listOfItems;
	private int noOfItems;
	private int[] noOfItemType;
	private static Menu single_instance = null;
	private Item tempItem;
	
	//Constructor
	private Menu() {
		noOfItemType = new int[5];
		listOfItems = new ArrayList<Item>();
		importDB();
		sortDB();
	}
	public static Menu getInstance() {
		if (single_instance == null) single_instance = new Menu();
		return single_instance;
	}

	//Behaviours
	public void printMenu() {
		int num;
		//System.out.println("Number of Items: " + String.valueOf(noOfItems));
		for (num=1;num<=5;num++) {
			//System.out.println("Number of Items: " + String.valueOf(noOfItemType[num-1]));
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
		for (num=0;num<noOfItems;num++) {
			tempItem = listOfItems.get(num);
			if (tempItem.getType()==type) {
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
		for (num=0;num<noOfItems;num++) {
			tempItem = listOfItems.get(num);
			if (tempItem.getID()==ID) break;
		}
		if (num==noOfItems) {
			System.out.println("Item does not exist");
			return null;
		}
		return tempItem;
	}
	public void sortDB() {
	    int a,b;
	    Item temp0, temp1;
	    for (a=0;a<noOfItems;a++){
	        for (b=a;b>0;b--){
	        	temp1 = listOfItems.get(b);
	        	temp0 = listOfItems.get(b-1);
	            if (temp1.getID() >= temp0.getID()) break;
	            else {
	            	listOfItems.set(b-1, temp1);
	            	listOfItems.set(b, temp0);
	            }
	        }
	    }
	}
	public void addItem() {
		int I;
		float F;
		String S;
		tempItem = new Item();
		Scanner sc = new Scanner(System.in);
		System.out.println("--Adding item--\n1. Appetizer\n2. Entree\n3. Side\n4. Dessert\n5. Beverage\nItem type: ");
		I = sc.nextInt();
		sc.nextLine();
		tempItem.setType(I);
		noOfItemType[I-1]++;
		tempItem.setID(I*100+noOfItemType[I-1]);
		System.out.println("Item name: ");
		S = sc.nextLine();
		tempItem.setName(S);
		System.out.println("Item description: ");
		S = sc.nextLine();
		tempItem.setDesc(S);
		System.out.println("Item price: ");
		F = sc.nextFloat();
		tempItem.setPrice(F);
		noOfItems++;
		listOfItems.add(tempItem);
		sortDB();
		saveDB();
		importDB();
		System.out.println("\nItem added!\n");
	}
	public void removeItem() {
		int I, ID;
		Scanner sc = new Scanner(System.in);
		System.out.println("--Removing item--\nItem ID: ");
		ID = sc.nextInt();
		for (I=0;I<noOfItems;I++) {
			tempItem = listOfItems.get(I);
			if (tempItem.getID()==ID) break;
		}
		if (I==noOfItems) {
			System.out.println("Item does not exist\n");
			return;
		}
		int type;
		type = tempItem.getType();
		noOfItems--;
		listOfItems.remove(I);
		for (I=I;I<noOfItems;I++) {
			tempItem = listOfItems.get(I);
			if (tempItem.getType() == type) tempItem.setID(tempItem.getID()-1);
			else break;
		}
		saveDB();
		importDB();
		System.out.println("Item deleted...\n");
	}
	public void editItem() {
		Item I;
		int ID,choice;
		float F;
		String S;
		Scanner sc = new Scanner(System.in);
		System.out.println("--Editing item--\nItem ID: ");
		ID = sc.nextInt();
		I = getItem(ID);
		if (I==null) return;
		System.out.println("1. Name\n2. Description\n3. Price");
		choice = sc.nextInt();
		sc.nextLine();
		switch (choice) {
		case 1:
			System.out.println("New item name: ");
			S = sc.nextLine();
			I.setName(S);
			break;
		case 2:
			System.out.println("New item description: ");
			S = sc.nextLine();
			I.setDesc(S);
			break;
		case 3:
			System.out.println("New item price: ");
			F = sc.nextFloat();
			I.setPrice(F);
			break;
		default:
			break;
		}
		saveDB();
		importDB();
		System.out.println("Item edited!\n");
	}
	public void importDB() {
		String cut;
		int start,end;
		noOfItems = 0;
		listOfItems.clear();
		for (start=0;start<5;start++) {
			noOfItemType[start] = 0;
		}
		try {
			File currentDir = new File("");
			File itemList = new File(currentDir.getAbsolutePath() + File.separator + "src" + File.separator + "itemList.txt");
			Scanner myReader = new Scanner(itemList);
			String data = myReader.nextLine(); data = myReader.nextLine();
		    while (myReader.hasNextLine()) {
		      tempItem = new Item();
		      data = myReader.nextLine();
		      //itemID
		      start=0;
		      end=data.indexOf(";",start);
		      cut = data.substring(start, end);
		      tempItem.setID(Integer.parseInt(cut));
		      //itemName
		      start=end+1;
		      end=data.indexOf(";",start);
		      cut = data.substring(start, end);
		      tempItem.setName(cut);
		      //itemDesc
		      start=end+1;
		      end=data.indexOf(";",start);
		      cut = data.substring(start, end);
		      tempItem.setDesc(cut);
		      //itemPrice
		      start=end+1;
		      end=data.indexOf(";",start);
		      cut = data.substring(start, end);
		      tempItem.setPrice(Float.parseFloat(cut));
		      //itemType
		      start=end+1;
		      end=data.indexOf(";",start);
		      cut = data.substring(start, end);
		      tempItem.setType(Integer.parseInt(cut));
		      noOfItemType[tempItem.getType()-1]++;
		      listOfItems.add(tempItem);
		      noOfItems++;
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
		    System.out.println("An error occurred...");
		    e.printStackTrace();
		}
	}
	public void saveDB() {
		int I;
		File currentDir = new File("");
		File newFile = new File(currentDir.getAbsolutePath() + File.separator + "src" + File.separator + "itemListSave.txt");
		File oldFile = new File(currentDir.getAbsolutePath() + File.separator + "src" + File.separator + "itemList.txt");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {
			newFile.createNewFile();
			Writer myWriter = new FileWriter(newFile);
			myWriter.write("//itemID;itemName;itemDesc;itemPrice;itemType\n");
			myWriter.write("//Type: 0=NIL, 1=Appetizers, 2=Entrees, 3=Sides, 4=Desserts, 5=Drinks\n");
			for (I=0;I<noOfItems;I++) {
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
	}
}
