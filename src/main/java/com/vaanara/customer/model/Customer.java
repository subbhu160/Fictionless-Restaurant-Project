package com.vaanara.customer.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Document(collection = "customer_details")
public class Customer 
{
	@Id
	private Integer customerId;
	private String customerName;
	private   Long customerFaceId;
	private Long customerMobileNo;
	private List<CustomerOrder> customerOrder;
	
  
}
