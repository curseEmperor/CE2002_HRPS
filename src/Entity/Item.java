package Entity;

import Enums.ItemTypes;

public class Item extends Entities {
	// Variables
	private String itemID;
	private String itemName;
	private String itemDesc;
	private float itemPrice;
	private ItemTypes itemType;
	// private int itemType; // 0=NIL, 1=Appetizer, 2=Entree, 3=Side, 4=Dessert,
	// 5=Beverage

	// Constructor
	public Item() {

	}

	public Item(String itemName, String itemDesc, float itemPrice, ItemTypes itemType) {
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

	public ItemTypes getType() {
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

	public void setType(ItemTypes type) {
		itemType = type;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.itemID + "\t" + this.itemName + "\t $" + this.itemPrice);

		return result.toString();
	}
}
