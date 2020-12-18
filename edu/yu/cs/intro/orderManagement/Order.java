package edu.yu.cs.intro.orderManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * models an order placed by a customer. An item in the order can be an instance
 * of either Product or Service
 */

public class Order {
	private Map<Item, Integer> itemsOrderedMap;
	private boolean orderStatus;

	public Order() {
		this.itemsOrderedMap = new HashMap<>();
		this.orderStatus = false;
	}

	public Item[] getItems() {
		// turn map into keyset and set into array
		Set<Item> setFromMap = this.itemsOrderedMap.keySet();
		return setFromMap.toArray(new Item[setFromMap.size()]);
	}

	public int getQuantity(Item b) {
		if (!this.itemsOrderedMap.containsKey(b)) {
			return 0;
		}
		return this.itemsOrderedMap.get(b);
	}

	public void addToOrder(Item item, int quantity) {
		if (this.itemsOrderedMap.containsKey(item)) {
			this.itemsOrderedMap.put(item, this.itemsOrderedMap.get(item) + quantity);
		} else {
			this.itemsOrderedMap.put(item, quantity);
		}
	}

	public double getProductsTotalPrice() {
		double total = 0.0;
		for (Item item : this.itemsOrderedMap.keySet()) {
			if (item instanceof Product) {
				total += this.itemsOrderedMap.get(item) * item.getPrice();
			}
		}
		return total;
	}

	public double getServicesTotalPrice() {
		double total = 0.0;
		for (Item item : this.itemsOrderedMap.keySet()) {
			if (item instanceof Service) {
				total += this.itemsOrderedMap.get(item) * item.getPrice();
			}
		}
		return total;
	}

	public boolean isCompleted() {
		return this.orderStatus;
	}

	public void setCompleted(boolean completed) {
		this.orderStatus = completed;
	}

	protected List<Service> getServicesList() {
		List<Service> orderServices = new ArrayList<>();
		for (Item i : this.itemsOrderedMap.keySet()) {
			if (i instanceof Service) {
				orderServices.add((Service) i);
			}
		}
		return orderServices;
	}

	protected List<Product> getProductsList() {
		List<Product> orderProducts = new ArrayList<>();
		for (Item i : this.itemsOrderedMap.keySet()) {
			if (i instanceof Product) {
				orderProducts.add((Product) i);
			}
		}
		return orderProducts;
	}
}
