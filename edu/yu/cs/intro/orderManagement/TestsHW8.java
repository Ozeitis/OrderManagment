package edu.yu.cs.intro.orderManagement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestsHW8 {

	public static void main(String[] args) {
		System.out.println("Testing Service:");
		testService();
		System.out.println();
		System.out.println("Testing Product:");
		testProduct();
		System.out.println();
		System.out.println("Testing ServiceProvider:");
		testServiveProvider();
		System.out.println();
		System.out.println("Testing Warehouse:");
		testWarehouse();
	}

	public static void testService(){
		double pricePerHour = 20;
		int numberOfHours = 4;
		int serviceID = 221;
		String description = "RunsAService";

		Service testItem = new Service(pricePerHour, numberOfHours, serviceID, description);

		System.out.println("The number of hours is: " + testItem.getNumberOfHours());
		System.out.println("The service ID is: " + testItem.getItemNumber());
		System.out.println("The service is described as: " + testItem.getDescription());
		System.out.println("The service costs: " + testItem.getPrice());

		Item testEqual1 = new Service(30, 5, 444, "RunsAService");
		Item testEqual2 = new Service(20, 4, 221, "RunsAService");

		System.out.println("testEqual1 does not equal testItem, equals() returns: " + testItem.equals(testEqual1));
		System.out.println("testEqual2 does equal testItem: " + (testItem.hashCode() == testEqual2.hashCode()));

	}

	public static void testProduct(){
		double price = 15;
		int productID = 212;
		String name = "Ball";

		Product testItem = new Product(name, price, productID);

		System.out.println("The product ID is: " + testItem.getItemNumber());
		System.out.println("The product is described as: " + testItem.getDescription());
		System.out.println("The product costs: " + testItem.getPrice());

		Item testEqual1 = new Product("Stick", 2, 44);
		Item testEqual2 = new Product("Ball", 15, 212);

		System.out.println("testEqual1 does not equal testItem, equals() returns: " + testItem.equals(testEqual1));
		System.out.println("testEqual2 does equal testItem: " + (testItem.hashCode() == testEqual2.hashCode()));

	}

	public static void testServiveProvider(){
		String name = "Coach";
		int id = 21;
		Set<Service> services = new HashSet<>();
		Service s1 = new Service(15, 2, 1, "Drills");
		Service s2 = new Service(30, 1, 2, "Workouts");
		Service s3 = new Service(50, 2, 3, "Games");
		Service s4 = new Service(10, 1, 4, "Diet");
		services.add(s1);
		services.add(s2);
		services.add(s3);

		ServiceProvider testProvider = new ServiceProvider(name, id, services);
		System.out.println("Title: " + testProvider.getName() + ", ID: " + testProvider.getId() + ", Services: " + setToString(services));

		testProvider.assignToCustomer();
		System.out.println("Assigned"); //Not sure how to test this

		testProvider.endCustomerEngagement();
		System.out.println("Unengaged"); //Not sure how to test this

		System.out.println("s4 was added to services: " + testProvider.addService(s4));
		System.out.println("s1 was removed from services: " + testProvider.removeService(s1));
		System.out.println("Current services: " + setToString(testProvider.getServices()));

		Set<Service> copy = testProvider.getServices();
		System.out.println("A copy was made and s1 was readded to services: " + testProvider.addService(s1));
		System.out.println("Copied services: " + setToString(copy));
		System.out.println("Current services: " + setToString(testProvider.getServices()));

		ServiceProvider testEqual1 = new ServiceProvider("Steve", 33, testProvider.getServices());
		ServiceProvider testEqual2 = new ServiceProvider("Shalom", 21, testProvider.getServices());

		System.out.println("testEqual1 does not equal testItem, equals() returns: " + testProvider.equals(testEqual1));
		System.out.println("testEqual2 does equal testItem: " + (testProvider.hashCode() == testEqual2.hashCode()));


	}
	public static String setToString(Set<Service> inputService){
		String returnString = "";
		for(Service s : inputService){
			returnString += s.getDescription() + ", ";
		}
		return returnString;
	}

	public static void testWarehouse(){
		Warehouse testWH = new Warehouse();
		System.out.println("Empty Warehouse created");

		Product testStock1 = new Product("Ball", 15, 212);
		Product testStock2 = new Product("Stick", 2, 44);
		testWH.addNewProductToWarehouse(testStock1, 100);
		testWH.addNewProductToWarehouse(testStock2, 50);
		System.out.println("Current catalog: " + setToStringProducts(testWH.getAllProductsInCatalog()));
		System.out.println("Current stock of Balls: " + testWH.getStockLevel(212));
		System.out.println("There is a product whose ID is 44 in the testWH: " + testWH.isInCatalog(testStock2.getItemNumber()));
		
		System.out.println("testStock1 is on doNotRestock list, and this is how many remain currently: " + testWH.doNotRestock(testStock1.getItemNumber()));	
		

	}
	public static String setToStringProducts(Set<Product> input){
		String returnString = "";
		for(Product p : input){
			returnString += p.getDescription() + ", ";
		}
		return returnString;
	}



}
