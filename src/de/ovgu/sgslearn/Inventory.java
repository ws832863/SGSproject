package de.ovgu.sgslearn;

import java.io.Serializable;
import java.util.Vector;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;

import de.ovgu.sgslearn.items.Item;

public class Inventory implements ManagedObject, Serializable {

	/**
	 * if you want ot have a ManagedObject as an instance variable of another
	 * ManagedObject you must instead store a ManagedReference to that
	 * ManagedObject this class represents a Inventory of a user, it store other
	 * item(also ManagedObject) it stores the managedreference.
	 * 
	 */
	private static final long serialVersionUID = 9210902541965051562L;

	protected Vector<ManagedReference<Item>> items;

	public Inventory() {
		items = new Vector<ManagedReference<Item>>();
	}

	public void addItem(Item i) {
		has(i);
		DataManager dm = AppContext.getDataManager();
		ManagedReference<Item> ref = dm.createReference(i);
		items.add(ref);
	}

	public Item getItem(int ix) {
		ManagedReference<Item> ref = items.get(ix);
		return ref.get();
	}

	/**
	 * if item exists in this inventory, return its index if not exists return
	 * -1;
	 * 
	 * @param item
	 * @return
	 */
	public int has(Item item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).get().getName().equals(item.getName())) {
				return i;
			}
		}
		return -1;
	}

	public int getItemsCount() {
		return items.size();
	}

	public void removeItem(Item item) {
		int index = has(item);

		if (index >= 0) {
			items.remove(index);
			System.out
					.println("remove item at index " + index + " sucessfully");
		}
	}

	public void listAllItems() {
		System.out.println("------------------------------------");
		System.out.println("  " + getItemsCount() + " items in this inventory");
		System.out.println("------------------------------------");
		for (int i = 0; i < items.size(); i++) {
			System.out.print("index = " + i + " ||");
			System.out.println("Item Name: " + items.get(i).get().getName()
					+ "|| Item Price: " + items.get(i).get().getPrice());
		}
		System.out.println("------------------------------------");
	}
}
