package edu.yu.cs.intro.orderManagement;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class Warehouse {
	private HashMap<Product, Integer> currentStockLevel;
	private HashMap<Product, Integer> idealStockLevel;
	private HashMap<Integer, Product> idMap;
	private Set<Product> doNotStock;

	protected Warehouse() {
		this.currentStockLevel = new HashMap<>();
		this.idealStockLevel = new HashMap<>();
		this.idMap = new HashMap<>();
		this.doNotStock = new HashSet<>();
	}

	protected Set<Product> getAllProductsInCatalog() {
		return currentStockLevel.keySet();
	}

	protected void addNewProductToWarehouse(Product product, int desiredStockLevel) {
		if (currentStockLevel.containsKey(product)) {
			throw new IllegalArgumentException();
		} else {
			currentStockLevel.put(product, desiredStockLevel);
			idealStockLevel.put(product, desiredStockLevel);
			idMap.put(product.getItemNumber(), product);
		}
	}

	protected void restock(int productNumber, int minimum) {
		if (!isRestockable(productNumber) || !(idMap.containsKey(productNumber))) {
			throw new IllegalArgumentException();
		}
		int csl = currentStockLevel.get(idMap.get(productNumber));
		if (csl < minimum) {
			//potential fix
			System.out.println("Ideal of p1 (should be 1): " + this.idealStockLevel.get(idMap.get(productNumber)));
			if(minimum < this.idealStockLevel.get(this.idMap.get(productNumber))){
				currentStockLevel.put(idMap.get(productNumber), minimum);
			}else{
				currentStockLevel.put(idMap.get(productNumber), this.idealStockLevel.get(this.idMap.get(productNumber)));
			}
			
		
		}
	}

	protected int setDefaultStockLevel(int productNumber, int quantity) {
		int oldStock = this.idealStockLevel.get(idMap.get(productNumber));//currentStockLevel.get(idMap.get(productNumber)
		if (doNotStock.contains(idMap.get(productNumber)) || isInCatalog(productNumber) == false) {
			throw new IllegalArgumentException();
		} else {
			System.out.println("OG ideal stock: " + oldStock);
			this.idealStockLevel.put(idMap.get(productNumber), quantity);//currentStockLevel.get(idMap.get(productNumber)
			System.out.println("The new ideal stock of p1: " + this.idealStockLevel.get(idMap.get(productNumber)));
		}
		return oldStock; // correct? It wants the old stock?
	}

	protected int getStockLevel(int productNumber) {
		if (!isInCatalog(productNumber)) {
			return 0;
		}
		return currentStockLevel.get(idMap.get(productNumber));
	}

	protected boolean isInCatalog(int itemNumber) {
		return idMap.containsKey(itemNumber);
	}

	protected boolean isRestockable(int itemNumber) {
		if (!isInCatalog(itemNumber)) {
			return false;
		} else {
			return idealStockLevel.containsKey(idMap.get(itemNumber));
		}
	}

	protected int doNotRestock(int productNumber) {
		if (!isInCatalog(productNumber) || !isRestockable(productNumber)) {
			throw new IllegalArgumentException();
		}
		doNotStock.add(idMap.get(productNumber));
		idealStockLevel.remove(idMap.get(productNumber));
		return currentStockLevel.get(idMap.get(productNumber));
	}

	protected boolean canFulfill(int productNumber, int quantity) {
		if (!isInCatalog(productNumber) || currentStockLevel.get(idMap.get(productNumber)) < quantity) {
			return false;
		}
		return true;
	}

	protected void fulfill(int productNumber, int quantity) {
		if (canFulfill(productNumber, quantity) == false) {
			throw new IllegalArgumentException();
		}
		currentStockLevel.put(idMap.get(productNumber), currentStockLevel.get(idMap.get(productNumber)) - quantity);
		//potential bug fix
		for(Product prdct : this.currentStockLevel.keySet()){
			if(this.currentStockLevel.get(prdct) == 0 && isRestockable(prdct.getItemNumber())){
				restock(prdct.getItemNumber(), this.idealStockLevel.get(prdct));
			}
		}
	}
}
