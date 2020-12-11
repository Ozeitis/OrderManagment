package edu.yu.cs.intro.orderManagement;

/**
 * models an order placed by a customer. An item in the order can be an instance
 * of either Product or Service
 */
import java.util.ArrayList;

public class Order {
   private ArrayList<Item> orderList;
   private boolean orderStatus;

   public Order() {
      this.orderList = new ArrayList<>();
   }

   /**
    * @return all the items (products and services) in the order
    */
   public Item[] getItems() {
      return this.orderList.toArray(new Item[this.orderList.size()]);
   }

   /**
    * @param b
    * @return the quantity of the given item ordered in this order. Zero if the
    *         item is not in the order.
    */
   public int getQuantity(Item b) {
      if (!orderList.contains(b)) {
         return 0;
      }
      int quantity = 0;
      for (Item x : orderList) {
         if (x.getClass() == b.getClass()) {
            quantity++;
         }
      }
      return quantity;
   }

   /**
    * Add the given quantity of the given item (product or service) to the order
    * * @param item
    * 
    * @param quantity
    */
   public void addToOrder(Item item, int quantity) {
      for (int i = 0; i < quantity; i++) {
         orderList.add(i, item);
      }
   }

   /**
    * Calculate the total price of PRODUCTS in the order. Must multiply each item's
    * price by the quantity.
    * 
    * @return the total price of products in this order
    */
   public double getProductsTotalPrice() {
      double total = 0.0;
      if (orderList.isEmpty()) {
         return total;
      }
      for (Item i : orderList) {
         if (i instanceof Product) {
            int x = getQuantity(i);
            total = total + (x * i.getPrice());
         }
      }
      return total;
   }

   /**
    * Calculate the total price of the SERVICES in the order. Must multiply each
    * item's price by the quantity.
    * 
    * @return the total price of products in this order
    */
   public double getServicesTotalPrice() {
      double total = 0.0;
      if (orderList.isEmpty()) {
         return total;
      }
      for (Item i : orderList) {
         if (i instanceof Service) {
            int x = getQuantity(i);
            total = total + (x * i.getPrice());
         }
      }
      return total;
   }

   /**
    * @return has the order been completed by the order management system?
    */
   public boolean isCompleted() {
      return orderStatus;
   }

   /**
    * Indicate if the order has been completed by the order management system
    * * @param completed
    */
   public void setCompleted(boolean completed) {
      orderStatus = completed;
   }
}
