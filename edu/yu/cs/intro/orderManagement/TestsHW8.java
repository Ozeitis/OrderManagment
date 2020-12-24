package edu.yu.cs.intro.orderManagement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

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

		

		// Resolved - but Judah's code made weird return for runDownStockViaOrdersRestoreToDefaultLevel
		System.out.println();
		System.out.println("Testing testAddGetCatalog:");
		testAddGetCatalog();

		System.out.println();
		System.out.println("Testing unsuccessfulValidateServicesNotEnoughProviders:");
		unsuccessfulValidateServicesNotEnoughProviders();

		//Unresolved
		System.out.println();
		System.out.println("Testing successfulValidateProducts:");
		successfulValidateProducts();

		System.out.println();
		System.out.println("Testing restockToLowerAndGetStockLevel:");
		restockToLowerAndGetStockLevel();

		System.out.println();
		System.out.println("Testing runDownStockViaOrdersRestoreToDefaultLevel:");
		runDownStockViaOrdersRestoreToDefaultLevel();
		

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



	public static void testOrder(){
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

		for(Item itm : testOrder.getItems()){
			System.out.println(testOrder.getQuantity(itm) + " of " + itm.getDescription() + " at " + itm.getPrice());
		}
		System.out.println("Services will cost $" + testOrder.getServicesTotalPrice() + " and Products will colt $" + testOrder.getProductsTotalPrice());

		//check completing
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


		System.out.println("The warehouse can fulfill the order: " + testWH.canFulfill(212, 50));
		System.out.println("Current stock of Balls: " + testWH.getStockLevel(212));
		System.out.println("Balls can be restocked: " + testWH.isRestockable(212));
		//Need to check later, after order is placed, that stock changes, and fulfills

	}
	public static String setToStringProducts(Set<Product> input){
		String returnString = "";
		for(Product p : input){
			returnString += p.getDescription() + ", ";
		}
		return returnString;
	}



	public static void testOMS(){
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

		//idk the rest

	}

	

	public static void runDownStockViaOrdersRestoreToDefaultLevel(){
		Warehouse warehouse = new Warehouse();
		Product prod2 = new Product("prod2",2,2);
		warehouse.addNewProductToWarehouse(prod2, 5);
		// System.out.println("We start this quant. of prod2: " + warehouse.getStockLevel(2));
		warehouse.fulfill(2,4);
		// System.out.println("After 4 of prod2 are ordered there are: " + warehouse.getStockLevel(2));
		warehouse.fulfill(2,1);
		// System.out.println("After 1 of prod2 are ordered there are: " + warehouse.getStockLevel(2));
		warehouse.restock(2,1);
		// System.out.println("The stock of prod2 now: " + warehouse.getStockLevel(2));

		if(warehouse.getStockLevel(2)!= 5){
			System.out.println("Failed, Judah threw IncorrectBehaviorException");
			// throw new IncorrectBehaviorException("Stock level of product 2 was initially 5, 4 were ordered and then 1 was ordered, warehouse.getStockLevel should now return 5, but it returned " );
		} else {
			System.out.println("Fixed");
		}

	}

	public static void restockToLowerAndGetStockLevel(){
		Warehouse warehouse = new Warehouse();
        warehouse.addNewProductToWarehouse(new Product("prod1",1,1), 5);
        warehouse.addNewProductToWarehouse(new Product("prod2",2,2), 5);
        warehouse.addNewProductToWarehouse(new Product("prod3",3,3), 5);
        warehouse.setDefaultStockLevel(1,1);
        int level = warehouse.getStockLevel(1);
        if(warehouse.getStockLevel(1)!= 5){
        	System.out.println("Failed, Judah threw IncorrectBehaviorException");
            // throw new IncorrectBehaviorException("Stock level of product 1 was initially 5, then set to 1 using warehouse.setDefaultStockLevel, but warehouse.getStockLevel returned " + level);
        } else {
			System.out.println("Fixed");
		}


	}

	public static void successfulValidateProducts(){
		int ogDefaultStock = 5;
		Set<Product> prdcts = new HashSet<>();
		Set<ServiceProvider> svcPvdrs = new HashSet<>();
		Warehouse wh = new Warehouse();
		OrderManagementSystem testOMS = new OrderManagementSystem(prdcts, ogDefaultStock, svcPvdrs, wh);

        Order order = new Order();
        Product p1 = new Product("p1", 15, 1);
		Product p2 = new Product("p2", 2, 2);
        order.addToOrder(p1,15);
        order.addToOrder(p2,4);
        ArrayList<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        System.out.println("validateProducts returned: " + testOMS.validateProducts(products,order));
        int ret = testOMS.validateProducts(products,order);
        if(ret != 0){
        	System.out.println("Failed: IncorrectBehaviorException thrown in Judah's code.");
            // throw new IncorrectBehaviorException("validateProducts should've returned 0, but returned " + ret);
        } else {
			System.out.println("Fixed");
		}
	}

	public static void unsuccessfulValidateServicesNotEnoughProviders(){
		int ogDefaultStock = 5;
		Set<Product> prdcts = new HashSet<>();
		Set<ServiceProvider> svcPvdrs = new HashSet<>();
		Warehouse wh = new Warehouse();
		OrderManagementSystem testOMS = new OrderManagementSystem(prdcts, ogDefaultStock, svcPvdrs, wh);
        Order order = new Order();
        Service srvc1 = new Service(15, 2, 1, "srvc1");
        Service srvc2 = new Service(10, 1, 2, "srvc2");
        Service srvc6 = new Service(15, 2, 1, "srvc1");;
        order.addToOrder(srvc1,2);
        order.addToOrder(srvc2,2);
        order.addToOrder(srvc6,1);
        ArrayList<Service> srvcs = new ArrayList<>();
        srvcs.add(srvc1);
        srvcs.add(srvc2);
        srvcs.add(srvc6);
        int ret = testOMS.validateServices(srvcs,order);
        if(ret != 6 && ret != 2){
			System.out.println("Failed: IncorrectBehaviorException thrown in Judah's code.");
            // throw new IncorrectBehaviorException("validateServices should've returned 6 or 2, but returned " + ret);
        } else {
			System.out.println("Fixed");
		}
	}

	public static void testAddGetCatalog(){
        int ogDefaultStock = 5;
		Set<Product> prdcts = new HashSet<>();
		Product testStock1 = new Product("Ball", 15, 212);
		Product testStock2 = new Product("Stick", 2, 44);
		prdcts.add(testStock1);
		prdcts.add(testStock2);
		Set<ServiceProvider> svcPvdrs = new HashSet<>();
		Warehouse wh = new Warehouse();
		OrderManagementSystem testOMS = new OrderManagementSystem(prdcts, ogDefaultStock, svcPvdrs, wh);
        catalogMatchesExpectedSet(testOMS);
        //make sure that adding duplicates doesn't result in more products in the catalog
        testOMS.addNewProducts(prdcts);
        catalogMatchesExpectedSet(testOMS);
    }
    public static void catalogMatchesExpectedSet(OrderManagementSystem system){
    	Set<Product> prdcts = new HashSet<>();
		Product testStock1 = new Product("Ball", 15, 212);
		Product testStock2 = new Product("Stick", 2, 44);
		prdcts.add(testStock1);
		prdcts.add(testStock2);
        //populate our system with products and services
        system.addNewProducts(prdcts);
        //make sure all the products added are in the catalog
        Set<Product> catalog = system.getProductCatalog();
        if(prdcts.size() != catalog.size() || !catalog.containsAll(prdcts)){
        	System.out.println("Failed: IncorrectBehaviorException thrown in Judah's code.");
            // throw new IncorrectBehaviorException("Incorrect Set of Products returned from OrderManagementSystem.getProductCatalog after products were added via OrderManagementSystem.addNewProducts");
        } else {
			System.out.println("Fixed");
		}
    }


}
