package com.wrappiza.application.service;

import com.wrappiza.application.model.CreateCustomerRequest;
import com.wrappiza.application.model.CreateCustomerResponse;

/**
 * Interface for Customer service
 * 
 * @author Chandan Kumar
 *
 */
public interface CustomerService {

	/**
	 * Method used to create a customer logic
	 * 
	 * @param createCustomerRequest
	 */
	CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);

	/**
	 * Method used to retrieve all customer logic
	 * 
	 * @param
	 */
	CreateCustomerResponse retrieveCustomer();
	
	/**
	 * Method used to retrieve single customer logic
	 * 
	 * @param id,mobileNumber
	 */
	CreateCustomerResponse getSingleCustomer(String id, String mobileNumber);
	
	/**
	 * Method used to update single customer logic
	 * 
	 * @param id
	 * @param mobileNumber
	 * @param createCustomerRequest
	 * 
	 */
	CreateCustomerResponse updateSingleCustomer(String id, String mobileNumber, CreateCustomerRequest createCustomerRequest);
	
	/**
	 * Method used to delete single customer logic
	 * 
	 * @param String id,String mobileNumber
	 */
	CreateCustomerResponse deleteSingleCustomer(String id, String mobileNumber);
}