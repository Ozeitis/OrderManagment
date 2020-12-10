package edu.yu.cs.intro.orderManagement;
/**
 * An implementation of item which represents a Service provided by the business.
 * Has a price per billable hour as well a number of hours this service takes.
 * The price returned by getPrice must be the per hour price multiplied by the number of hours the
service takes
 */
public class Service implements Item {
public Service(double pricePerHour, int numberOfHours, int serviceID, String description){}
/**
* @return the number of house this service takes */
public int getNumberOfHours(){} @Override
public int getItemNumber() {} @Override
public String getDescription() {} @Override
public double getPrice() {} @Override
public boolean equals(Object o) {} @Override
public int hashCode() {} }