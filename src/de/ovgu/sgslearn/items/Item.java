package de.ovgu.sgslearn.items;

import java.io.Serializable;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;

/**
 * the basic item class represents a basic item in game world it could have many
 * subclass such as weapons, armors and so on
 * 
 * @author parallels
 * 
 */
public class Item implements Serializable, ManagedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8205125310371311417L;

	protected int price;

	protected String itemName = "a no name Item";

	public Item(int price) {

		this.price = price;
	}

	public int getPrice() {
		return this.price;
	}

	public String getName() {
		return itemName;
	}

	public void setItemName(String name) {
		this.itemName = name;
	}
}
