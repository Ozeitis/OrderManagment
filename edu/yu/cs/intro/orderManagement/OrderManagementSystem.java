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
public class OrderManagementSystem { // Version / Date: 1.1 / December 10, 2020
	private Warehouse warehouse;
	private Set<Service> allServices;
	private Map<Service, List<ServiceProvider>> serveToServer;
	private Map<ServiceProvider, Integer> serviceProviderUses;
	private int defaultProductStockLevel;
	// private Set<ServiceProvider> providersInThisOrder;

	/**
	 * Creates a new Warehouse instance and calls the other constructor *
	 * 
	 * @param products
	 * @param defaultProductStockLevel
	 * @param serviceProviders
	 */
	public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel,
			Set<ServiceProvider> serviceProviders) {
		this(products, defaultProductStockLevel, serviceProviders, new Warehouse());
	}

	/**
	 * 1) populate the warehouse with the products. 2) retrieve set of services
	 * provided by the ServiceProviders, to save it as the set of services the
	 * business can provide 3) create map of services to the List of service
	 * providers(As explained by Judah, this means that the key is a specific
	 * service and the value is a list of service providers who rpovide that
	 * service)
	 *
	 * @param products                 - set of products to populate the warehouse
	 *                                 with
	 * @param defaultProductStockLevel - the default number of products to stock for
	 *                                 any product
	 * @param serviceProviders         - set of service providers and the services
	 *                                 they provide, to make up the services arm of
	 *                                 the business
	 * @param warehouse                - the warehouse that we will store our
	 *                                 products in
	 */
	public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel,
			Set<ServiceProvider> serviceProviders, Warehouse warehouse) {
		this.defaultProductStockLevel = defaultProductStockLevel;
		// this.providersInThisOrder = new HashSet<>();
		this.serviceProviderUses = new HashMap<>();
		this.warehouse = warehouse;
		for (Product p : products) {
			try {
				this.warehouse.addNewProductToWarehouse(p, defaultProductStockLevel);
			} catch (IllegalArgumentException e) {
				continue;
			}
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

	/**
	 * Accept an order: 1) See if we have ServiceProviders for all Services in the
	 * order. If not, reject the order.
	 * 
	 * 2) See if the we can fulfill the order for Items. If so, place the product
	 * orders with the warehouse and handle the service orders inside this class
	 * 
	 * 2a) We CAN fulfill a product order if either the warehouse currently has
	 * enough quantity in stock OR if the product is NOT on the "do not restock"
	 * list. //if in stock but on do not restock fulfill order if quantity allows it
	 * //first check in stock then check if on do not retsock //if in stock but on
	 * do not restock still fulfill //if not in stock and not in do not restock -
	 * restock
	 * 
	 * 
	 * In the case that the current quantity of a product is < the quantity in the
	 * order AND the product is NOT on the "do not restock" list, the order
	 * management system should first instruct the warehouse to restock the item,
	 * and then tell the warehouse to fulfill this order. //tell to restock and
	 * still fulfill even if not enough stock
	 * 
	 * 3) Mark the order as completed // call setCompleted
	 * 
	 * 4) Update busy status of serviced providers... //update the max 3 //if
	 * serviceprovider is busy check another if stuill busy throw error
	 * 
	 * @throws IllegalArgumentException if any part of the order for PRODUCTS can
	 *                                  not be fulfilled
	 * @throws IllegalStateException    if any part of the order for SERVICES can
	 *                                  not be fulfilled
	 */
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
				// }
			} else { // IF PRODUCT DO BELOW
				if (!this.warehouse.isRestockable(i.getItemNumber())) {
					throw new IllegalArgumentException();
				}
				if (validateProducts(order.getProductsList(), order) == 0) {
					this.warehouse.restock(i.getItemNumber(), order.getQuantity(i));
					this.warehouse.fulfill(i.getItemNumber(), order.getQuantity(i));
				}
			}
		}

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

	/**
	 * Validate that all the services being ordered can be provided. Make sure to
	 * check how many instances of a given service are being requested in the order,
	 * and see if we have enough providers for them.
	 * 
	 * @param services the set of services which are being ordered inside the order
	 * @param order    the order whose services we are validating
	 * @return itemNumber of a requested service that we either do not have provider
	 *         for at all, or for which we do not have an available provider. Return
	 *         0 if all services are valid.
	 */
	protected int validateServices(Collection<Service> services, Order order) {
		// As soon as an order needs a service provider, the service provider is added
		// to this set.
		// We then check if the service provider has already been used in this order.
		// Have to do this all here bc Judah's test code compares a set of services to
		// the services in the order, without ever initializing
		// an ordermanagementsystem, and therefore, we cant just rely on the
		// serviceProviderUses map.
		Set<ServiceProvider> usedUp = new HashSet<>();
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
				if (y == -1 && !usedUp.contains(server)) { // see explanation above
					counter++;
					usedUp.add(server);// see explanation above
				}
			}
			// If we don't have enough service providers, we can't fulfill the service
			if (counter < order.getQuantity(service)) {
				return service.getItemNumber();
			}
		}
		return 0;
		// Still worried about when the service provider becomes available

	}

	/**
	 * validate that the requested quantity of products can be fulfilled
	 * 
	 * @param products being ordered in this order
	 * @param order    the order whose products we are validating
	 * @return itemNumber of product which is either not in the catalog or which we
	 *         have insufficient quantity of. Return 0 if we can fulfill.
	 */
	protected int validateProducts(Collection<Product> products, Order order) {
		for (Product prod : products) {
			int qOrdered = order.getQuantity(prod);
			if (qOrdered > this.warehouse.getStockLevel(prod.getItemNumber())
					&& !warehouse.isRestockable(prod.getItemNumber())) {
				return prod.getItemNumber();
			}
		}
		return 0;
	}

	/**
	 * Adds new Products to the set of products that the warehouse can ship/fulfill
	 * 
	 * @param products the products to add to the warehouse
	 * 
	 * @return set of products that were actually added (don't include any products
	 *         that were Version / Date: 1.1 / December 10, 2020
	 * 
	 *         4 already in the warehouse before this was called!)
	 */
	protected Set<Product> addNewProducts(Collection<Product> products) {
		Set<Product> warehouseProducts = this.warehouse.getAllProductsInCatalog();
		Set<Product> newProducts = new HashSet<>();

		for (Product product : products) {
			try{
			this.warehouse.addNewProductToWarehouse(product, this.defaultProductStockLevel);
			}catch(IllegalArgumentException e){
				continue;
			}
			if (!warehouseProducts.contains(product)) {
				newProducts.add(product);
			}
		}
		return newProducts;
	}

	/**
	 * Adds an additional ServiceProvider to the system. Update all relevant data
	 * about which Services are offered and which ServiceProviders provide which
	 * services * @param provider the provider to add
	 */
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

	/**
	 * *
	 * 
	 * @return get the set of all the products offered/sold by this business
	 */
	public Set<Product> getProductCatalog() {
		return this.warehouse.getAllProductsInCatalog();
	}

	/**
	 * @return get the set of all the Services offered/sold by this business
	 */
	public Set<Service> getOfferedServices() {
		return this.allServices;
	}

	/**
	 * Discontinue Item, i.e. stop selling a Service or Product. Also prevent the
	 * Item from being added in the future. If it's a Service - remove it from the
	 * set of provided services. If it's a Product - still sell whatever instances
	 * of this Product are in stock, but do not restock it.
	 * 
	 * @param item the item to discontinue see {@link Item}
	 */
	protected void discontinueItem(Item item) {
		if (item instanceof Product) {
			this.warehouse.doNotRestock(item.getItemNumber());
		} else {
			this.allServices.remove(item);
		}
	}

	/**
	 * Set the default product stock level for the given product * @param prod
	 * 
	 * @param level
	 */
	protected void setDefaultProductStockLevel(Product prod, int level) {
		this.warehouse.setDefaultStockLevel(prod.getItemNumber(), level);
	}

	protected String listToString(List<ServiceProvider> input) {
		String returnString = "";
		for (ServiceProvider sP : input) {
			returnString += sP.getName() + ", ";
		}
		return returnString;
	}

}
