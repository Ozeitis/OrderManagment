package edu.yu.cs.intro.orderManagement;

import java.util.Objects;

public class Service implements Item {
	private double pricePerHour;
	private int numberOfHours;
	private int serviceID;
	private String description;

	public Service(double pricePerHour, int numberOfHours, int serviceID, String description) {
		this.pricePerHour = pricePerHour;
		this.numberOfHours = numberOfHours;
		this.serviceID = serviceID;
		this.description = description;
	}

	public int getNumberOfHours() {
		return this.numberOfHours;
	}

	@Override
	public int getItemNumber() {
		return this.serviceID;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public double getPrice() {
		return this.pricePerHour;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null) {
			return false;
		}
		if (o instanceof Service) { // should be instance of Item?
			Service other = (Service) o;
			if (this.serviceID == other.getItemNumber()) {
				return true;
			}
		}
		return false;
	}

	@Override // This needs to be checked, I'm not confident.
	public int hashCode() {
		return Objects.hash(this.serviceID);
	}

}
