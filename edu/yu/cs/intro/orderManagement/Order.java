package edu.yu.cs.intro.orderManagement;

/**
 * models an order placed by a customer. An item in the order can be an instance
 * of either Product or Service
 */
public class Order {
    public Order() {
    }

    /**
     * @return all the items (products and services) in the order
     */
    public Item[] getItems() {
    }

    /**
     * @param b
     * @return the quantity of the given item ordered in this order. Zero if the
     *         item is not in the order.
     */
    public int getQuantity(Item b) {
    }

    /**
     * Add the given quantity of the given item (product or service) to the order
     * * @param item
     * 
     * @param quantity
     */
    public void addToOrder(Item item, int quantity) {
    }

    /**
     * Calculate the total price of PRODUCTS in the order. Must multiply each item's
     * price by the quantity.
     * 
     * @return the total price of products in this order
     */
    public double getProductsTotalPrice() {
    }

    /**
     * Calculate the total price of the SERVICES in the order. Must multiply each
     * item's price by the quantity.
     * 
     * @return the total price of products in this order
     */
    public double getServicesTotalPrice() {
    }

    /**
     * @return has the order been completed by the order management system?
     */
    public boolean isCompleted() {
    }

    /**
     * Indicate if the order has been completed by the order management system
     * * @param completed
     */
    public void setCompleted(boolean completed) {
    }
}