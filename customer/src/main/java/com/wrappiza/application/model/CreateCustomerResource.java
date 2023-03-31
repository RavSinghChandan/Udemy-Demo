package com.wrappiza.application.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for CreateCustomerResource
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Document(collection = "customer")
public class CreateCustomerResource {

	@Id
	private String customerId;
	private String name;
	private String email;
	private String mobileNumber;
	private LocalDate date;
	private String status;

}