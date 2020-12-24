package edu.yu.cs.intro.orderManagement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestsHW8 {

	public static void main(String[] args) {
		// System.out.println("Testing Service:");
		// testService();
		// System.out.println();
		// System.out.println("Testing Product:");
		// testProduct();
		// System.out.println();
		// System.out.println("Testing ServiceProvider:");
		// testServiveProvider();
		// System.out.println();
		// System.out.println("Testing Order:");
		// testOrder();
		// System.out.println();
		// System.out.println("Testing Warehouse:");
		// testWarehouse();
		// System.out.println();
		// System.out.println("Testing OMS:");
		// testOMS();

		// System.out.println("Testing validateProducts:");
		// testValidateProducts();
		// System.out.println("Testing runDownStockViaOrdersRestoreToDefaultLevel:");
		// runDownStockViaOrdersRestoreToDefaultLevel();
		System.out.println("Testing restockToLowerAndGetStockLevel:");
		restockToLowerAndGetStockLevel();

	}

	public static void testService() {
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

	public static void testProduct() {
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

	public static void testServiveProvider() {
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
		System.out.println("Title: " + testProvider.getName() + ", ID: " + testProvider.getId() + ", Services: "
				+ setToString(services));

		testProvider.assignToCustomer();
		System.out.println("Assigned"); // Not sure how to test this

		testProvider.endCustomerEngagement();
		System.out.println("Unengaged"); // Not sure how to test this

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

	public static String setToString(Set<Service> inputService) {
		String returnString = "";
		for (Service s : inputService) {
			returnString += s.getDescription() + ", ";
		}
		return returnString;
	}

	public static void testOrder() {
		Order testOrder = new Order();
		System.out.println("New Order created");

		double pricePerHour = 20;
		int numberOfHours = 4;
		int serviceID = 221;
		String description = "RunsAService";
		double price = 15;
		int productID = 212;
		String name = "Ball";
		Item testProduct = new Product(name, price, productID);
		Item testService = new Service(pricePerHour, numberOfHours, serviceID, description);
		testOrder.addToOrder(testProduct, 30);
		testOrder.addToOrder(testService, 3);

		for (Item itm : testOrder.getItems()) {
			System.out.println(testOrder.getQuantity(itm) + " of " + itm.getDescription() + " at " + itm.getPrice());
		}
		System.out.println("Services will cost $" + testOrder.getServicesTotalPrice() + " and Products will colt $"
				+ testOrder.getProductsTotalPrice());

		// check completing
	}

	public static void testWarehouse() {
		Warehouse testWH = new Warehouse();
		System.out.println("Empty Warehouse created");

		Product testStock1 = new Product("Ball", 15, 212);
		Product testStock2 = new Product("Stick", 2, 44);
		testWH.addNewProductToWarehouse(testStock1, 100);
		testWH.addNewProductToWarehouse(testStock2, 50);
		System.out.println("Current catalog: " + setToStringProducts(testWH.getAllProductsInCatalog()));
		System.out.println("Current stock of Balls: " + testWH.getStockLevel(212));
		System.out.println(
				"There is a product whose ID is 44 in the testWH: " + testWH.isInCatalog(testStock2.getItemNumber()));

		System.out.println("testStock1 is on doNotRestock list, and this is how many remain currently: "
				+ testWH.doNotRestock(testStock1.getItemNumber()));

		System.out.println("The warehouse can fulfill the order: " + testWH.canFulfill(212, 50));
		System.out.println("Current stock of Balls: " + testWH.getStockLevel(212));
		System.out.println("Balls can be restocked: " + testWH.isRestockable(212));
		// Need to check later, after order is placed, that stock changes, and fulfills

	}

	public static String setToStringProducts(Set<Product> input) {
		String returnString = "";
		for (Product p : input) {
			returnString += p.getDescription() + ", ";
		}
		return returnString;
	}

	public static void testOMS() {
		Set<Product> prdcts = new HashSet<>();
		Product testStock1 = new Product("Ball", 15, 212);
		Product testStock2 = new Product("Stick", 2, 44);
		prdcts.add(testStock1);
		prdcts.add(testStock2);

		Set<ServiceProvider> svcPvdrs = new HashSet<>();
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
		testProvider.addService(s4);
		testProvider.removeService(s1);
		Set<Service> copy = testProvider.getServices();
		testProvider.addService(s1);
		ServiceProvider testEqual1 = new ServiceProvider("Steve", 33, copy);
		svcPvdrs.add(testProvider);
		svcPvdrs.add(testEqual1);

		OrderManagementSystem testOMS = new OrderManagementSystem(prdcts, 10, svcPvdrs);
		System.out.println("OMS created");

		Order order = new Order();
		order.addToOrder(testStock1, 40);
		order.addToOrder(s4, 1);
		testOMS.placeOrder(order);

		Set<Product> catalog = testOMS.getProductCatalog();
		Set<Service> offeredServices = testOMS.getOfferedServices();
		System.out.println("testOMS catalog: " + setToStringProducts(catalog));
		System.out.println("testOMS offeredServices: " + setToString(offeredServices));

		// idk the rest

	}

	// public static testValidateProducts(){

	// }

	public static void runDownStockViaOrdersRestoreToDefaultLevel() {
		int ogDefaultStock = 5;
		Set<Product> prdcts = new HashSet<>();
		Product p1 = new Product("p1", 15, 1);
		Product p2 = new Product("p2", 2, 2);
		prdcts.add(p1);
		prdcts.add(p2);
		Set<ServiceProvider> svcPvdrs = new HashSet<>();
		OrderManagementSystem testOMS = new OrderManagementSystem(prdcts, ogDefaultStock, svcPvdrs);
		System.out.println("OMS created");

		Order o1 = new Order();
		o1.addToOrder(p2, 4);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed r1");

		o1 = new Order();
		o1.addToOrder(p2, 1);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed r2");

		testOMS.discontinueItem(p2);
		o1 = new Order();
		o1.addToOrder(p2, 5);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed r3, p2 discont");

		o1 = new Order();
		o1.addToOrder(p2, 1);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed again, cannot fulfill");

	}

	public static void restockToLowerAndGetStockLevel() {
		int ogDefaultStock = 5;
		Set<Product> prdcts = new HashSet<>();
		Product p1 = new Product("p1", 15, 1);
		Product p2 = new Product("p2", 2, 2);
		prdcts.add(p1);
		prdcts.add(p2);
		Set<ServiceProvider> svcPvdrs = new HashSet<>();
		OrderManagementSystem testOMS = new OrderManagementSystem(prdcts, ogDefaultStock, svcPvdrs);
		System.out.println("OMS created");

		testOMS.setDefaultProductStockLevel(p1, 1);

		Order o1 = new Order();
		o1.addToOrder(p1, 5);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed r1");

		o1 = new Order();
		o1.addToOrder(p1, 5);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed r2");

		testOMS.setDefaultProductStockLevel(p1, 3);

		o1 = new Order();
		o1.addToOrder(p1, 5);
		testOMS.placeOrder(o1);
		System.out.println("o1 placed r3");

	}

}
