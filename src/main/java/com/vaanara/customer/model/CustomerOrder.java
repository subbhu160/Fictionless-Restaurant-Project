package com.vaanara.customer.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
public class CustomerOrder 
{
	@Id
	private Integer orderId;
	// private Clock clock;
	private Map<String,String> orderedItems;
	 @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate creationDate; // Separate field for date
	 @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime creationTime; // Separate field for time

    // No-args constructor for Spring Data MongoDB
    public CustomerOrder() {
        this.creationDate = LocalDate.now(); // Initialize creationDate with current system date
        this.creationTime = LocalTime.now(); // Initialize creationTime with current system time
    }
}
