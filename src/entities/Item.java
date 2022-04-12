package entities;

public class Item {
	//Variables
	private int itemID;
	private String itemName;
	private String itemDesc;
	private float itemPrice;
	private int itemType; //0=NIL, 1=Appetizer, 2=Entree, 3=Side, 4=Dessert, 5=Beverage
	
	//Constructor
	public Item() {
		
	}
	
	//Get values
	public int getID() {
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
	
	//Set values
	public void setID(int ID) {
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
