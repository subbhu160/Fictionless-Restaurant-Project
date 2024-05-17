package com.vaanara.customer.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.vaanara.customer.model.Customer;

public interface CustomerService 
{
	public String saveCustomer(List<Customer> customer);
	public List<String> getLastOrder(Integer customerid);
	public List<String> basedOnTime(Integer customerId, LocalTime arrivalTime);
	public Map<String, Integer>  avgItem(Integer customerId);

}
