package edu.yu.cs.intro.orderManagement;

import java.util.Arrays;

public class TestsHW8 {

	public static void main(String[] args) {
		double pricePerHour = 20;
		int numberOfHours = 4;
		int serviceID = 221;
		String description = "RunsAService";

		Item testItem = new Service(pricePerHour, numberOfHours, serviceID, description);

		// System.out.println("The number of hours is: " + testItem.getNumberOfHours());
		System.out.println("The service ID is: " + testItem.getItemNumber());
		System.out.println("The service is described as: " + testItem.getDescription());
		System.out.println("The service costs: " + testItem.getPrice());

		Item testEqual1 = new Service(30, 5, 444, "RunsAService");
		Item testEqual2 = new Service(20, 4, 221, "RunsAService");

		System.out.println("testEqual1 does not equal testItem :" + testItem.equals(testEqual1));
		System.out.println("testEqual2 does equal testItem :" + testItem.equals(testEqual2));

	}

}