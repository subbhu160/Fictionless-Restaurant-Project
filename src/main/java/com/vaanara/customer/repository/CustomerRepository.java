package com.vaanara.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vaanara.customer.model.Customer;





public interface CustomerRepository extends MongoRepository<Customer, Integer>
{
	public   Customer findByCustomerId(Integer customerId);

}
