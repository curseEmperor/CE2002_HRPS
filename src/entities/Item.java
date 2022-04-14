package entities;

import java.io.Serializable;

public class Item implements Serializable {
	// Variables
	private String itemID;
	private String itemName;
	private String itemDesc;
	private float itemPrice;
	private int itemType; // 0=NIL, 1=Appetizer, 2=Entree, 3=Side, 4=Dessert, 5=Beverage

	// Constructor
	public Item() {

	}

<<<<<<< Updated upstream
	public Item(String itemName, String itemDesc, float itemPrice, int itemType) {
		this.itemID = 0;
=======
	public Item(String itemName, String itemDesc, float itemPrice, ItemTypes itemType) {
>>>>>>> Stashed changes
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.itemPrice = itemPrice;
		this.itemType = itemType;
	}

	// Get values
	public String getID() {
		return itemID;
	}

	public String getName() {
		return itemName;
	}

	public String getDesc() {
		return itemDesc;
	}

	public float getPrice() {
		return itemPrice;
	}

	public int getType() {
		return itemType;
	}

	// Set values
	public void setID(String ID) {
		itemID = ID;
	}

	public void setName(String name) {
		itemName = name;
	}

	public void setDesc(String desc) {
		itemDesc = desc;
	}

	public void setPrice(float price) {
		itemPrice = price;
	}

	public void setType(int type) {
		itemType = type;
	}
}
