package com.wrappiza.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrappiza.application.model.CreateCustomerRequest;
import com.wrappiza.application.model.CreateCustomerResponse;
import com.wrappiza.application.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class for Customer Service
 * 
 * @author Chandan Kumar
 *
 */
@RestController
@RequestMapping("/wrappiza")
@Slf4j
public class CustomerController {

	@Autowired
	private final CustomerService customerService;

	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * API used for creating a customer service
	 * 
	 * @param createCustomerRequest
	 */
	@PostMapping(value = "/addcustomer")
	public ResponseEntity<CreateCustomerResponse> addCustomer(
			@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {

		log.info("Add customer is in progress");

		CreateCustomerResponse response = customerService.createCustomer(createCustomerRequest);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for retrieving all customer
	 * 
	 * @param
	 */
	@GetMapping(value = "/customer")
	@Tag(name = "Get Customer", description = "All Customer is retrieved")
	public ResponseEntity<CreateCustomerResponse> retrieveAll() {

		log.info("Retrieve all customer is in progress");

		CreateCustomerResponse response = customerService.retrieveCustomer();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for retrieving single customer by customer Id or customer mobile
	 * number
	 * 
	 * @param id,mobileNumber
	 */
	@GetMapping(value = "/singlecustomer")
	@Tag(name = "Get Single Customer", description = "Single customer is retrieved")
	public ResponseEntity<CreateCustomerResponse> retrieveSingleCustomer(@RequestParam(required = false) String id,
			@RequestParam(required = false) String mobileNumber) {

		log.info("Retrieve single customer is in progress");

		CreateCustomerResponse response = customerService.getSingleCustomer(id, mobileNumber);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for updating single customer by Id or mobile number
	 * 
	 * @param id,String mobileNumber
	 */
	@PutMapping(value = "/updatecustomer")
	@Tag(name = "Customer Updation", description = "Single Customer is updated")
	public ResponseEntity<CreateCustomerResponse> updateCustomer(
			@RequestBody CreateCustomerRequest createCustomerRequest, @RequestParam(required = false) String id,
			@RequestParam(required = false) String mobileNumber) {

		log.info("Update customer is in progress");

		CreateCustomerResponse response = customerService.updateSingleCustomer(id, mobileNumber, createCustomerRequest);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for deleting single customer by customer Id or mobile number
	 * 
	 * @param id,name
	 */
	@DeleteMapping(value = "/deletecustomer")
	@Tag(name = "Customer Deletion", description = "Single customer is deleted")
	public ResponseEntity<CreateCustomerResponse> deleteCustomer(@RequestParam(required = false) String id,
			@RequestParam(required = false) String mobileNumber) {

		log.info("Delete customer is in progress");

		CreateCustomerResponse response = customerService.deleteSingleCustomer(id, mobileNumber);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}