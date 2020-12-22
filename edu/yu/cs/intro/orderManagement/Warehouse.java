
package edu.yu.cs.intro.orderManagement;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Stocks products, fulfills product orders, manages stock of products.
 */
public class Warehouse {
     private HashMap<Product, Integer> currentStockLevel;
     private HashMap<Product, Integer> idealStockLevel;
     private HashMap<Integer, Product> idMap;
     private Set<Product> doNotStock;

     /**
      * create a warehouse, initialize all the instance variables
      */
     protected Warehouse() {
          this.currentStockLevel = new HashMap<>();
          this.idealStockLevel = new HashMap<>();
          this.idMap = new HashMap<>();
          this.doNotStock = new HashSet<>();
     }

     /**
      * @return all unique Products stocked in the warehouse
      */
     protected Set<Product> getAllProductsInCatalog() {
          return currentStockLevel.keySet();
     }

     /**
      * Add a product to the warehouse, at the given stock level.
      * 
      * @param product
      * @param desiredStockLevel the number to stock initially, and also to restock
      *                          to when subsequently restocked Version / Date: 1.1 /
      *                          December 10, 2020 5
      * @throws IllegalArgumentException if the product is in the "do not restock"
      *                                  set, or if the product is already in the
      *                                  warehouse
      */
     protected void addNewProductToWarehouse(Product product, int desiredStockLevel) {
          if (currentStockLevel.containsKey(product)) {
               throw new IllegalArgumentException();
          } else {
               currentStockLevel.put(product, desiredStockLevel);
               idealStockLevel.put(product, desiredStockLevel);
               idMap.put(product.getItemNumber(), product);
          }
     }

     /**
      * If the actual stock is already >= the minimum, do nothing. Otherwise, raise
      * it to minimum OR the default stock level, whichever is greater
      * 
      * @param productNumber
      * @param minimum
      * @throws IllegalArgumentException if the product is in the "do not restock"
      *                                  set, or if it is not in the catalog
      */
     protected void restock(int productNumber, int minimum) {
          if (!isRestockable(productNumber) || !(idMap.containsKey(productNumber))) {
               throw new IllegalArgumentException();
          }
          // int isl = idealStockLevel.get(idMap.get(productNumber));
          int csl = currentStockLevel.get(idMap.get(productNumber));
          if (csl < minimum) {
               if(minimum > idealStockLevel.get(idMap.get(productNumber)){
                    currentStockLevel.put(idMap.get(productNumber), minimum);
               }else{
                    currentStockLevel.put(idMap.get(productNumber), idealStockLevel.get(idMap.get(productNumber));
          }
     }

     /**
      * Set the new default stock level for the given product
      * 
      * @param productNumber
      * @param quantity
      * @return the old default stock level
      * @throws IllegalArgumentException if the product is in the "do not restock"
      *                                  set, or if it is not in the catalog
      */
     protected int setDefaultStockLevel(int productNumber, int quantity) {
          int oldStock = currentStockLevel.get(idMap.get(productNumber));
          if (doNotStock.contains(idMap.get(productNumber)) || isInCatalog(productNumber) == false) {
               throw new IllegalArgumentException();
          } else {
               currentStockLevel.put(idMap.get(productNumber), quantity);
          }
          return oldStock; // correct? It wants the old stock?
     }

     /**
      * @param productNumber
      * @return how many of the given product we have in stock, or zero if it is not
      *         stocked
      */
     protected int getStockLevel(int productNumber) {
          if (!isInCatalog(productNumber)) {
               return 0;
          }
          return currentStockLevel.get(idMap.get(productNumber));
     }

     /**
      * @param itemNumber
      * @return true if the given item number is in the warehouse's catalog, false if
      *         not
      */
     protected boolean isInCatalog(int itemNumber) {
          return idMap.containsKey(itemNumber);
     }

     /**
      * *
      * 
      * @param itemNumber
      * @return false if it's not in catalog or is in the "do not restock" set.
      *         Otherwise true.
      */
     protected boolean isRestockable(int itemNumber) {
          if (!isInCatalog(itemNumber)) {
               return false;
          } else {
               return idealStockLevel.containsKey(idMap.get(itemNumber));
          }
     }

     /**
      * add the given product to the "do not restock" set * @param productNumber
      * 
      * @return the current actual stock level
      */
     protected int doNotRestock(int productNumber) {
          if (!isInCatalog(productNumber) || !isRestockable(productNumber)) {
               throw new IllegalArgumentException();
          }
          doNotStock.add(idMap.get(productNumber));
          idealStockLevel.remove(idMap.get(productNumber));
          return currentStockLevel.get(idMap.get(productNumber));
     }

     /**
      * can the warehouse fulfill an order for the given amount of the given product?
      * 
      * @param productNumber
      * @param quantity
      * @return false if the product is not in the catalog or there are fewer than
      *         quantity of the products in the catalog. Otherwise true.
      */
     protected boolean canFulfill(int productNumber, int quantity) {
          if (!isInCatalog(productNumber) || currentStockLevel.get(idMap.get(productNumber)) < quantity) {
               return false;
          } else
               return true;
     }

     /**
      * Fulfill an order for the given amount of the given product, i.e. lower the
      * stock levels of the product by the given amount
      * 
      * @param productNumber
      * @param quantity
      * @throws IllegalArgumentException if {@link #canFulfill(int, int)} returns
      *                                  false Version / Date: 1.1 / December 10,
      *                                  2020
      * 
      *                                  6
      */
     protected void fulfill(int productNumber, int quantity) {
          if (canFulfill(productNumber, quantity) == false) {
               throw new IllegalArgumentException();
          }
          currentStockLevel.put(idMap.get(productNumber), currentStockLevel.get(idMap.get(productNumber)) - quantity);
     }
}
