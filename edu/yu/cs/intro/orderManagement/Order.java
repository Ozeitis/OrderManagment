package edu.yu.cs.intro.orderManagement;

import java.util.HashMap;
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
		// if(!this.itemsOrderedMap.contains(b)){
		// return 0;
		// }
		// return this.itemsOrderedMap.get(b);

		return this.itemsOrderedMap.getOrDefault(b, 0);
	}

	public void addToOrder(Item item, int quantity) {
		// if(this.itemsOrderedMap.keySet().contains(item)){
		// this.itemsOrderedMap.put(item, this.itemsOrderedMap.get(item) + quantity);
		// }else{
		// this.itemsOrderedMap.put(item, quantity);
		// }

		this.itemsOrderedMap.put(item, this.itemsOrderedMap.getOrDefault(item, 0) + quantity);

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
	protected List<Service> getServicesList()
{
	List<Service> orderServices = new ArrayList<>();
	for (Item i : this.itemsOrderedMap.keySet()) {
	 	if (i instanceof Service) {
	 		orderServices.add(i);
	 	}
	 }
	 return orderServices;
}
protected List<Product> getProductsList()
{
	List<Product> orderProducts = new ArrayList<>();
	for (Item i : this.itemsOrderedMap.keySet()) {
		if (i instanceof Product) {
		orderProducts.add(i);
		}
	}
		return orderProducts;
}
}
