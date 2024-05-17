package com.vaanara.customer.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaanara.customer.exception.CustomerNotFoundException;
import com.vaanara.customer.model.Customer;
import com.vaanara.customer.model.CustomerOrder;
import com.vaanara.customer.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public List<String> getLastOrder(Integer customerid) {
		
		Customer customer = findCustomerById(customerid);
	        if (customer == null) {
	            return new ArrayList<>();
	        }

	        CustomerOrder lastOrder = customer.getCustomerOrder().stream()
	                .max(Comparator.comparing(CustomerOrder::getCreationDate))
	                .orElse(null);

	        if (lastOrder != null) {
	            return List.of(lastOrder.toString());
	        } else {
	            return new ArrayList<>();
	        }
	    }
	

	
	@Override
	public List<String> basedOnTime(Integer customerId, LocalTime arrivalTime) {
	    Customer customer = findCustomerById(customerId);
	    if (customer == null) {
	    	throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
	    }
	    

	    List<CustomerOrder> ordersInRange = new ArrayList<>();

	 // Determine the time range based on arrival time
	    int hour = arrivalTime.getHour();
	    if (hour >= 0 && hour < 8) {
	        // Fetch orders created between midnight and 8:00 AM
	        ordersInRange = getOrdersInRange(customer, LocalTime.MIN, LocalTime.of(8, 0));
	    } else if (hour >= 8 && hour < 16) {
	        // Fetch orders created between 8:00 AM and 4:00 PM
	        ordersInRange = getOrdersInRange(customer, LocalTime.of(8, 0), LocalTime.of(16, 0));
	    } else if (hour >= 16 && hour <= 23) {
	        // Fetch orders created between 4:00 PM and midnight
	        ordersInRange = getOrdersInRange(customer, LocalTime.of(16, 0), LocalTime.MAX);
	    }

	    if (!ordersInRange.isEmpty()) {
	        CustomerOrder lastOrder = ordersInRange.stream()
	                .max(Comparator.comparing(CustomerOrder::getCreationDate))
	                .orElse(null);
	        if (lastOrder != null) {
	            return List.of(lastOrder.toString());
	        }
	    }
	    return new ArrayList<>();
	}

	private List<CustomerOrder> getOrdersInRange(Customer customer, LocalTime startTime, LocalTime endTime) {
	    return customer.getCustomerOrder().stream()
	            .filter(order -> order.getCreationTime().isAfter(startTime) && order.getCreationTime().isBefore(endTime))
	            .collect(Collectors.toList());
	}

	@Override
	public Map<String, Integer> avgItem(Integer customerId) {
	    // Fetch customer by ID
	    Customer customer = findCustomerById(customerId);
	    if (customer == null) {
	        throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
	    }

	    // Map to store item names and their counts
	    Map<String, Integer> itemCountMap = new HashMap<>();

	    // Iterate through customer orders
	    List<CustomerOrder> customerOrders = customer.getCustomerOrder();
	    for (CustomerOrder order : customerOrders) {
	        Map<String, String> orderedItems = order.getOrderedItems();
	        for (String itemName : orderedItems.values()) {
	            // Increment count for each ordered item
	            itemCountMap.put(itemName, itemCountMap.getOrDefault(itemName, 0) + 1);
	        }
	    }

	    // Sort items based on their counts in descending order
	    List<Map.Entry<String, Integer>> sortedItems = new ArrayList<>(itemCountMap.entrySet());
	    sortedItems.sort((a, b) -> b.getValue().compareTo(a.getValue()));

	    // Convert sortedItems list to a LinkedHashMap to maintain the insertion order
	    Map<String, Integer> result = new LinkedHashMap<>();
	    for (Map.Entry<String, Integer> entry : sortedItems) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    
	    return result;
	}

	@Override
	public String saveCustomer(List<Customer> customers) {
	    for (Customer customer : customers) {
	        Customer existingCustomer = findCustomerById(customer.getCustomerId());
	        if (existingCustomer != null) {
	            // If the customer already exists, add new orders to the existing customerOrder
	            List<CustomerOrder> existingOrders = existingCustomer.getCustomerOrder();
	            List<CustomerOrder> newOrders = customer.getCustomerOrder();

	            existingOrders.addAll(newOrders);
	            existingCustomer.setCustomerOrder(existingOrders);

	            // Save the updated customer
	            customerRepository.save(existingCustomer);
	        } else {
	            // If the customer does not exist, save as new customer
	        	
	            customerRepository.save(customer);
	        }
	    }
	    return "Order have Placed Successfully !!!";
	}

		
	
    public Customer findCustomerById(Integer userId) {
        
        return customerRepository.findByCustomerId(userId);
    }

}
