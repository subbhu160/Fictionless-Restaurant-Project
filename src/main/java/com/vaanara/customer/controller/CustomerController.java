package com.vaanara.customer.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaanara.customer.model.Customer;
import com.vaanara.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController 
{
	@Autowired
	CustomerService  customerService;
	
	@PostMapping("/save")
	public String saveCustomer(@RequestBody List<Customer> Customer)
	{
		
		 String msg =customerService.saveCustomer(Customer);
		return msg;
	}
	
	
	@GetMapping("/recommend/{customerId}")
	public Map<String, Integer> getAvgItems(@PathVariable Integer customerId)
	{
		
		 
		return customerService.avgItem(customerId);
	}
	
	@GetMapping("/getLastOrder/{customerId}")
	public List<String> getLastOrder(@PathVariable Integer customerId)
	{
		
		 
		return customerService.getLastOrder(customerId);
	
  }
	@GetMapping("/getbOnTimeR")
    public List<String> getOrderBasedOnTimeRange(@RequestParam Integer customerId,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalTime arrivalTime){
       
		 return customerService.basedOnTime(customerId, arrivalTime);
       
      
        }
}
