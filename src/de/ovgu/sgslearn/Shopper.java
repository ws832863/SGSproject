package de.ovgu.sgslearn;

import java.io.Serializable;
import java.util.Vector;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;

import de.ovgu.sgslearn.items.Item;

public class Shopper implements ManagedObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8328646715109297668L;

	protected int money;;
	protected Vector<ManagedReference<Item>> stuffs;

	public Shopper() {
		this.money = 500;
		this.stuffs = new Vector<ManagedReference<Item>>();
	}

	public int getMoney() {
		return this.money;
	}

	public int getItemCount() {
		return stuffs.size();
	}

	public Item getItem(int ix) {
		return stuffs.get(ix).get();
	}

	public boolean buy(Item item, Inventory inventory) {

		DataManager dm = AppContext.getDataManager();
		int price = item.getPrice();
		dm.markForUpdate(this);
		if (price <= money) {
			dm.markForUpdate(inventory);
			int index = inventory.has(item);
			if (index >= 0) {
				stuffs.add(dm.createReference(item));
				inventory.removeItem(item);
				money -= price;
				return true;
			}
		}
		System.out.println("not enouch money");
		return false;
	}

	public void listStuffs() {
		if (stuffs.size() == 0) {
			System.out
					.println("---------------nothing in bag-------------------");

		} else {
			System.out.println("------------- users stuffs "
					+ this.getItemCount() + " items--------------");
			for (int i = 0; i < stuffs.size(); i++) {
				System.out.println("index " + i + " || name: "
						+ stuffs.get(i).get().getName() + " || price :"
						+ stuffs.get(i).get().getPrice());
			}
			System.out.println("-------------------------------------------");
		}
	}
}
