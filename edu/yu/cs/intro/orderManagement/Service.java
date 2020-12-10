package edu.yu.cs.intro.orderManagement;
public class Service implements Item {
    private double pricePerHour;
    private int numberOfHours;
    private int serviceID;
    private String description;

	public Service(double pricePerHour, int numberOfHours, int serviceID, String description){
		this.pricePerHour = pricePerHour;
		this.numberOfHours = numberOfHours;
		this.serviceID = serviceID;
		this.description = description;
	}

    @Override
	public int getNumberOfHours(){
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
		if (o == this) return true;
	    if (!(o instanceof Service) || o == null) {
	        return false;
	    }
	    Service serv = (Service) o;
	    return serv.getPrice() == this.pricePerHour && serv.getNumberOfHours() == this.numberOfHours && serv.getItemNumber() == this.serviceID && serv.getDescription().equals(this.description);
	} 

	@Override //This needs to be checked, I'm not confident.
	public int hashCode() {
		return Objects.hash(this.pricePerHour, this.numberOfHours, this.serviceID, this.description);
	}

}