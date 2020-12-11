package edu.yu.cs.intro.orderManagement;

import java.util.Set;
import java.util.HashSet;

public class ServiceProvider {
	private String name;
	private int id;
	private Set<Service> services;
	private boolean busy = false;
	// this is to mark whether the service provider is busy or not. it is modified
	// by other methods.

	public ServiceProvider(String name, int id, Set<Service> services) {
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
			throw new IllegalStateException;
		} else {
			this.busy = true;
		}
	}

	protected void endCustomerEngagement() {
		if (this.busy == false) {
			throw new IllegalStateException;
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
		Set<Service> copy = new Set<>();
		copy.addAll(this.services);
		return copy;
	}

	@Override
	public boolean equals(Object o) {
		if (this = o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) { // You can probably compine the tests which return false
			return false;
		}
		if (this.getId = o.getId) {
			return true;
		} else
			return false; // you can just return this.getId = o.getId and cut down the lines of code
	}

	@Override
	public int hashCode(){
		int cipher = this.id * 54;
		for(int a = 0; a <) //What is this?
	return 
	}

}
