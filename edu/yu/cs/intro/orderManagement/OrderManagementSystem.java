package edu.yu.cs.intro.orderManagement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//check if serviceprovider is in map if not add it
//if its in check the ammount of orders that passed
//if number is 3 then remove from map
//if below 3 CHECK DIFF service provider if availabkle and add 1 to orders that passed

//use get or default
/**
* Takes orders, manages the warehouse as well as service providers
*/
public class OrderManagementSystem {
	private Warehouse warehouse;
	private Set<Service> allServices;
	private Map<Service, List<ServiceProvider>> serveToServer;
	private Map<ServiceProvider, Integer> serviceProviderUses;
	private int defaultProductStockLevel;
	
	public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel, Set<ServiceProvider> serviceProviders) {
		this(products, defaultProductStockLevel, serviceProviders, new Warehouse());
	}

	public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel, Set<ServiceProvider> serviceProviders, Warehouse warehouse) {
		this.defaultProductStockLevel = defaultProductStockLevel;
		this.serviceProviderUses = new HashMap<>();
		this.warehouse = warehouse;
		for (Product p : products) {
			this.warehouse.addNewProductToWarehouse(p, defaultProductStockLevel);
		}

		this.allServices = new HashSet<Service>();
		for (ServiceProvider servPro : serviceProviders) {
		this.allServices.addAll(servPro.getServices());

		}
		// The key will be a service and the value will be all the serviceproviders that
		// can do that service
		this.serveToServer = new HashMap<>();
		// Going through each service that the business offers
		for (Service bigServ : this.allServices) {
			// The list of serviceproviders who offer this specific service
			List<ServiceProvider> serveProList = new ArrayList<>();
			// going through each servciceProvider the business offers
			for (ServiceProvider servePro : serviceProviders) {
				// the set of services that this specific serviceprovider provides
				Set<Service> services = servePro.getServices();
				// going through the set of services provided by this specific serviceprovider
				for (Service serv : services) {
				// if any of the services offered by this service provider equals the service we
				// are checking for,
				// then the specific serviceprovider is added to the list of service providers
					if (serv.equals(bigServ)) {
						serveProList.add(servePro);
					}
				}
			}
			this.serveToServer.put(bigServ, serveProList);
		}
	}

	public void placeOrder(Order order) {
		if (validateServices(order.getServicesList(), order) != 0) {
			throw new IllegalStateException();
		}
		

		Set<ServiceProvider> providersInThisOrder = new HashSet<>();
		for (Item i : order.getItems()) {// ADD ALL CODE TO VALIDATE SERVICE
			if (i instanceof Service) {
				int counter = 0;
				// while (counter <= order.getQuantity(i)) {
				for (ServiceProvider server : this.serveToServer.get(i)) {
					if (counter <= order.getQuantity(i)) {
						try {
							server.assignToCustomer();
							this.serviceProviderUses.put(server, 0);
							counter++;
							providersInThisOrder.add(server);
						} catch (IllegalStateException e) {
							throw new IllegalStateException();
						}
					}
				}
			} else { // IF PRODUCT DO BELOW
				//test
				System.out.println("Starting stock of " + i.getDescription() + ": " + warehouse.getStockLevel(1));
				if (!this.warehouse.isRestockable(i.getItemNumber()) && this.warehouse.getStockLevel(i.getItemNumber()) == 0) {
					throw new IllegalArgumentException();
				}
				if (validateProducts(order.getProductsList(), order) == 0) {
					this.warehouse.fulfill(i.getItemNumber(), order.getQuantity(i));
				} else {
					this.warehouse.restock(i.getItemNumber(), order.getQuantity(i));
					this.warehouse.fulfill(i.getItemNumber(), order.getQuantity(i));
				}
			}
		}

		//testing stock
		System.out.println("New stock of p1: " + warehouse.getStockLevel(1));

		// PLACE ORDER
		order.setCompleted(true);
		for (ServiceProvider server : this.serviceProviderUses.keySet()) {
			if (!providersInThisOrder.contains(server)) {
				this.serviceProviderUses.replace(server, this.serviceProviderUses.get(server) + 1);
				if (this.serviceProviderUses.get(server) == 3) {
					server.endCustomerEngagement();
					this.serviceProviderUses.put(server, -1);
				}
			}
		}
	}


	protected int validateServices(Collection<Service> services, Order order) {
		for (Service service : services) {
			if (!this.allServices.contains(service)) {
				return service.getItemNumber();
			}
			int x = order.getQuantity(service);
			List<ServiceProvider> serviceProviders = this.serveToServer.get(service);
			// Easy check: If order has more requests for a specific service than we have
			// providers for, we can't fulfill it.
			if (x > serviceProviders.size()) {
				return service.getItemNumber();
			}
			// It could be that we have enough providers in the list, but they aren't all
			// available, so we check that with this
			// counter will be the number of available service providers for the current
			// service
			int counter = 0;
			for (ServiceProvider server : serviceProviders) {
				int y = this.serviceProviderUses.getOrDefault(server, -1);
				if (y == -1) {
					counter++;
				}
			}
			// If we don't have enough service providers, we can't fulfill the service
			if (counter < order.getQuantity(service)) {
				return service.getItemNumber();
			}
		}
		return 0;
	}

	protected int validateProducts(Collection<Product> products, Order order) {
		for (Product prod : products) {
			int qOrdered = order.getQuantity(prod);
			if (qOrdered > this.warehouse.getStockLevel(prod.getItemNumber())) {
				return prod.getItemNumber();
			}
		}
		return 0;
	}

	protected Set<Product> addNewProducts(Collection<Product> products) {
		Set<Product> warehouseProducts = this.warehouse.getAllProductsInCatalog();
		Set<Product> newProducts = new HashSet<>();

		for (Product product : products) {
			this.warehouse.addNewProductToWarehouse(product, this.defaultProductStockLevel);
			if (!warehouseProducts.contains(product)) {
				newProducts.add(product);
			}
		}
		return newProducts;
	}

	protected void addServiceProvider(ServiceProvider provider) {
		Set<Service> mapServices = this.serveToServer.keySet();
		Set<Service> services = provider.getServices();
		this.allServices.addAll(services);
		for (Service s : services) {
			if (mapServices.contains(s)) {
				List<ServiceProvider> servers = this.serveToServer.get(s);
				servers.add(provider);
				this.serveToServer.put(s, servers);
			} else {
				List<ServiceProvider> thisServer = new ArrayList<>();
				thisServer.add(provider);
				this.serveToServer.put(s, thisServer);
			}
		}
	}

	public Set<Product> getProductCatalog() {
		return this.warehouse.getAllProductsInCatalog();
	}

	public Set<Service> getOfferedServices() {
		return this.allServices;
	}

	protected void discontinueItem(Item item) {
		if (item instanceof Product) {
			this.warehouse.doNotRestock(item.getItemNumber());
		} else {
			this.allServices.remove(item);
		}
	}

	protected void setDefaultProductStockLevel(Product prod, int level) {
		System.out.println("Current stock of p1: " + warehouse.getStockLevel(1));
		this.warehouse.setDefaultStockLevel(prod.getItemNumber(), level);
		System.out.println("New stock of p1 (shouldn't change): " + warehouse.getStockLevel(1));
	}
}	
