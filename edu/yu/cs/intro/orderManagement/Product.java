package edu.yu.cs.intro.orderManagement;

import java.util.Objects;

/**
 * Is a "physical" item that is "stocked" in the warehouse.
 */
public class Product implements Item {

  private String name;
  private double price;
  private int productID;

  public Product(String name, double price, int productID) {
    this.name = name;
    this.price = price;
    this.productID = productID;
  }

  @Override
  public int getItemNumber() {
    return this.productID;
  }

  @Override
  public String getDescription() {
    return this.name; // ASSUMING THIS IS CORRECT OM PIAZZA THEY DID NOT CONFIRM.
  }

  @Override
  public double getPrice() {
    return this.price;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (o == null) {
      return false;
    }
    if (o instanceof Product) { // should be instance of Item?
      Product other = (Product) o;
      if (this.productID == other.getItemNumber()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(productID);
  }
}