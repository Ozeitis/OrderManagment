package edu.yu.cs.intro.orderManagement;

/**
 * 1) has a Set of services that he can provide Version / Date: 1.1 / December
 * 10, 2020 Demo Program 8 2) can only work on one order at a time - once
 * assigned to a customer, can not take another assignment until 3 other orders
 * have been placed with the order management system 3) is uniquely identified
 * by its ID
 */
public class ServiceProvider {
    /**
     *
     * @param name
     * @param id       unique id of the ServiceProvider
     * @param services set of services this provider can provide
     */
    public ServiceProvider(String name, int id, Set<Service> services) {
    }

    public String getName() {
    }

    public int getId() {
    }

    /**
     * Assign this provider to a customer. Record the fact that he is busy.
     * 
     * @throws IllegalStateException if the provider is currently assigned to a job
     */
    protected void assignToCustomer() {
    }

    /**
     * Free this provider up - is not longer assigned to a customer
     * 
     * @throws IllegalStateException if the provider is NOT currently assigned to a
     *                               job
     */
    protected void endCustomerEngagement() {
    }

    /**
     * @param s add the given service to the set of services this provider can
     *          provide * @return true if it was added, false if not
     */
    protected boolean addService(Service s) {
    }

    /**
     * @param s remove the given service to the set of services this provider can
     *          provide * @return true if it was removed, false if not
     */
    protected boolean removeService(Service s) {
    }

    /**
     * *
     * 
     * @return a COPY of the set of services. MUST NOT return the Set instance
     *         itself, since that would allow a caller to then add/remove services
     */
    public Set<Service> getServices() {
    }

    @Override
    public boolean equals(Object o) {
    }

    @Override
    public int hashCode() {
    }
}