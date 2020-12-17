package edu.yu.cs.intro.orderManagement;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class ServiceProvider {
	private String name;
	private int id;
	private Set<Service> services;
	private boolean busy = false;
	// this is to mark whether the service provider is busy or not. it is modified
	// by other methods.

	public ServiceProvider(String name, int id, Set<Service> services) { //add a counter in ordermanagesystem for max 3 uses
		this.name = name;
		this.id = id;
		this.services = new HashSet<>();
		this.services.addAll(services);
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	protected void assignToCustomer() {
		if (this.busy == true) {
			throw new IllegalStateException();
		} else {
			this.busy = true;
		}
	}

	protected void endCustomerEngagement() {
		if (this.busy == false) {
			throw new IllegalStateException();
		} else {
			this.busy = false;
		}
	}

	protected boolean addService(Service s) {
		return this.services.add(s);
	}

	protected boolean removeService(Service s) {
		return this.services.remove(s);
	}

	public Set<Service> getServices() {
		Set<Service> copy = new HashSet<>();
		copy.addAll(this.services);
		return copy;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null) {
			return false;
		}
		if (o instanceof ServiceProvider) { // should be instance of Item?
			ServiceProvider other = (ServiceProvider) o;
			if (this.id == other.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
}
